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

import DAO.ReportsDAO;
import DTO.ReportBeans;

/**
 * Servlet implementation class CreateReportServlet
 */
@WebServlet("/CreateReport")
public class CreateReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/editReport.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		// 文字コード設定
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String emp_id = (String)session.getAttribute("emp_id");

		Date date = new Date(System.currentTimeMillis());

		String report = request.getParameter("report");
		String text = request.getParameter("text");

		ReportBeans rb = new ReportBeans();
		rb.setDate(date);
		rb.setEmp_id(emp_id);
		rb.setReport(report);
		rb.setText(text);

		try (ReportsDAO repo = new ReportsDAO()){
			repo.createReport(rb);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServletException(e);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/reports.jsp");
		rd.forward(request, response);
	}

}
