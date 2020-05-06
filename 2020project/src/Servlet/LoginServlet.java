package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInfoDAO;
import DTO.UserInfoBeans;

/**
 * Servlet implementation class LoginServlet
 *
 * doGet ：ログイン画面を呼び出す
 * doPost：ログイン処理を実行する
 *
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 * ログイン画面を呼び出す
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String logincheck = (String)session.getAttribute("emp_id");

		if(logincheck == null) {
		// ログイン画面にフォワードする
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}else {
			// HomeControllerにリダイレクトする
			String ServletPath =  request.getContextPath()+"/Home";
		    response.sendRedirect(ServletPath);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータ受け取り時の文字フォーマット指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの取得と、DTOへの格納（ログインID、パスワード）
		UserInfoBeans dto = new UserInfoBeans();
		dto.setEmp_id(request.getParameter("emp_id"));
		dto.setPass(request.getParameter("pass"));


		// フォワードパス格納用変数
		String forwardPath = null;

		try ( UserInfoDAO dao = new UserInfoDAO() ){
			// DB検索結果を取得する
			UserInfoBeans loginUser = (UserInfoBeans) dao.loginUserCheck(dto);

			// ログイン情報が格納されている場合
			if ( loginUser.getUser_name() != null ) {

				// セッションスコープにログイン情報を格納（user_nameとemp_idとadmin_flg以外まだ）
				HttpSession session = request.getSession();
				session.setAttribute("user_name", loginUser.getUser_name());
				session.setAttribute("emp_id", loginUser.getEmp_id());
				session.setAttribute("admin_flg",loginUser.getAdmin_flg());
				session.setAttribute("user_type",loginUser.getUser_type());
				session.setAttribute("paid_vacations", loginUser.getPaid_vacations());


				// フォワードパスを指定
				if(loginUser.getAdmin_flg()=="1") {
					forwardPath = "/menu.jsp";
				}else {
					forwardPath = "/dakoku.jsp";
				}



			// ユーザ登録されていない／入力内容に誤りがある場合
			} else {
				String message = "ログイン認証に失敗しました";
				request.setAttribute("message", message);
				forwardPath = "/login.jsp";

				RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// HomeControllerにリダイレクトする
		String ServletPath =  request.getContextPath()+"/Home";
	    response.sendRedirect(ServletPath);

	}

}


