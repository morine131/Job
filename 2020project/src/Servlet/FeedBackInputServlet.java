package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DTO.FeedBackBeans;

/**
 * Servlet implementation class FeedBackInputServlet
 */
@WebServlet("/Servlet/FeedBackInput")
public class FeedBackInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackInputServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		int jobid = Integer.parseInt(request.getParameter("jobid"));
		String nickname = request.getParameter("nickname");
		String userid = request.getParameter("userid");

		// voの作成
		FeedBackBeans dto = new FeedBackBeans();

		// 新登録であることを判断するために Fbid=0 としている
		dto.setFbid(0);

		// タスク１件の DTO をリクエスト属性へバインド
		request.setAttribute("dto", dto);
		request.setAttribute("jobid", jobid);
		request.setAttribute("nickname", nickname);
		request.setAttribute("userid", userid);

		// 詳細画面を表示する
		RequestDispatcher rd = request.getRequestDispatcher("/feedbackinput.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
