package Servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ReportsDAO;
import DAO.UserInfoDAO;
import DAO.WorkHistoryDAO;
import DTO.ReportBeans;

/**
 * Servlet implementation class ManageReportsServlet
 */
@WebServlet("/ManageReports")
public class ManageReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageReportsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String admin_flg = (String)session.getAttribute("admin_flg");
		if(!admin_flg.equals("1")) {
			// セッションスコープの情報を破棄
			session.invalidate();

			String message = "管理者権限へのアクセスが拒否されました";
			request.setAttribute("message", message);

			// フォワード実行
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}

		HashMap<String,ArrayList<String>> userMap = new HashMap<String,ArrayList<String>>(); //情報を取り出すだけのマップ
		try(UserInfoDAO ud = new UserInfoDAO();){
			userMap = (HashMap<String,ArrayList<String>>)ud.getUserMap();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		ArrayList<String>user_name_list = userMap.get("user_name_list");
		ArrayList<String>emp_id_list = userMap.get("emp_id_list");

		String targetUser = "";
		if(request.getParameter("target_user") == null || request.getParameter("target_user") == "") {
		    targetUser = user_name_list.get(0) ;
		}else {
			targetUser= request.getParameter("target_user");
		}
		//target_userのemp_idを取得する
		int index = user_name_list.indexOf(targetUser);
		String emp_id = emp_id_list.get(index);


		int target_year = 0;
		int target_month = 0;
		int target_date =0;
		if(request.getParameter("target_year") == null) {
		    Calendar calendar = Calendar.getInstance();
		    target_year = calendar.get(Calendar.YEAR) ;
		}else {
			target_year = Integer.parseInt(request.getParameter("target_year"));
		}
		if(request.getParameter("target_month") == null) {
		    Calendar calendar = Calendar.getInstance();
		    target_month = calendar.get(Calendar.MONTH) +1;
		}else {
			target_month = Integer.parseInt(request.getParameter("target_month"));
		}
		if(request.getParameter("target_date") == null) {
		    Calendar calendar = Calendar.getInstance();
		    target_date = calendar.get(Calendar.DATE) ;
		}else {
			target_date = Integer.parseInt(request.getParameter("target_date"));
		}

		//対象日付の業務報告をデータベースから持ってくる
		Date date = new Date(target_year-1900,target_month-1,target_date);
		ReportBeans rb = new ReportBeans();

		try (ReportsDAO repo = new ReportsDAO()){
			rb = repo.getReport(emp_id,date);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServletException(e);
		}

		//カレンダー表示の処理
		int startDay = getStartOfMonth(target_year,target_month);
		int endDate  = getEndOfMonth(target_year,target_month);

		int left_top = 2-startDay;

		int line = 0;

		List <List<Integer>> dateList  = new ArrayList<List<Integer>>();

		int i = left_top;
		while(i<=endDate) {
			List<Integer> dateWeekList  = new ArrayList<Integer>();
			for(int n=0;n<7;n++) {
				dateWeekList.add(i);
				i++;
				if(i>endDate) {
					break;
				}
			}
			dateList.add(dateWeekList);
			line ++;
		}

		//jspのselectで表示する年のリストを取得する
		ArrayList<Integer>yearList = new ArrayList<Integer>();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			yearList = wd.getYearList();
		} catch (Exception e) {
			throw new ServletException(e);
		}

		request.setAttribute("targetUser", targetUser);
		request.setAttribute("nameList", user_name_list);
		request.setAttribute("yearList", yearList);
		request.setAttribute("rb", rb);
		request.setAttribute("line", line);
		request.setAttribute("dateList", dateList);
		request.setAttribute("target_date", target_date);


		RequestDispatcher rd = request.getRequestDispatcher("/manageReports.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	//年と月を指定して、月末日を取得するメソッド
	public int getEndOfMonth(int year,int month) {
		//取得処理
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int result = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return result;
	}

	//年と月を指定して、1日の曜日を取得するメソッド
	public int getStartOfMonth(int year,int month) {
		//取得処理
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);

		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:     // Calendar.SUNDAY:1 （値。意味はない）
			return 7;
		case Calendar.MONDAY:     // Calendar.MONDAY:2
			//月曜日
			return 1;
		case Calendar.TUESDAY:    // Calendar.TUESDAY:3
			//火曜日
			return 2;
		case Calendar.WEDNESDAY:  // Calendar.WEDNESDAY:4
			//水曜日
			return 3;
		case Calendar.THURSDAY:   // Calendar.THURSDAY:5
			//木曜日
			return 4;
		case Calendar.FRIDAY:     // Calendar.FRIDAY:6
			//金曜日
			return 5;
		case Calendar.SATURDAY:   // Calendar.SATURDAY:7
			//土曜日
			return 6;
		default :
			return 1;

		}


	}

}
