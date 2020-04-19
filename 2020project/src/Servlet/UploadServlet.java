package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import DAO.JobInfoDAO;
import DTO.JobInfoBeans;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet( urlPatterns= {"/upload"})

//  ↓の22行目でPDFファイルの保存先を指定してる。「SougouSeisaku」の部分を自分のワークスペース名に変える必要あり 1/9 外池
@MultipartConfig( location="C:\\pleiades-2019-03-java-win-64bit_20190508\\pleiades\\SougouSeisaku\\2020project\\WebContent\\PDF")

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// <INPUT type="file" name="uploadfile">からMultipart形式のアップロードコンテンツの内容を取得

		Part part = request.getPart("uploadfile");

		// アップロードされたコンテンツ（Part）からファイル名部分を示す部分を解析し、取得する
		String pdfname = null;
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			cd = cd.trim();
			log(cd);

			if (cd.startsWith("filename")) {
				// ファイル名は=の右側以降の文字列
				// ただし利用環境によってはダブルコーテーションが含まれているので、取り除く
				pdfname = cd.substring(cd.indexOf("=")+1).trim().replace("\"","");
				log("upload file:" + pdfname);
				break;
			}
		}

		// リクエストパラメータのidを取得する
		String idStr = request.getParameter("jobid");
		log("idStr:" + idStr);
		int id = Integer.parseInt(idStr);

		// アップロードしたファイルを書き出す

		if(pdfname != null) {
			log(">> file write start.");

			// アップロードされたファイル名は、OS依存のファイルパスなどを含んでいるので置換する
			// \は/に置換し、その後ファイル名のみ抽出する
			pdfname = pdfname.replace("\\","/");

			int pos = pdfname.lastIndexOf("/");
			if(pos >= 0) {
				pdfname = pdfname.substring(pos+1);
			}
			log("filename : " +pdfname);
			part.write(pdfname);

			log("   complete!");

			// アップロードが完了した後はデータベースに登録する
			// 保存するのはファイル名のみ。完全パスは含まれない。
			JobInfoBeans dto = new JobInfoBeans();
			dto.setJobid(id);
			dto.setPdfname(pdfname);;

			try(JobInfoDAO dao = new JobInfoDAO()){
				int result = dao. uploadPDF(dto);
				log("アップロード登録結果:" + result);
			}catch(Exception e) {
				throw new ServletException(e);
			}


		}else {
			log("upload filename is blank.");
	//		message = "アップロードが失敗しました";
		}
		String message = null;
		message = "[ " +pdfname + " ]のアップロードが完了しました";
//		request.setAttribute("message", message);

		 HttpSession session = request.getSession(true);
		  session.setAttribute("message", message);

//		 String ServletPath =  request.getContextPath()+"/Servlet/JobSearch";
//	        response.sendRedirect(request.getContextPath()+"/Servlet/JobSearch");
		  String ServletPath =  request.getContextPath()+"/Servlet/JobSearch";
	        response.sendRedirect(ServletPath);

//		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
