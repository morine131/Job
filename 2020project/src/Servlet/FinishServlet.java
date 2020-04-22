package Servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

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
		String feeling = request.getParameter("feeling");

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");
		String user_type = (String)session.getAttribute("user_type");
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());

		//打刻完了画面に渡すdateを整形
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
		request.setAttribute("date", sdfDate.format(date));

		//打刻完了画面に渡すtimeを整形
		String timeStr = time.toString();
		request.setAttribute("time",timeStr.substring(0, 5));

		//打刻完了画面に渡す文字列を指定
		request.setAttribute("punchMessage", "退勤");

		//帰社時刻が0:00〜8:00の間、前日分の帰社打刻とするにする処理
		ProcessedTime pTime = new ProcessedTime(time.toString());
		if(pTime.getIndex() <= 16) {
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.workOverFinish(emp_id,date,time,feeling);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}else {//23:30までに退勤打刻した時
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.workFinish(emp_id,date,time,feeling,user_type);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		//検索一覧画面へ
		RequestDispatcher rd = request.getRequestDispatcher("/successPunch.jsp");
		rd.forward(request, response);

	}

}
