package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class ReportsServlet
 */
@WebServlet("/Reports")
public class ReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

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
		request.setAttribute("yearList", yearList);


		request.setAttribute("line", line);
		request.setAttribute("dateList", dateList);
		request.setAttribute("target_date", target_date);


		RequestDispatcher rd = request.getRequestDispatcher("/reports.jsp");
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
