package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.WorkHistoryDAO;
import DTO.OutputHistoryBeans;
import DTO.WorkHistoryBeans;
import myClass.ProcessedTime;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/History")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HistoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String emp_id = (String)session.getAttribute("emp_id");

		//パラメータが空の時＝メニューからの遷移時、現在年を対象とする
		//パラメーターに値が含まれている場合＝feelList.jspの操作でGetメソッドが呼び出された時、パラメーターで指定された年を対象とする
		int targetYear = 0;
		int targetMonth = 0;
		Calendar calendar = Calendar.getInstance();
		if(request.getParameter("target_year") == null) {
		    targetYear = calendar.get(Calendar.YEAR) ;
		}else {
			targetYear= Integer.parseInt(request.getParameter("target_year"));
		}
		if(request.getParameter("target_month") == null) {
		    targetMonth = calendar.get(Calendar.MONTH)+1 ;
		}else {
			targetMonth= Integer.parseInt(request.getParameter("target_month"));
		}

		String division = "";

		int int_weekdays_work_time = 0;
		int int_weekdays_standard_time = 0;
		int int_weekdays_much_or_little = 0;
		int int_weekdays_over_time = 0;
		int int_weekdays_late_over_time = 0;

		int int_holidays_work_time = 0;
		int int_holidays_over_time = 0;
		int int_holidays_late_over_time = 0;

		ProcessedTime p_weekdays_work_time = new ProcessedTime();
		ProcessedTime p_weekdays_standard_time = new ProcessedTime();
		ProcessedTime p_weekdays_much_or_little = new ProcessedTime();
		ProcessedTime p_weekdays_over_time = new ProcessedTime();
		ProcessedTime p_weekdays_late_over_time = new ProcessedTime();

		ProcessedTime p_holidays_work_time = new ProcessedTime();
		ProcessedTime p_holidays_over_time = new ProcessedTime();
		ProcessedTime p_holidays_late_over_time = new ProcessedTime();

		String weekdays_work_time = "";
		String weekdays_standard_time = "";
		String weekdays_much_or_little = "";
		String weekdays_over_time = "";
		String weekdays_late_over_time = "";

		String holidays_work_time = "";
		String holidays_over_time = "";
		String holidays_late_over_time = "";

		ArrayList<WorkHistoryBeans>list = new ArrayList<WorkHistoryBeans>();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){

			list = wd.getMonthHistory(emp_id,targetYear,targetMonth);

		} catch (Exception e) {
			throw new ServletException(e);
		}
		ArrayList<OutputHistoryBeans>resultList = new ArrayList<OutputHistoryBeans>();

		int i= 0;
		for (WorkHistoryBeans wb : list) {
			OutputHistoryBeans ob = new OutputHistoryBeans();
			//出退勤されているとき
			if(wb.getFinish_time()!= null && wb.getStart_time()!= null ) {
				ob.setDate(wb.getDate().toString());
				ob.setDay(wb.getDate().toString());
				ob.setDivision(wb.getDivision());
				ob.setStart_time(wb.getStart_time());
				ob.setFinish_time(wb.getFinish_time());
				ob.setStart_time_hhmm(wb.getStart_time());
				ob.setFinish_time_hhmm(wb.getFinish_time());
				ob.setFeeling(wb.getFeeling());
				ob.setHoliday(wb.getHoliday());
				ob.setBreak_time(wb.getBreak_time());
				ob.setStandard_time(wb.getStandard_time());
				ob.setMuch_or_little(wb.getMuch_or_little());
				ob.setOver_time(wb.getOver_time());
				ob.setLate_over_time(wb.getLate_over_time());
				ob.setWork_time(wb.getWork_time());
				ob.setNote(wb.getNote());
				ob.setReason(wb.getReason());
				if(wb.getHoliday().equals("0")) {
					ProcessedTime p_weekday_work_time = new ProcessedTime(wb.getWork_time().toString());
					ProcessedTime p_weekday_standard_time = new ProcessedTime(wb.getStandard_time().toString());
					ProcessedTime p_weekday_over_time = new ProcessedTime(wb.getOver_time().toString());
					ProcessedTime p_weekday_late_over_time = new ProcessedTime(wb.getLate_over_time().toString());
					int_weekdays_work_time += p_weekday_work_time.getIndex();
					int_weekdays_standard_time += p_weekday_standard_time.getIndex();
					int_weekdays_much_or_little += Integer.parseInt(wb.getMuch_or_little());
					int_weekdays_over_time += p_weekday_over_time.getIndex();
					int_weekdays_late_over_time += p_weekday_late_over_time.getIndex();
				}else {
					ProcessedTime p_holiday_work_time = new ProcessedTime(wb.getWork_time().toString());
					ProcessedTime p_holiday_over_time = new ProcessedTime(wb.getOver_time().toString());
					ProcessedTime p_holiday_late_over_time = new ProcessedTime(wb.getLate_over_time().toString());
					int_holidays_work_time += p_holiday_work_time.getIndex();
					int_holidays_over_time += p_holiday_over_time.getIndex();
					int_holidays_late_over_time += p_holiday_late_over_time.getIndex();
				}
			}else if(wb.getFinish_time() != null && wb.getStart_time() == null) {
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(targetYear,targetMonth,i+1);
				ob.setFinish_time(wb.getFinish_time());
				ob.setFinish_time_hhmm(wb.getFinish_time());
				ob.setFeeling(wb.getFeeling());
			}
			else if(wb.getStart_time() != null){
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(wb.getHoliday());
				ob.setStart_time(wb.getStart_time());
				ob.setStart_time_hhmm(wb.getStart_time());
			}else if(wb.getHoliday() != null) {
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(wb.getHoliday());
			}else {
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(targetYear,targetMonth,i+1);
				ob.setNotExist(1);
			}
			resultList.add(ob);
			i++;
		}

		OutputHistoryBeans ob = new OutputHistoryBeans();
		ob.setMuch_or_little(int_weekdays_much_or_little + "");

		p_weekdays_work_time.setIndex(int_weekdays_work_time);
		p_weekdays_standard_time.setIndex(int_weekdays_standard_time);
		p_weekdays_over_time.setIndex(int_weekdays_over_time);
		p_weekdays_late_over_time.setIndex(int_weekdays_late_over_time);

		p_holidays_work_time.setIndex(int_holidays_work_time);
		p_holidays_over_time.setIndex(int_holidays_over_time);
		p_holidays_late_over_time.setIndex(int_holidays_late_over_time);

		weekdays_work_time = p_weekdays_work_time.convertHHHTime();
		weekdays_standard_time = p_weekdays_standard_time.convertHHHTime();
		weekdays_over_time = p_weekdays_over_time.convertHHHTime();
		weekdays_late_over_time = p_weekdays_late_over_time.convertHHHTime();

		holidays_work_time = p_holidays_work_time.convertHHHTime();
		holidays_over_time = p_holidays_over_time.convertHHHTime();
		holidays_late_over_time = p_holidays_late_over_time.convertHHHTime();

		request.setAttribute("week_work_time", weekdays_work_time);
		request.setAttribute("week_standard_time", weekdays_standard_time);
		request.setAttribute("week_much_or_little", ob.getMuch_or_little());
		request.setAttribute("week_over_time", weekdays_over_time);
		request.setAttribute("week_late_over_time", weekdays_late_over_time);

		request.setAttribute("holi_work_time", holidays_work_time);
		request.setAttribute("holi_over_time", holidays_over_time);
		request.setAttribute("holi_late_over_time", holidays_late_over_time);

		request.setAttribute("list", resultList);

		RequestDispatcher rd = request.getRequestDispatcher("/history.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
