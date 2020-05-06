package Servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class ChangeHoliday
 */
@WebServlet("/ChangeHoliday")
public class ChangeHolidayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeHolidayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		String status = request.getParameter("status");
		System.out.println("status: " + status);

		String setHoliday = "";
			if(status.equals("平日")) {
				setHoliday = "1";
			}else if(status.equals("休日")) {
				setHoliday = "2";
			}else {
				setHoliday = "0";
			}


		int targetYear = Integer.parseInt(request.getParameter("target_year"));
		int targetMonth = Integer.parseInt(request.getParameter("target_month"));
		int targetDay = Integer.parseInt(request.getParameter("target_day"));
		Date date = UpdateHistoryServlet.convertSQLDate(targetYear,targetMonth,targetDay);

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");


		//flag レコードだけあり打刻なし→３ 打刻レコードがそもそもない→２ 出勤打刻のみあり→1 出退勤打刻あり→0
		System.out.println("notExist: " + request.getParameter("notExist"));
		int flag = 3;
		if(request.getParameter("finish_time") != "") {
			flag = 0;
		}else if(request.getParameter("start_time") != "") {
			flag = 1;
		}else if(request.getParameter("notExist").equals("1")) {
			flag = 2;
		}

		//レコードがないとき、emp_id,date,holidayのみデータベースに登録する
		if(flag == 2) {
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.setHoliday(emp_id,date,setHoliday);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		//打刻なしでレコードがあるとき、holidayを更新する
		}else if(flag == 3) {
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.updateHoliday(emp_id,date,setHoliday);
			} catch (Exception e) {
				throw new ServletException(e);
			}
//		//出勤打刻のみあるとき、出勤時刻だけ更新する
//		}else if(flag == 3) {
//
//		}

//
//		BigDecimal start_latitude = null;
//		start_latitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("start_latitude"));
//		BigDecimal start_longitude = null;
//		start_longitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("start_longitude"));
//		BigDecimal finish_latitude = null;
//		finish_latitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("finish_latitude"));
//		BigDecimal finish_longitude = null;
//		finish_longitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("finish_longitude"));
//

//		String user_type = (String) session.getAttribute("user_type");
//
//		Date date = UpdateHistoryServlet.convertSQLDate(targetYear,targetMonth,targetDay);
//
//		Time start_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("start_time"));
//		Time finish_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("finish_time"));
//		String division = request.getParameter("division");
//		String note = request.getParameter("note");
//		String reason = request.getParameter("reason");
//		String feeling = request.getParameter("feeling");
//		String holiday = request.getParameter("holiday");
//
//		Time break_time = null;
//		Time standard_time = null;
//		String much_or_little = "";
//		Time over_time = null;
//		Time late_over_time = null;
//		Time work_time = null;
//
//		if(! isAuto) {
//			break_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("break_time"));
//			standard_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("standard_time"));
//			much_or_little = UpdateHistoryServlet.convertMOL(request.getParameter("much_or_little"));
//			over_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("over_time"));
//			late_over_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("late_over_time"));
//			work_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("work_time"));
//		}
		System.out.println("flag: "+ flag);

		// HistoryServletにリダイレクトする
		String ServletPath =  request.getContextPath()+"/History";
	    response.sendRedirect(ServletPath);
	}

}
