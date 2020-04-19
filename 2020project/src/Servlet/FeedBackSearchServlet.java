/*作成者：高橋、飯田　12/23　外池・安西・荒井さんたちのSerchServletを引用*/



package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.FeedBackDAO;
import DTO.FeedBackBeans;
/**
 *
 * 検索機能。就活体験談一覧情報を取得し、一覧結果(feedbacklist.jsp)へフォワードする
 *
 */
@WebServlet("/Servlet/FeedBackSearch")
public class FeedBackSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

   /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		 HttpSession session1 = request.getSession(false);
//		session1.removeAttribute("company");

		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		 int jobid = Integer.parseInt(request.getParameter("jobid"));

		 HttpSession session3 = request.getSession(false);
		 String sessionCompany = (String)session3.getAttribute("company");
		 String company =sessionCompany;
		 if(request.getParameter("company") != null) {
		 company = request.getParameter("company");
		}


		// DAOの取得
		try(FeedBackDAO dao = new FeedBackDAO()) {
			//就活体験談のリストを一覧で取得し、リクエスト属性へ格納する
			List<FeedBackBeans> list = (List<FeedBackBeans>) dao.feedbackList(jobid);

			request.setAttribute("feedbackList",list);
		}catch (Exception e) {
			throw new ServletException(e);
		}


		 HttpSession session = request.getSession(false);
		 String feedbackmessage = (String)session.getAttribute("feedbackmessage");
		request.setAttribute("feedbackmessage",feedbackmessage);

		if(sessionCompany ==null || sessionCompany != company) {
		HttpSession session2 = request.getSession(true);
		  session2.setAttribute("company", company);
		}

		request.setAttribute("jobid",jobid);
		RequestDispatcher rd = request.getRequestDispatcher("/feedbacklist.jsp");
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
