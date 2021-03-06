package Servlet;

import java.io.IOException;
import java.math.BigDecimal;
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
import myClass.ProcessedTime;

/**
 * Servlet implementation class StartServlet
 */
@WebServlet("/Start")
public class StartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");
		String exist = (String)session.getAttribute("exist");
		String user_type = (String) session.getAttribute("user_type");

		Date date = new Date(System.currentTimeMillis()); //現在の日付
		Time time = new Time(System.currentTimeMillis()); //現在の時間
		String holiday = getDayOfTheWeek(); //休日か平日かの取得

		BigDecimal latitude = new BigDecimal(request.getParameter("latitude"));
		BigDecimal longitude = new BigDecimal(request.getParameter("longitude"));

		Boolean finished = false;
		Time finish_time = null;

		try(WorkHistoryDAO wd = new WorkHistoryDAO()){
			finish_time = wd.finishCheck(emp_id,date);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		if (finish_time != null) {
			finished = true;
		}

		if(!finished) {
			//SQLの実行
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.workStart(emp_id,date,time,holiday,latitude,longitude,exist);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}else{
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.workStart(emp_id,date,time,holiday,latitude,longitude,"1","1");
			} catch (Exception e) {
				throw new ServletException(e);
			}
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.automaticCalculation(new ProcessedTime(time.toString()), new ProcessedTime(finish_time.toString()), emp_id, date, holiday, user_type);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}


		//打刻完了画面に渡すdateを整形
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
		request.setAttribute("date", sdfDate.format(date));

		//打刻完了画面に渡すtimeを整形
		String timeStr = time.toString();
		request.setAttribute("time",timeStr.substring(0, 5));

		//打刻完了画面に渡す文字列を指定
		request.setAttribute("punchMessage", "出勤");

		//打刻完了画面へ
		RequestDispatcher rd = request.getRequestDispatcher("/successPunch.jsp");
		rd.forward(request, response);

	}

	/**
	 * 現在の曜日を返します。
	 * @return	土日→"1" 平日→"0"
	 */
	public static String getDayOfTheWeek() {
		Calendar cal = Calendar.getInstance();
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
}
