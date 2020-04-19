//10章のTodoDAOをもとに改変中 by外池　12/17
//SQLがまだできてない12/19　by外池

package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.JobInfoBeans;

/*import job.dto.job;*/

public class JobInfoDAO extends DAO {


	//一覧表示用のメソッド
	public List<JobInfoBeans> jobList() throws Exception {
		List<JobInfoBeans> returnList = new ArrayList<JobInfoBeans>();

		String sql = "SELECT jobid,company,job_info.postcode,area_list.pref as pref,area_list.city as city,salary1,salary2,\r\n" +
				"job_info.empid,empstatus,job_info.typeid,typestatus,workarea,publish,validperiod,joblastupdate,pdfname\r\n" +
				"FROM job_info\r\n" +
				"left join area_list  on job_info.postcode = area_list.postcode " +
				"left join emp_status on job_info.empid = emp_status.empid " +
				"left join job_type on job_info.typeid = job_type.typeid ORDER BY joblastupdate desc";

		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = getPreparedStatement(sql);

		// SQLを実行してその結果を取得する
		ResultSet rs = statement.executeQuery();

		// 検索結果の行数分フェッチを行い、取得結果をJobInfoBeansインスタンスへ格納する
		while (rs.next()) {
			JobInfoBeans dto = new JobInfoBeans();

			// クエリー結果をVOへ格納（あらかじめクエリー結果とdtoの変数名は一致させている）
			dto.setJobid(rs.getInt("jobid"));
			dto.setCompany(rs.getString("company"));
			//	dto.setCompany_post_code(rs.getInt("company_post_code"));
			dto.setPref(rs.getString("pref"));
			dto.setCity(rs.getString("city"));



			dto.setSalary1(rs.getInt("salary1"));
			dto.setSalary2(rs.getInt("salary2"));

			dto.setEmpstatus(rs.getString("empstatus"));
			dto.setTypestatus(rs.getString("typestatus"));

			dto.setWorkarea(rs.getString("workarea"));

			dto.setPdfname(rs.getString("pdfname"));


			returnList.add(dto);
		}
		return returnList;
	}

	/*	 新規登録を行うメソッド　12/20外池がいじった
	 *
	 * 10章がベース
	 */


	public int registerInsert(JobInfoBeans dto) throws Exception{

		String sql = "INSERT INTO job_info (jobid,company,postcode,salary1,salary2,empid,typeid,publish,validperiod,"
				+ "joblastupdate,workarea) VALUES(?,?,?,?,?,?,?,?,?,now(),?)";

		int result = 0;
		// プリペアステートメントを取得し、実行SQL を渡す
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1,dto.getJobid());
			statement.setString(2,dto.getCompany());
			statement.setInt(3, dto.getPostcode());
			statement.setInt(4, dto.getSalary1());
			statement.setInt(5, dto.getSalary2());
			statement.setInt(6, dto.getEmpid());
			statement.setInt(7, dto.getTypeid());
			statement.setInt(8, dto.getPublish());
			statement.setString(9, dto.getInputvalidperiod());
			//			statement.setTimestamp(10, dto.getJoblastupdate());
			statement.setString(10,dto.getWorkarea());

			result = statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			// ロールバックを行い、スローした例外は呼ぶ出し元のクラスへ渡す
			super.rollback();
			throw e;
		}
		return result;
	}

	/*
	 * 表示するタスクの番号を指定して、タスク詳細意を返す。
	 * @param id 表示対象のタスクID
	 * @return
	 * @throws Exception
	 */


	public JobInfoBeans jobUpdate(int jobid) throws Exception{
		JobInfoBeans dto = new JobInfoBeans();

		String sql = " SELECT jobid,company,postcode,salary1,salary2,empid,typeid,publish,validperiod,workarea FROM job_info WHERE jobid = ?";

		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = getPreparedStatement(sql);

		statement.setInt(1,jobid);

		// SQLを実行してその結果を取得する
		ResultSet rs = statement.executeQuery();

		// 検索結果の行数分フェッチを行い、取得結果をJobInfoBeansインスタンスへ格納する
		while (rs.next()) {
			// クエリー結果をVOへ格納（あらかじめクエリー結果とdtoの変数名は一致させている）
			dto.setJobid(rs.getInt("jobid"));
			dto.setCompany(rs.getString("company"));
			//	dto.setCompany_post_code(rs.getInt("company_post_code"));
			dto.setPostcode(rs.getInt("postcode"));
			/*					dto.setPref(rs.getString("pref"));
								dto.setCity(rs.getString("city"));*/


			//一旦ミニマムの給与だけ表示
			dto.setSalary1(rs.getInt("salary1"));
			dto.setSalary2(rs.getInt("salary2"));
			dto.setEmpid(rs.getInt("empid"));
			dto.setTypeid(rs.getInt("typeid"));
			dto.setPublish(rs.getInt("publish"));
			dto.setValidperiod(rs.getDate("validperiod"));
			dto.setWorkarea(rs.getString("workarea"));


		}
		return dto;
	}



	/* 更新処理を行う
	 * @param dto
	 * @return
	 * @throws Exception
	 */


	public int registerUpdate(JobInfoBeans dto) throws Exception{
		String sql = "UPDATE job_info SET jobid = ?,company = ? ,postcode = ?,salary1 = ?,salary2 = ? ,"
				+ "empid = ?,typeid = ?,publish = ?, validperiod = ?, joblastupdate = now(),workarea = ? "
				+ "WHERE jobid = ? ";

		// プリペアードステートメントを取得し、実行SQLを渡す
		int result = 0;
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1,dto.getJobid());
			statement.setString(2,dto.getCompany());
			statement.setInt(3,dto.getPostcode());
			statement.setInt(4,dto.getSalary1());
			statement.setInt(5,dto.getSalary2());
			statement.setInt(6,dto.getEmpid());
			statement.setInt(7,dto.getTypeid());
			statement.setInt(8,dto.getPublish());
			statement.setString(9,dto.getInputvalidperiod());
			statement.setString(10,dto.getWorkarea());
			statement.setInt(11,dto.getJobid());

		result=	statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	return result;
	}


	public int delete(int jobid) throws Exception{
		String sql = "DELETE FROM job_info WHERE jobid = ?";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		int result = 0;
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1, jobid);;

			result = statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
		return result;
	}

