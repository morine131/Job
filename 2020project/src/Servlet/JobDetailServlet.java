package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.JobInfoDAO;
import DTO.JobInfoBeans;

/**
 * Servlet implementation class JobDetailServlet
 */
@WebServlet("/Servlet/JobDetail")
public class JobDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		// リクエストパラメータから選択したタスクidを取得する
    	String id = request.getParameter("jobid");

		// String から int へ変換し、dao で処理を行う。更新対象のタスクを１件取得する
    	JobInfoBeans dto;
		try(JobInfoDAO dao = new JobInfoDAO()){
			// intへ変換 ※NumberFormatExceptionが発生する可能性あり。チェック対象
			int jobid = Integer.parseInt(id);

			// タスク詳細結果を取得
			dto = dao.jobUpdate(jobid);
		}catch (Exception e) {
			throw new ServletException(e);
		}

		// タスク１件のvoをリクエスト属性へバインド
		request.setAttribute("dto", dto);

		// 画面を返す
		RequestDispatcher rd = request.getRequestDispatcher("/jobinput.jsp");
		rd.forward(request,response);
	}
}