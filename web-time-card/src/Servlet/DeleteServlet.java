package Servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInfoDAO;
import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
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
		String day = request.getParameter("day");

		request.setAttribute("target_year", targetYear);
		request.setAttribute("target_month", targetMonth);
		request.setAttribute("target_day", targetDay);
		request.setAttribute("day", day);

		RequestDispatcher rd = request.getRequestDispatcher("/delete.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		int targetYear = Integer.parseInt(request.getParameter("target_year"));
		int targetMonth = Integer.parseInt(request.getParameter("target_month"));
		int targetDay = Integer.parseInt(request.getParameter("target_day"));

		HttpSession session = request.getSession();
		String emp_id = (String) session.getAttribute("emp_id");

		Date date = UpdateHistoryServlet.convertSQLDate(targetYear,targetMonth,targetDay);

		Boolean isPaidHoliday = false;
		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			isPaidHoliday = wd.delete(date,emp_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(isPaidHoliday) {
			try(UserInfoDAO ud = new UserInfoDAO()){
				ud.updatePaidVacations(emp_id, "plus");
				int num = (int) session.getAttribute("paid_vacations");
				num ++;
				session.setAttribute("paid_vacations", num);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		String confirm_message = targetYear +"/" + targetMonth + "/" + targetDay +"のデータを削除しました";
		session.setAttribute("confirm_message", confirm_message);

		String ServletPath =  request.getContextPath()+"/History?target_year=" + targetYear +"&target_month=" + targetMonth;
	    response.sendRedirect(ServletPath);

	}

}
