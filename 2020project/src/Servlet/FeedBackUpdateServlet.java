package Servlet;

import java.io.IOException;

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
 * Servlet implementation class FeedbackUpdateServlet
 */
@WebServlet("/Servlet/FeedBackUpdate")
public class FeedBackUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータから選択したタスクidを取得する

    	request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）

		// Stringからintへ変換し、daoで処理を行う。更新対象のタスクを１件取得する。
    	int jobid = Integer.parseInt(request.getParameter("jobid"));
		int fbid = Integer.parseInt(request.getParameter("fbid"));
		String nickname = request.getParameter("nickname");
		String userid = request.getParameter("userid");
		String fbcontent = request.getParameter("fbcontent");


		// タスク１件の DTO をリクエスト属性へバインド
		request.setAttribute("jobid", jobid);
		request.setAttribute("nickname", nickname);
		request.setAttribute("fbid", fbid);
		request.setAttribute("userid", userid);
		request.setAttribute("fbcontent", fbcontent);

		// 確認画面を表示する

		String commandMessage = "更新";
		request.setAttribute("commandMessage",commandMessage);

		RequestDispatcher rd = request.getRequestDispatcher("/feedbackconfirm.jsp");
		rd.forward(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）

		// リクエストパラメータを受け取り、DTOに格納する準備をする
		int jobid = Integer.parseInt(request.getParameter("jobid"));
		int fbid = Integer.parseInt(request.getParameter("fbid"));
		String nickname = request.getParameter("nickname");
		String userid = request.getParameter("userid");
		String fbcontent = request.getParameter("fbcontent");

		// DTOへ格納する。登録される期限（limit)はTodoクラスではinputLimitになる

		FeedBackBeans dto = new FeedBackBeans();

		dto.setFbid(fbid);
		dto.setFbcontent(fbcontent);
		dto.setJobid(jobid);
		dto.setUserid(userid);
		dto.setNickname(nickname);


		String message = "";
		try(FeedBackDAO dao = new FeedBackDAO()){
			// 更新または登録処理を行う
			// id が0 の時は新規登録、id >= 1 の時は更新
			//if(jobid == 0){
				dao.registerUpdate(dto);
				message = "更新処理が完了しました。";

				HttpSession session = request.getSession(true);
				  session.setAttribute("feedbackmessage", message);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		// 登録完了 → 一覧画面を表示する
		 String ServletPath =  request.getContextPath()+"/Servlet/FeedBackSearch?jobid="+jobid;
	        response.sendRedirect(ServletPath);
	}
}