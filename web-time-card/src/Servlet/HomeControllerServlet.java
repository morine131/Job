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

import DAO.WorkHistoryDAO;
import DTO.WorkHistoryBeans;

/**
 * Servlet implementation class HomeControllerServlet
 */
@WebServlet("/Home")
public class HomeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String admin_flg = (String)session.getAttribute("admin_flg");
		String start_btn_flg = "";//出勤打刻ボタンの活性非活性切り替えのフラグ
		String exist = "";//レコードがあるかどうかの判定


		WorkHistoryBeans wb = new WorkHistoryBeans();

		String forwardPath = "";
		if(admin_flg.equals("1")) {
			forwardPath = "/menu.jsp";
		}else {
			forwardPath = "/dakoku.jsp";
			wb = CheckStart(request,response);
			if(wb.getStart_time() == null) {
				start_btn_flg = "0";
			}else {
				start_btn_flg = "1";
			}

			if(wb.getHoliday() == null) {
				exist = "0";
			}else {
				exist = "1";
			}

			session.setAttribute("exist", exist);
			request.setAttribute("start_btn_flg", start_btn_flg);
		}

		// フォワードする
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

	private WorkHistoryBeans CheckStart(HttpServletRequest request,HttpServletResponse response) throws ServletException {

		WorkHistoryBeans wb = new WorkHistoryBeans();

		 HttpSession session = request.getSession();
		 String emp_id = (String) session.getAttribute("emp_id");

		 Date date = new Date(System.currentTimeMillis()); //現在の日付

		 //SQLの実行
		 try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				wb = wd.checkStart(emp_id,date);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		return wb;
	}

}
