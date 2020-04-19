//   飯田作成中　1/7


package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.FeedBackDAO;
import DTO.FeedBackBeans;

/**
 * Servlet implementation class FeedBackDetailServlet
 */
@WebServlet("/Servlet/FeedBackDetail")
public class FeedBackDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// リクエストパラメータから選択した就活体験談IDを取得する

		int fbid = Integer.parseInt(request.getParameter("fbid"));

	// String から int へ変換し、dao で処理を行う。更新対象のタスクを１件取得する
	FeedBackBeans dto;
	try(FeedBackDAO dao = new FeedBackDAO()){
		// intへ変換 ※NumberFormatExceptionが発生する可能性あり。チェック対象
	//	int fbid = Integer.parseInt("fbid");
	//	int jobid = Integer.parseInt("jobid");

		// 就活体験談詳細結果を取得
		dto = dao.feedbackUpdate(fbid);
		}catch (Exception e) {
		throw new ServletException(e);
	}

	// 就活体験談１件のvoをリクエスト属性へバインド
	request.setAttribute("dto", dto);
//	request.setAttribute("jobid", jobid);

	// 画面を返す
	RequestDispatcher rd = request.getRequestDispatcher("/feedbackinput.jsp");
	rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}
}
