/* ログイン処理  作成者：市橋 1/6
 *
 */

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

//		//遷移元URLを取得
//		String referer = request.getHeader("REFERER");
//
//		//遷移元URLからファイル名以降を取得
//		int position = referer.lastIndexOf("/");
//		if ( position >= 0 ) {
//			referer = referer.substring( position + 1 );
//		}
//
//		//★テスト用コメント出力 ２行
//		System.out.println(request.getHeader("referer"));	//★テスト用コメント出力
//		System.out.println(referer);						//★テスト用コメント出力
//
//
		HttpSession session = request.getSession();
		String logincheck = (String)session.getAttribute("emp_id");
		System.out.println("ログインチェックの値は"+logincheck);

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *
	 * ログイン処理を実行する
	 *   ログインフォームに入力された userid, password でDB検索し、
	 *   ユーザが登録されていた場合は、ユーザ情報をセッションスコープに格納する
	 *
	 * @return UserInfoBeans<loginUser>  userid, nickname, roleid
	 *
	 */
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


