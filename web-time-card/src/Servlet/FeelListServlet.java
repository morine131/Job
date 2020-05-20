package Servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInfoDAO;
import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class FeelListServlet
 */
@WebServlet("/FeelList")
public class FeelListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeelListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

		//パラメータが空の時＝メニューからの遷移時、現在年を対象とする
		//パラメーターに値が含まれている場合＝feelList.jspの操作でGetメソッドが呼び出された時、パラメーターで指定された年を対象とする
		int target_year = 0;
		if(request.getParameter("target_year") == null) {
		    Calendar calendar = Calendar.getInstance();
		    target_year = calendar.get(Calendar.YEAR) ;
		}else {
			target_year = Integer.parseInt(request.getParameter("target_year"));
		}


		HashMap<String,ArrayList<String>> userMap = new HashMap<String,ArrayList<String>>(); //情報を取り出すだけのマップ
		try(UserInfoDAO ud = new UserInfoDAO();){
			userMap = (HashMap<String,ArrayList<String>>)ud.getUserMap();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		ArrayList<String>user_name_list = userMap.get("user_name_list");
		ArrayList<String>emp_id_list = userMap.get("emp_id_list");

		ArrayList<HashMap<Object,Object>>list = new ArrayList<HashMap<Object,Object>>();

		for(int i=0;i<user_name_list.size();i++) {
			HashMap<Object, Object> userWorkInfoMap = new HashMap<Object,Object>();
			userWorkInfoMap.put("user_name", user_name_list.get(i));
			ArrayList<ArrayList<String>>feelYearList = new ArrayList<ArrayList<String>>();
			for(int n=0;n<12;n++) {
				Date startDay = new Date(target_year - 1900,n,1);
				Date endDay = new Date(target_year,n,getEndOfMonth(target_year,n+1));

				try(WorkHistoryDAO wd = new WorkHistoryDAO();){
					ArrayList<String>feelMonthList = new ArrayList<String>();
					feelMonthList = wd.getFeelList(emp_id_list.get(i), target_year,n+1);
					feelYearList.add(n, feelMonthList);
				} catch (Exception e) {
					throw new ServletException(e);
				}
			}
			userWorkInfoMap.put("feelYearList", feelYearList);
			list.add(i, userWorkInfoMap);
		}

		//jspのselectで表示する年のリストを取得する
		ArrayList<Integer>yearList = new ArrayList<Integer>();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			yearList = wd.getYearList();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		request.setAttribute("yearList", yearList);


		request.setAttribute("list", list);

		RequestDispatcher rd = request.getRequestDispatcher("/feelList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

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

}
