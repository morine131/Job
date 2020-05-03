package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserInfoDAO;
import DAO.WorkHistoryDAO;

/**
 * Servlet implementation class OverTimeWorkListServlet
 */
@WebServlet("/OverTimeWorkList")
public class OverTimeWorkListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OverTimeWorkListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//パラメータが空の時＝メニューからの遷移時、現在年を対象とする
		//パラメーターに値が含まれている場合＝feelList.jspの操作でGetメソッドが呼び出された時、パラメーターで指定された年を対象とする
		int target_year = 0;
		if(request.getParameter("target_year") == null) {
		    Calendar calendar = Calendar.getInstance();
		    target_year = calendar.get(Calendar.YEAR) ;
		}else {
			target_year = Integer.parseInt(request.getParameter("target_year"));
		}

		ArrayList<String> user_list = new ArrayList<String>(); //emp_idのリスト
		ArrayList<String> name_list = new ArrayList<String>(); //nameのリスト
		HashMap <String,ArrayList<String>> map = new HashMap <String,ArrayList<String>>();

		try (UserInfoDAO ud = new UserInfoDAO()){
			user_list = ud.getUserMap().get("emp_id_list");
			name_list = ud.getUserMap().get("user_name_list");
		} catch (Exception e) {
			// TODO: handle exception
		}

		try (WorkHistoryDAO wd = new WorkHistoryDAO()){
			map = wd.getOverTimeMap(target_year,user_list,name_list);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println("テスト出力： "+ map);
		request.setAttribute("map", map);
		request.setAttribute("user_list", user_list);
		RequestDispatcher rd = request.getRequestDispatcher("/overTimeWorkList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
