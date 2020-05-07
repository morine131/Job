package Servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserInfoDAO;
import DAO.WorkHistoryDAO;
import DTO.OutputHistoryBeans;
import DTO.WorkHistoryBeans;
import myClass.ProcessedTime;

/**
 * Servlet implementation class ManageServlet
 */
@WebServlet("/Manage")
public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HashMap<String,ArrayList<String>> userMap = new HashMap<String,ArrayList<String>>(); //情報を取り出すだけのマップ
		try(UserInfoDAO ud = new UserInfoDAO();){
			userMap = (HashMap<String,ArrayList<String>>)ud.getUserMap();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		ArrayList<String>user_name_list = userMap.get("user_name_list");
		ArrayList<String>emp_id_list = userMap.get("emp_id_list");
		ArrayList<String>user_type_list = userMap.get("user_type_list");

		ArrayList<BigDecimal>start_latitude_list = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal>start_longitude_list = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal>finish_latitude_list = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal>finish_longitude_list = new ArrayList<BigDecimal>();


		int targetYear = 0;
		int targetMonth = 0;
		String targetUser = "";
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
		if(request.getParameter("target_user") == null) {
		    targetUser = user_name_list.get(0) ;
		}else {
			targetUser= request.getParameter("target_user");
		}

		//target_userのemp_idを取得する
		int index = user_name_list.indexOf(targetUser);
		String emp_id = emp_id_list.get(index);
		String user_type = user_type_list.get(index);


		//対象年、対象月、対象ユーザーの勤務表をデータベースから持ってくる
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
			if(wb.getFinish_time()!= null) {
				ob.setDate(wb.getDate().toString());
				ob.setDay(wb.getDate().toString());
				ob.setDivision(wb.getDivision());
				ob.setStart_time(wb.getStart_time());
				ob.setFinish_time(wb.getFinish_time());
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

				start_latitude_list.add(wb.getStart_latitude());
				start_longitude_list.add(wb.getStart_longitude());
				finish_latitude_list.add(wb.getFinish_latitude());
				finish_longitude_list.add(wb.getFinish_longitude());
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
			}else if(wb.getStart_time() != null){
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(targetYear,targetMonth,i+1);
				ob.setStart_time(wb.getStart_time());

				start_latitude_list.add(wb.getStart_latitude());
				start_longitude_list.add(wb.getStart_longitude());
				finish_latitude_list.add(wb.getFinish_latitude());
				finish_longitude_list.add(wb.getFinish_longitude());
			}else {
				ob.setDate(i+1);
				ob.setDay(targetYear, targetMonth,i+1);
				ob.setHoliday(targetYear,targetMonth,i+1);

				start_latitude_list.add(wb.getStart_latitude());
				start_longitude_list.add(wb.getStart_longitude());
				finish_latitude_list.add(wb.getFinish_latitude());
				finish_longitude_list.add(wb.getFinish_longitude());
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

		//jspのselectで表示する年のリストを取得する
		ArrayList<Integer>yearList = new ArrayList<Integer>();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			yearList = wd.getYearList();
		} catch (Exception e) {
			throw new ServletException(e);
		}


		request.setAttribute("week_work_time", weekdays_work_time);
		request.setAttribute("week_standard_time", weekdays_standard_time);
		request.setAttribute("week_much_or_little", ob.getMuch_or_little());
		request.setAttribute("week_over_time", weekdays_over_time);
		request.setAttribute("week_late_over_time", weekdays_late_over_time);

		request.setAttribute("holi_work_time", holidays_work_time);
		request.setAttribute("holi_over_time", holidays_over_time);
		request.setAttribute("holi_late_over_time", holidays_late_over_time);

		request.setAttribute("list", resultList);
		request.setAttribute("target_user_type", user_type);

		request.setAttribute("yearList", yearList);

		request.setAttribute("start_latitude_list", start_latitude_list);
		request.setAttribute("start_longitude_list", start_longitude_list);
		request.setAttribute("finish_latitude_list",finish_latitude_list);
		request.setAttribute("finish_longitude_list", finish_longitude_list);

		request.setAttribute("nameList", user_name_list);
		RequestDispatcher rd = request.getRequestDispatcher("/manage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