//求人検索用のメソッド and検索のみ
	public List<JobInfoBeans> jobsearch(JobInfoBeans dtoSearch ) throws Exception {
		List<JobInfoBeans> returnList = new ArrayList<JobInfoBeans>();
//
//		String sql = "" ;
//		if(jobidList != null) {
//			sql = "SELECT jobid,company,job_info.postcode,area_list.pref as pref,area_list.city as city,salary1,salary2, " +
//					"job_info.empid,empstatus,job_info.typeid,typestatus,workarea,publish,validperiod,joblastupdate,pdfname " +
//					"FROM job_info " +
//					"left join area_list  on job_info.postcode = area_list.postcode " +
//					"left join emp_status on job_info.empid = emp_status.empid " +
//					"left join job_type on job_info.typeid = job_type.typeid " +
//					"WHERE company like ? " +
//					"and pref like  ? " +
//					"and city like ? " +
//					"and salary1 >= ?  " +
//					"and salary2 >= ? " +
//					"and empstatus like ? " +
//					"and typestatus like ? " +
//					"and workarea like ?  " +
//					"WHERE jobid in (?)" +
//					"ORDER BY salary1 DESC"	;

		 String sql = "SELECT jobid,company,job_info.postcode,area_list.pref as pref,area_list.city as city,salary1,salary2, " +
				"job_info.empid,empstatus,job_info.typeid,typestatus,workarea,publish,validperiod,joblastupdate,pdfname " +
				"FROM job_info " +
				"left join area_list  on job_info.postcode = area_list.postcode " +
				"left join emp_status on job_info.empid = emp_status.empid " +
				"left join job_type on job_info.typeid = job_type.typeid " +
				"WHERE company like ? " +
				"and pref like  ? " +
				"and city like ? " +
				"and salary1 >= ?  " +
				"and salary2 >= ? " +
				"and empstatus like ? " +
				"and typestatus like ? " +
				"and workarea like ?  " ;



		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = getPreparedStatement(sql);
		//sql側に％を入れると''の関係でうまく動かないので、statement.setで%を入れる
		statement.setString(1,"%"+dtoSearch.getCompany()+"%");
		statement.setString(2,"%"+dtoSearch.getPref()+"%");
		statement.setString(3,"%"+dtoSearch.getCity()+"%");
		statement.setInt(4,dtoSearch.getSalary1());
		statement.setInt(5,dtoSearch.getSalary2());
		statement.setString(6,"%"+dtoSearch.getEmpstatus()+"%");
		statement.setString(7,"%"+dtoSearch.getTypestatus()+"%");
		statement.setString(8,"%"+dtoSearch.getWorkarea()+"%");



		// SQLを実行してその結果を取得する
		ResultSet rs = statement.executeQuery();
		// 検索結果の行数分フェッチを行い、取得結果をJobInfoBeansインスタンスへ格納する
		while (rs.next()) {
			JobInfoBeans dto = new JobInfoBeans();

			// クエリー結果をVOへ格納（あらかじめクエリー結果とdtoの変数名は一致させている）
			dto.setJobid(rs.getInt("jobid"));
			dto.setCompany(rs.getString("company"));
			dto.setPref(rs.getString("pref"));
			dto.setCity(rs.getString("city"));
			dto.setSalary1(rs.getInt("salary1"));
			dto.setSalary2(rs.getInt("salary2"));
			dto.setEmpstatus(rs.getString("empstatus"));
			dto.setTypestatus(rs.getString("typestatus"));
			dto.setWorkarea(rs.getString("workarea"));
			dto.setPdfname(rs.getString("pdfname"));


			returnList.add(dto);
		}
		return returnList;
	}



		//PDFファイル名の登録用メソッド

		public int uploadPDF (JobInfoBeans dto) throws Exception{
			String sql = "UPDATE job_info SET pdfname = ? WHERE jobid = ?";

			int result = 0;
			// プリペアステートメントを取得し、実行SQLを渡す
			try{
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1, dto.getPdfname());
				statement.setInt(2, dto.getJobid());

				// 登録を行う
			result = statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
				return result;
		}


	//まず一覧表示から作ってるので、ほかのメソッドはいったんコメントアウト

	/*
	 * 表示するタスクの番号を指定して、タスク詳細意を返す。
	 * @param id 表示対象のタスクID
	 * @return
	 * @throws Exception


		public job detail(int id) throws Exception{
			job dto = new job();

			String sql = "SELECT id,title,task,limitdate,lastupdate,userid,label,td.status,filename FROM job_list td LEFT JOIN status_list stts ON stts.status = td.status where id = ?";

			// プリペアステートメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setInt(1, id);;

			// SQLを実行してその結果を取得する
			ResultSet  rs = statement.executeQuery();

			// 検索結果の行数分フェッチを行い、取得結果をDTOへ格納する
			while(rs.next()) {
				// クエリー結果をDTOへ格納（あらかじめクエリー結果とDTOの変数名は一致させている）
				dto.setId(rs.getInt("id"));
				dto.setTitle(rs.getString("title"));
				dto.setTask(rs.getString("task"));
				dto.setLimitdate(rs.getTimestamp("limitdate"));
				dto.setLastupdate(rs.getTimestamp("lastupdate"));
				dto.setUserid(rs.getString("userid"));
				dto.setLabel(rs.getString("label"));
				dto.setStatus(rs.getInt("status"));
				dto.setFilename(rs.getString("filename"));
			}
			return dto;
		}

		  新規登録処理を行う
	 * タスクIDは AutoIncrement のキー項目なので、INSERT文のSQLに含めなくても自動的に最新のIDが登録される
	 *
	 * @param dto 入力されたタスク内容
	 * @return 追加された件数
	 * @throws Exception


		public int registerInsert(job dto) throws Exception{

			String sql = "INSERT INTO job_list (title,task,limitdate,lastupdate,userid,status) VALUES(?,?,?,now(),?,?)";

			int result = 0;
			// プリペアステートメントを取得し、実行SQL を渡す
			try {
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1,dto.getTitle());
				statement.setString(2,dto.getTask());
				statement.setString(3, dto.getInputLimitdate());
				statement.setString(4, dto.getUserid());
				statement.setInt(5, dto.getStatus());

				result = statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				// ロールバックを行い、スローした例外は呼ぶ出し元のクラスへ渡す
				super.rollback();
				throw e;
			}
			return result;
		}

		 更新処理を行う
	 * @param dto
	 * @return
	 * @throws Exception


		public int registerUpdate(job dto) throws Exception{
			String sql = "UPDATE job_list SET title = ?,task = ? ,limitdate = ?,lastupdate = now(),userid = ?,status = ? WHERE id = ?";

			// プリペアードステートメントを取得し、実行SQLを渡す
			int result = 0;
			try {
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1,dto.getTitle());
				statement.setString(2, dto.getTask());
				statement.setString(3, dto.getInputLimitdate());
				statement.setString(4, dto.getUserid());
				statement.setInt(5,dto.getStatus());
				statement.setInt(6, dto.getId());

				result= statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
			return result;
		}

		  削除処理を行う。指定されたid のタスクを削除する
	 *
	 * @param id
	 * @return 削除件数
	 * @throws Exception


		public int delete(int id) throws Exception{
			String sql = "DELETE FROM job_list WHERE id = ?";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			int result = 0;
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setInt(1, id);;

				result = statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
			return result;
		}


	 * タスクに添付されるアップロードされたファイル情報を更新する
	 * @param VO
	 * @throws Exception
	 *

		public int updateUploadInfo(job dto) throws Exception{
			String sql = "UPDATE job_list SET filename = ? WHERE id = ?";
			int result = 0;
			// プリペアステートメントを取得し、実行SQLを渡す
			try{
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1, dto.getFilename());
				statement.setInt(2, dto.getId());

				// 登録を行う
				result = statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
			return result;
		}*/
}
