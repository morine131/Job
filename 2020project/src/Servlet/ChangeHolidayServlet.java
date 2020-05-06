package Servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInfoDAO;
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
		}else if(flag == 3 || flag == 1) {
			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.updateHoliday(emp_id,date,setHoliday);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}else {
			Time start_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("start_time"));
			Time finish_time = UpdateHistoryServlet.convertSQLTime(request.getParameter("finish_time"));
			String feeling = request.getParameter("feeling");
			String user_type = (String)session.getAttribute("user_type");
			BigDecimal finish_latitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("finish_latitude"));
			BigDecimal finish_longitude = UpdateHistoryServlet.generateBigDecimal(request.getParameter("finish_longitude"));
			String note = request.getParameter("note");

			try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wd.updateAuto(emp_id,date,start_time,finish_time , setHoliday,feeling,user_type ,finish_latitude,finish_longitude ,note);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		//有給日数の計算
		if(setHoliday.equals("2")) {
			try(UserInfoDAO ud = new UserInfoDAO()){
				ud.updatePaidVacations(emp_id, "minus");
				int num = (int) session.getAttribute("paid_vacations");
				num --;
				session.setAttribute("paid_vacations", num);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}else if(setHoliday.equals("0")) {
			try(UserInfoDAO ud = new UserInfoDAO()){
				ud.updatePaidVacations(emp_id, "plus");
				int num = (int) session.getAttribute("paid_vacations");
				num ++;
				session.setAttribute("paid_vacations", num);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		String confirm_message = targetYear +"/" + targetMonth + "/" + targetDay +"のデータを更新しました。";
		session.setAttribute("confirm_message", confirm_message);
			// HistoryServletにリダイレクトする
			String ServletPath =  request.getContextPath()+"/History?target_year=" + targetYear +"&target_month=" + targetMonth;
			response.sendRedirect(ServletPath);

		}

	}
