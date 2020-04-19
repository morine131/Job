/**
 * ログアウト処理  作成者：市橋  1/7
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

/**
 * Servlet implementation class LogoutServelet
 *
 * ログアウト処理（セッション破棄）
 */
@WebServlet("/Logout2")
public class LogoutServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServelet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 * ログアウト前に最終確認を行う
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//遷移元URLを取得
		String referer = request.getHeader("REFERER");

		//ファイル名のみ取得
		int position = referer.lastIndexOf("/");
		if ( position >= 0 ) {
			referer = referer.substring( position + 1 );
		}

		//★テスト用コメント出力 ２行
		System.out.println(request.getHeader("referer"));	//★テスト用コメント出力
		System.out.println(referer);						//★テスト用コメント出力

		//遷移元URLをセッションに格納
		HttpSession session = request.getSession();
		session.setAttribute("referer", referer);


		// 最終確認処理の種別を、リクエスト属性に格納
		request.setAttribute( "commandMessage", "ログアウト" );

		// 最終確認画面を呼び出す
		RequestDispatcher checkDispatcher = request.getRequestDispatcher("/userconfirm.jsp");
		checkDispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *
	 * ログアウト処理を実行する
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション情報を取得
		HttpSession session = request.getSession();
		// セッションスコープの情報を破棄
		session.invalidate();

		String message = "ログアウトしました";
		request.setAttribute("message", message);

		// フォワード実行
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);


	}

}
