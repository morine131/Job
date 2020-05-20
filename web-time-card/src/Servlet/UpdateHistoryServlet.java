package Servlet;

import java.io.IOException;
import java.math.BigDecimal;
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

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

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
		int flag = 0;
		if(ob.getStart_time() == null && ob.getHoliday() == null && ob.getFinish_time() == null) {
			flag = 2;
		}else if(ob.getStart_time() == null && ob.getFinish_time() != null) {
			flag = 0;
		}
		else if(ob.getStart_time() == null){
			flag = 3;
		}else if(ob.getFinish_time() == null) {
			flag = 1;
		}

		ob.setDay(request.getParameter("day"), "");

		request.setAttribute("flag", flag);
		request.setAttribute("ob", ob);

		request.setAttribute("target_year", targetYear);
		request.setAttribute("target_month", targetMonth);

		RequestDispatcher rd = request.getRequestDispatcher("/updateHistory.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("postが発生");

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		int flag = Integer.parseInt(request.getParameter("flag"));

		int targetYear = Integer.parseInt(request.getParameter("target_year"));
		int targetMonth = Integer.parseInt(request.getParameter("target_month"));
		int targetDay = Integer.parseInt(request.getParameter("target_day"));

		BigDecimal start_latitude = null;
		start_latitude = generateBigDecimal(request.getParameter("start_latitude"));
		BigDecimal start_longitude = null;
		start_longitude = generateBigDecimal(request.getParameter("start_longitude"));
		BigDecimal finish_latitude = null;
		finish_latitude = generateBigDecimal(request.getParameter("finish_latitude"));
		BigDecimal finish_longitude = null;
		finish_longitude = generateBigDecimal(request.getParameter("finish_longitude"));

		Boolean isAuto = request.getParameter("isAuto").equals("auto");

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");
		String user_type = (String) session.getAttribute("user_type");

		Date date = convertSQLDate(targetYear,targetMonth,targetDay);

		Time start_time = convertSQLTime(request.getParameter("start_time") +":00");
		Time finish_time = convertSQLTime(request.getParameter("finish_time") + ":00");
		String division = request.getParameter("division");
		String note = request.getParameter("note");
		String reason = request.getParameter("reason");
		String feeling = request.getParameter("feeling");

		Time break_time = null;
		Time standard_time = null;
		String much_or_little = "";
		Time over_time = null;
		Time late_over_time = null;
		Time work_time = null;


		if(! isAuto) {
			break_time = convertSQLTime(request.getParameter("break_time"));
			standard_time = convertSQLTime(request.getParameter("standard_time"));
			much_or_little = convertMOL(request.getParameter("much_or_little"));
			over_time = convertSQLTime(request.getParameter("over_time"));
			late_over_time = convertSQLTime(request.getParameter("late_over_time"));
			work_time = convertSQLTime(request.getParameter("work_time"));
		}

			try (WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.updateHistory(emp_id,date,start_time,finish_time,feeling,break_time,standard_time,over_time,late_over_time,work_time,note,reason,division,much_or_little,start_latitude,start_longitude,finish_latitude,finish_longitude,isAuto,flag,user_type);
			} catch (Exception e) {
				// TODO: handle exception
			}

		String confirm_message = targetYear +"/" + targetMonth + "/" + targetDay +"のデータを更新しました。";
		session.setAttribute("confirm_message", confirm_message);
		//リダイレクトする
		String ServletPath =  request.getContextPath()+"/History?target_year=" + targetYear +"&target_month=" + targetMonth;
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
		}else {//プラスの時
			String hhmm = mol;
			int semiIndex = hhmm.indexOf(":");
			String hourStr = hhmm.substring(0,semiIndex);
			String minuteStr = hhmm.substring(semiIndex+1,semiIndex+3);
			int hour = Integer.parseInt(hourStr);
			int minute = Integer.parseInt(minuteStr);
			intMuchOrLittle += hour*2;
			if(minute == 30) {
				intMuchOrLittle ++;
			}
			much_or_little = intMuchOrLittle + "";
		}


		return much_or_little;
	}

	public static BigDecimal generateBigDecimal(String deciStr) {
		BigDecimal result = new BigDecimal(0) ;
		if(deciStr != "") {
			result = new BigDecimal(deciStr);
		}
		return result;
	}
}
