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

import DAO.JobInfoDAO;
import DTO.JobInfoBeans;

/**
 *
 * 検索機能。タスク一覧を取得し、一覧結果(index.jsp)へフォワードする
 *
 *　ここはほぼほぼ、いじらなくてよさそう　by外池
 *
 *　10章のサーブレットを流用してるので、一覧表示用のサーブレットなのに名前が「SearchServlet」のまま
 *　↑Job_SearchServletに改名決定↑
 *　
 */
@WebServlet("/Servlet/JobSearch")
public class JobSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());


		// DAOの取得
		try(JobInfoDAO dao = new JobInfoDAO()) {
			// タスクのリストを一覧で取得し、リクエスト属性へ格納する
			List<JobInfoBeans> list = (List<JobInfoBeans>) dao.jobList();

			request.setAttribute("jobList",list);



		}catch (Exception e) {
			throw new ServletException(e);
		}

		// 検索一覧を表示する

		 HttpSession session = request.getSession(false);
		 String message = (String)session.getAttribute("message");

	//	String message = request.getParameter("message");
		request.setAttribute("message",message);

		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//	doGet(request, response);

		// DAOの取得
		@SuppressWarnings("resource")
		JobInfoDAO dao = new JobInfoDAO() ;
		// タスクのリストを一覧で取得し、リクエスト属性へ格納する
		request.setCharacterEncoding("UTF-8");	// ※ 追加行（文字化けを防ぐ）
		try{

			//検索条件を保持させてindexで表示するために、
			//select要素からもらった要素はINTとStringの両方を用意する必要がある
			//INTでもらって、switch-caseでStringに入れている　1/11 外池

			String company = request.getParameter("company");
			String pref = request.getParameter("pref");
			String city = request.getParameter("city");
			int salaryA = Integer.parseInt(request.getParameter("salary1"));
			int salaryB = Integer.parseInt(request.getParameter("salary2"));
			int empstatusNumber = Integer.parseInt(request.getParameter("empstatus"));
			int typestatusNumber = Integer.parseInt(request.getParameter("typestatus"));
			int workareaNumber = Integer.parseInt(request.getParameter("workarea"));


			String empstatus= "";
			switch (empstatusNumber) {
			case 2:
			    empstatus = "正社員";
			    break;
			case 3:
				empstatus = "契約社員";
			    break;
			case 4:
				empstatus = "派遣社員";
			    break;
			case 5:
				empstatus = "請負";
			    break;
			}

			String typestatus= "";
			switch (typestatusNumber) {
			case 2:
			    typestatus = "プログラマー";
			    break;
			case 3:
				typestatus = "システムエンジニア";
			    break;
			case 4:
				typestatus = "ネットワークエンジニア";
			    break;
			case 5:
				typestatus = "運用保守";
			    break;
			case 6:
				typestatus = "サポート";
			    break;
			case 7:
				typestatus = "その他";
			    break;
			default:
			}

			String workarea= "";
			switch (workareaNumber) {
			case 2:
			    workarea = "東京";
			    break;
			case 3:
				workarea = "千葉";
			    break;
			}

			JobInfoBeans dtoSearch = new JobInfoBeans();

			dtoSearch.setCompany(company);
			dtoSearch.setPref(pref);
			dtoSearch.setCity(city);
			dtoSearch.setSalary1(salaryA);
			dtoSearch.setSalary2(salaryB);
			dtoSearch.setEmpstatus(empstatus);
			dtoSearch.setTypestatus(typestatus);
			dtoSearch.setWorkarea(workarea);


			List<JobInfoBeans> list = (List<JobInfoBeans>) dao.jobsearch(dtoSearch);

			String salary1 = "" + salaryA;
			String salary2 = "" + salaryB ;

			request.setAttribute("jobList",list);
			request.setAttribute("company",company);
			request.setAttribute("pref",pref);
			request.setAttribute("city",city);
			request.setAttribute("salary1",salary1);
			request.setAttribute("salary2",salary2);
			request.setAttribute("empstatusNumber", empstatusNumber);
			request.setAttribute("typestatusNumber", typestatusNumber);
			request.setAttribute("workareaNumber", workareaNumber);


		}catch (Exception e) {
			throw new ServletException(e);
		}

	//	 検索一覧を表示する

		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}
}
