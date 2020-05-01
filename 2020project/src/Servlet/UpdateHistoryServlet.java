package Servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.WorkHistoryDAO;
import DTO.OutputHistoryBeans;

/**
 * Servlet implementation class UpdateHistoryServlet
 */
@WebServlet("/UpdateHistory")
public class UpdateHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int targetYear = Integer.parseInt(request.getParameter("target_year"));
		int targetMonth = Integer.parseInt(request.getParameter("target_month"));
		int targetDay = Integer.parseInt(request.getParameter("target_day"));

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");

		Date date = convertSQLDate(targetYear,targetMonth,targetDay);

		OutputHistoryBeans ob = new OutputHistoryBeans();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			ob = wd.getDateHistory(date,emp_id);
		} catch (Exception e) {
			// TODO: handle exception
		}

		request.setAttribute("ob", ob);

		RequestDispatcher rd = request.getRequestDispatcher("/updateHistory.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		int targetYear = Integer.parseInt(request.getParameter("target_year"));
		int targetMonth = Integer.parseInt(request.getParameter("target_month"));
		int targetDay = Integer.parseInt(request.getParameter("target_day"));

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");

		Date date = convertSQLDate(targetYear,targetMonth,targetDay);

		Time start_time = convertSQLTime(request.getParameter("start_time"));
		Time finish_time = convertSQLTime(request.getParameter("finish_time"));
		String division = request.getParameter("division");
		String note = request.getParameter("note");
		String reason = request.getParameter("reason");
		String feeling = request.getParameter("feeling");
		System.out.println(request.getParameter("break_time"));
		if(request.getParameter("break_time") != null) {
			Time break_time = convertSQLTime(request.getParameter("break_time"));
			Time standard_time = convertSQLTime(request.getParameter("standard_time"));
			String much_or_little = convertMOL(request.getParameter("much_or_little"));
			Time over_time = convertSQLTime(request.getParameter("over_time"));
			Time late_over_time = convertSQLTime(request.getParameter("late_over_time"));
			Time work_time = convertSQLTime(request.getParameter("work_time"));

			try (WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.updateNormlaHistory(emp_id,date,start_time,finish_time,feeling,break_time,standard_time,over_time,late_over_time,work_time,note,reason,division,much_or_little);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		// HomeControllerにリダイレクトする
		String ServletPath =  request.getContextPath()+"/History";
	    response.sendRedirect(ServletPath);
	}

	public static Date convertSQLDate(int year,int month,int day) {
		Date date = new Date(year - 1900,month - 1,day);
		return date;
	}

	public static Time convertSQLTime(String hhmmss) {
		if(hhmmss.length() != 8) {
			hhmmss += ":00";
		}
		int hour = Integer.parseInt(hhmmss.substring(0, 2));
		int minute = Integer.parseInt(hhmmss.substring(3, 5));
		int second = Integer.parseInt(hhmmss.substring(6, 8));
		Time time = new Time(hour,minute,second);
		return time;
	}

	public static String convertMOL(String mol) {
		String much_or_little = "";
		int intMuchOrLittle = 0;
		int minus = mol.indexOf("-");
		//マイナスの時
		if(minus != -1) {
			String hhmm = mol.substring(1);
			int semiIndex = hhmm.indexOf(":");
			String hourStr = hhmm.substring(0,semiIndex);
			String minuteStr = hhmm.substring(semiIndex+1,semiIndex+3);
			int hour = Integer.parseInt(hourStr);
			int minute = Integer.parseInt(minuteStr);
			intMuchOrLittle += hour*2;
			if(minute == 30) {
				intMuchOrLittle ++;
			}
			much_or_little = "-" + intMuchOrLittle;
		}
		//プラスの時

		return much_or_little;
	}

}
