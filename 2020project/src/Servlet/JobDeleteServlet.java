package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.JobInfoDAO;
import DTO.JobInfoBeans;

/**
  *Servlet implementation class JobDeleteServlet
**/

@WebServlet("/Servlet/JobDelete")
public class JobDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

/**
    *  @see HttpServlet#HttpServlet()
**/

/**
	*  @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
**/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）
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

		//更新操作フラグをrequestに格納して
		String commandMessage = "削除";
		request.setAttribute("commandMessage",commandMessage);

		// 画面を返す
		// 検索一覧を表示する
		RequestDispatcher rd = request.getRequestDispatcher("/jobconfirm.jsp");
		rd.forward(request, response);
	}

/**
	*  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
**/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）

		// リクエストパラメータを受け取り、DTOに格納する準備をする
		int jobid = Integer.parseInt(request.getParameter("jobid"));
//		String pdfname = request.getParameter("pdfname");

		// DTOへ格納する。登録される期限（limit)はTodoクラスではinputLimitになる
		JobInfoBeans dto = new JobInfoBeans();
		dto.setJobid(jobid);

		String message = "";
		try(JobInfoDAO dao = new JobInfoDAO()){
			// 更新または登録処理を行う
			// id が0 の時は新規登録、id >= 1 の時は更新
			//if(jobid == 0){
				dao.delete(jobid);
				message = "削除処理が完了しました。";
//			}else {
//				dao.registerUpdate(dto);
//				message = "タスク[ " + jobid + " ]のの更新処理が完了しました。";
		//	}
				 HttpSession session = request.getSession(true);
				  session.setAttribute("message", message);
			//	setMessage(request, message);
			} catch (Exception e) {
				throw new ServletException(e);
			}

		//検索一覧画面へ
		String ServletPath =  request.getContextPath()+"/Servlet/JobSearch";
       response.sendRedirect(ServletPath);

	}

	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}
