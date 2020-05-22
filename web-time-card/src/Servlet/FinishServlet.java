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
 * Servlet implementation class FinishServlet
 */
@WebServlet("/Finish")
public class FinishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FinishServlet() {
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

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		String exist = request.getParameter("exist");

		String feeling = request.getParameter("feeling");

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");
		String user_type = (String)session.getAttribute("user_type");
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		String note = null;

		System.out.println(request.getParameter("latitude"));
		System.out.println(request.getParameter("longitude"));
		BigDecimal latitude = new BigDecimal(request.getParameter("latitude"));
		BigDecimal longitude = new BigDecimal(request.getParameter("longitude"));

		//打刻完了画面に渡すdateを整形
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
		request.setAttribute("date", sdfDate.format(date));

		//打刻完了画面に渡すtimeを整形
		String timeStr = time.toString();
		request.setAttribute("time",timeStr.substring(0, 5));

		//打刻完了画面に渡す文字列を指定
		request.setAttribute("punchMessage", "退勤");

		//8時までの退勤打刻は前日分の退勤打刻とする
		ProcessedTime pTime = new ProcessedTime(time.toString());
		Calendar cal = new Calendar.Builder().setInstant(date).build();
		if(pTime.getIndex() <= 16) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			date.setTime(cal.getTimeInMillis());
		}

		Boolean finished = false;
		try(WorkHistoryDAO wd = new WorkHistoryDAO()){
			 if(wd.finishCheck(emp_id,date) != null) {
				 finished = true;
			 }
		} catch (Exception e) {
			throw new ServletException(e);
		}

			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				if(exist.equals("1") ) {
					wd.workFinish(emp_id,date,time,feeling,user_type,latitude,longitude,note);
				}else if(finished) {
					System.out.println("ここが発生");
					wd.workFinish_update(emp_id,date,time,feeling,user_type,latitude,longitude);
				}
				else {
					wd.workFinish_new(emp_id,date,time,feeling,user_type,latitude,longitude,note);
				}
			} catch (Exception e) {
				throw new ServletException(e);
			}

		RequestDispatcher rd = request.getRequestDispatcher("/successPunch.jsp");
		rd.forward(request, response);

	}

}
