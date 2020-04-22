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
		HttpSession session = request.getSession();
		String emp_id = (String)session.getAttribute("emp_id");

		Calendar calendar = Calendar.getInstance();
		int targetYear = calendar.get(Calendar.YEAR);

		int targetMonth = calendar.get(Calendar.MONTH) + 1;
		System.out.println("targetMonth: "+ targetMonth);

		ArrayList<WorkHistoryBeans>list = new ArrayList<WorkHistoryBeans>();
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){

			list = wd.getMonthHistory(emp_id,targetYear,targetMonth);

		} catch (Exception e) {
			throw new ServletException(e);
		}
		ArrayList<OutputHistoryBeans>resultList = new ArrayList<OutputHistoryBeans>();

		for (WorkHistoryBeans wb : list) {
			OutputHistoryBeans ob = new OutputHistoryBeans();
			if(wb.getDate()!= null) {
				ob.setDate(wb.getDate().toString());
				ob.setDay(wb.getDate().toString());
				ob.setStart_time(wb.getStart_time());
				ob.setFinish_time(wb.getFinish_time());
				ob.setFeeling(wb.getFeeling());
				ob.setHoliday(wb.getHoliday());
				ob.setBreak_time(ob.getBreak_time());
				ob.setStandard_time(wb.getStandard_time());
				ob.setOver_time(wb.getOver_time());
				ob.setLate_over_time(wb.getLate_over_time());
				ob.setWork_time(wb.getWork_time());
				ob.setNote(wb.getNote());
				ob.setReason(wb.getReason());
			}
			resultList.add(ob);
		}
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
