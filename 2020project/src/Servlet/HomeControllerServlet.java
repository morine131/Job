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

		String forwardPath = "";
		if(admin_flg.equals("1")) {
			forwardPath = "/menu.jsp";
		}else {
			forwardPath = "/dakoku.jsp";
			start_btn_flg = CheckStart(request);
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

	private String CheckStart(HttpServletRequest request) throws ServletException {

		 HttpSession session = request.getSession();
		 String emp_id = (String) session.getAttribute("emp_id");
		 String flg = "";

		 Date date = new Date(System.currentTimeMillis()); //現在の日付

		 //SQLの実行
		 try(WorkHistoryDAO wd = new WorkHistoryDAO()){
				flg = wd.checkStart(emp_id,date);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		return flg;
	}

}
