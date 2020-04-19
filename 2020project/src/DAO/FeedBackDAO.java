//10章のTodoDAOをもとに作業中　飯田　1/8

package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.FeedBackBeans;

public class FeedBackDAO extends DAO {



	//就活体験談一覧表示用のメソッド
	public List<FeedBackBeans> feedbackList(int  jobid) throws Exception {
		List<FeedBackBeans> returnList = new ArrayList<FeedBackBeans>();


		String sql = "SELECT fbid, fbcontent, feedback_list.jobid, userid, nickname, fblastupdate, company  FROM feedback_list  left join job_info on job_info.jobid= feedback_list.jobid WHERE  feedback_list.jobid = ? ";
//				fbid, fbcontent, jobid, userid, nickname, fblastupdate

		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = getPreparedStatement(sql);

		statement.setInt(1,jobid);

		// SQLを実行してその結果を取得する
		ResultSet rs = statement.executeQuery();

		// 検索結果の行数分フェッチを行い、取得結果をFeedBackBeansインスタンスへ格納する
		while (rs.next()) {
			FeedBackBeans dto = new FeedBackBeans();


			// クエリー結果をVOへ格納（あらかじめクエリー結果とdtoの変数名は一致させている）
			dto.setFbid(rs.getInt("fbid"));
			dto.setFbcontent(rs.getString("fbcontent"));
			dto.setJobid(rs.getInt("jobid"));
			dto.setUserid(rs.getString("userid"));
			dto.setNickname(rs.getString("nickname"));
			dto.setFblastupdate(rs.getTimestamp("fblastupdate"));
			dto.setCompany(rs.getString("company"));

			returnList.add(dto);

		}

		return returnList;
	}





//	  新規登録処理を行う
	/* 就活体験談IDは AutoIncrement のキー項目なので、INSERT文のSQLに含めなくても自動的に最新のIDが登録される
	 *
	 * @param dto 入力された就活体験談
	 * @return 追加された件数
	 * @throws Exception
     */

	public int registerInsert(FeedBackBeans dto) throws Exception{

		String sql = "INSERT INTO feedback_list ( fbcontent, jobid, userid, nickname, fblastupdate)  VALUES(?,?,?,?,now())";

		int result = 0;
		// プリペアステートメントを取得し、実行SQL を渡す
		try {
			PreparedStatement statement = getPreparedStatement(sql);
//			statement.setInt(1,dto.getFbid());
			statement.setString(1,dto.getFbcontent());
			statement.setInt(2, dto.getJobid());
			statement.setString(3, dto.getUserid());
			statement.setString(4, dto.getNickname());
	//		statement.setTimestamp(6, dto.getFblastupdate());

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




// 就活体験談を詳細表示する
	/*
	 * 表示する就活体験談IDを指定して、就活体験談を詳細を返す。
	 * @param id 表示対象の就活体験談ID
	 * @return
	 * @throws Exception
	 */

	public FeedBackBeans feedbackUpdate(int fbid) throws Exception{
		FeedBackBeans dto = new FeedBackBeans();


		//  ここ変更中　1/9
		String sql = " SELECT fbid, fbcontent, jobid, userid, nickname FROM feedback_list WHERE fbid = ?";

		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = getPreparedStatement(sql);

		statement.setInt(1, fbid);

		// SQLを実行してその結果を取得する
		ResultSet rs = statement.executeQuery();

		// 検索結果の行数分フェッチを行い、取得結果をFeedBackBeansインスタンスへ格納する
		while (rs.next()) {
			// クエリー結果をVOへ格納（あらかじめクエリー結果とdtoの変数名は一致させている）
			dto.setFbid(rs.getInt("fbid"));
			dto.setFbcontent(rs.getString("fbcontent"));
			dto.setJobid(rs.getInt("jobid"));
			dto.setUserid(rs.getString("userid"));
			dto.setNickname(rs.getString("nickname"));
			// dto.setFblastupdate(rs.getTimestamp("fblastupdate"));

		}
		return dto;
	}




//	更新処理を行う
		 /* @param dto
		 * @return
		 * @throws Exception
         */

		public int registerUpdate(FeedBackBeans dto) throws Exception{
			String sql = "UPDATE feedback_list SET fbcontent = ?,fblastupdate = now() WHERE fbid = ? ";

			// プリペアードステートメントを取得し、実行SQLを渡す
			int result = 0;
			try {
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1,dto.getFbcontent());
				statement.setInt(2, dto.getFbid());


				result= statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
			return result;
		}




//		  削除処理を行う。(指定された就活体験談ID の就活体験談を削除する)
		 /*
		 * @param fbid
		 * @return 削除件数
		 * @throws Exception
		 */

		public int delete(FeedBackBeans dto) throws Exception{
			String sql = "DELETE FROM feedback_list WHERE fbid = ?";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			int result = 0;
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setInt(1, dto.getFbid());

				result = statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
			return result;
		}
}
