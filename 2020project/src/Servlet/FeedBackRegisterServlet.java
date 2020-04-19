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
 * Servlet implementation class FeedBackRegister
 */
@WebServlet("/Servlet/FeedBackRegister")
public class FeedBackRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedBackRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// DAOの取得
    	request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）

		// Stringからintへ変換し、daoで処理を行う。更新対象のタスクを１件取得する。
	//	FeedBackBeans dto = new FeedBackBeans();

	//		int fbid = Integer.parseInt(request.getParameter("fbid"));
			String fbcontent = request.getParameter("fbcontent");
			int jobid = Integer.parseInt(request.getParameter("jobid"));
		String userid= request.getParameter(request.getParameter("userid"));
			String nickname = request.getParameter("nickname");
//			int empid = Integer.parseInt(request.getParameter("empid"));
//			int typeid = Integer.parseInt(request.getParameter("typeid"));
//			int publish = Integer.parseInt(request.getParameter("publish"));
//			String inputvalidperiod = request.getParameter("inputvalidperiod");
//			String workarea = request.getParameter("workarea");

//			dto.setFbid(fbid);
//			dto.setFbcontent(fbcontent);
//			dto.setJobid(jobid);
//			dto.setUserid(userid);
//			dto.setNickname(nickname);
//			dto.setEmpid(empid);
//			dto.setTypeid(typeid);
//			dto.setPublish(publish);
//			dto.setInputvalidperiod(inputvalidperiod);
//			dto.setWorkarea(workarea);




		// タスク１件のvoをリクエスト属性へバインド
	//	request.setAttribute("dto", dto);
	//	request.setAttribute("fbid", fbid);
		request.setAttribute("fbcontent", fbcontent);
		request.setAttribute("jobid", jobid);
		request.setAttribute("userid", userid);
		request.setAttribute("nickname", nickname);

		//更新操作フラグをrequestに格納して
		String commandMessage = "登録";
		request.setAttribute("commandMessage",commandMessage);

		// 画面を返す
		// 検索一覧を表示する
		RequestDispatcher rd = request.getRequestDispatcher("/feedbackconfirm.jsp");
		rd.forward(request, response);
	}

    /**
	 * * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）

		// リクエストパラメータを受け取り、DTOに格納する準備をする
	//	int fbid = Integer.parseInt(request.getParameter("fbid"));
		String fbcontent = request.getParameter("fbcontent");
		int jobid = Integer.parseInt(request.getParameter("jobid"));
		String userid= request.getParameter("userid");
		String nickname = request.getParameter("nickname");

//		String company = request.getParameter("company");
//		int postcode = Integer.parseInt(request.getParameter("postcode"));
//		int salary1 = Integer.parseInt(request.getParameter("salary1"));
//		int salary2 = Integer.parseInt(request.getParameter("salary2"));
//		int empid = Integer.parseInt(request.getParameter("empid"));
//		int typeid = Integer.parseInt(request.getParameter("typeid"));
//		String inputvalidperiod = request.getParameter("inputvalidperiod");
//		String workarea = request.getParameter("workarea");
//		String pdfname = request.getParameter("pdfname");

		// DTOへ格納する。登録される期限（limit)はTodoクラスではinputLimitになる
		FeedBackBeans dto = new FeedBackBeans();
//		dto.setFbid(fbid);
		dto.setFbcontent(fbcontent);
		dto.setJobid(jobid);
		dto.setUserid(userid);
		dto.setNickname(nickname);
//		dto.setCompany(company);
//		dto.setPostcode(postcode);
//		dto.setSalary1(salary1);
//		dto.setSalary2(salary2);
//		dto.setEmpid(empid);
//		dto.setTypeid(typeid);
//		dto.setInputvalidperiod(inputvalidperiod);
//		dto.setWorkarea(workarea);
//		dto.setPdfname(pdfname);

		String message = "";
		try(FeedBackDAO dao = new FeedBackDAO()){
			// 更新または登録処理を行う
			// id が0 の時は新規登録、id >= 1 の時は更新
			//if(jobid == 0){
				dao.registerInsert(dto);
				message = "新規登録処理が完了しました。";
//			}else {
//				dao.registerUpdate(dto);
//				message = "タスク[ " + jobid + " ]のの更新処理が完了しました。";
		//	}
				HttpSession session = request.getSession(true);
				  session.setAttribute("feedbackmessage", message);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		// 登録完了 → 一覧画面を表示する
		 String ServletPath =  request.getContextPath()+"/Servlet/FeedBackSearch?jobid="+jobid;
	        response.sendRedirect(ServletPath);
	}

	/*
	 * JSPで表示するメッセージを設定する
	 *
	 * @param request
	 * 			サーブレットリクエスト
	 *
	 * @param message
	 * 			メッセージ文字列
	 */
	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}