package Servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/Test")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/test.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");
		String user_type = (String)session.getAttribute("user_type");

		String feeling = request.getParameter("feeling");

		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		Date date = convertSQLDate(year-1900,month-1,day);


		int hour = Integer.parseInt(request.getParameter("hour"));
		int minute = Integer.parseInt(request.getParameter("minute"));

		Time time = new Time(hour,minute,0);


		//打刻完了画面に渡すdateを整形
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
		request.setAttribute("date", sdfDate.format(date));

		//打刻完了画面に渡すtimeを整形
		String timeStr = time.toString();
		request.setAttribute("time",timeStr.substring(0, 5));

		if(feeling == null) {
			//打刻完了画面に渡す文字列を指定
			request.setAttribute("punchMessage", "出勤");
		}else {
			request.setAttribute("punchMessage", "退勤");
		}

		String holiday = getDayOfTheWeek(year,month,day); //休日か平日かの取得

		//SQLの実行
		//出勤打刻時
		if(feeling == null) {
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.workStart(emp_id,date,time,holiday);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}else {
				try(WorkHistoryDAO wd = new WorkHistoryDAO()){
					wd.workFinish(emp_id,date,time,feeling,user_type);
				} catch (Exception e) {
					throw new ServletException(e);
				}
		}
		//打刻完了画面へ
		RequestDispatcher rd = request.getRequestDispatcher("/successPunch.jsp");
		rd.forward(request, response);

	}

	public static String getDayOfTheWeek(int year,int month ,int date) {
		month --;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: return "1";
		case Calendar.MONDAY: return "0";
		case Calendar.TUESDAY: return "0";
		case Calendar.WEDNESDAY: return "0";
		case Calendar.THURSDAY: return "0";
		case Calendar.FRIDAY: return "0";
		case Calendar.SATURDAY: return "1";
		}
		throw new IllegalStateException();
	}

	public static Date convertSQLDate(int year,int month,int day) {
		Date date = new Date(year,month,day);
		return date;
	}

}
