// 10章のTodoDAOをもとに改変中 by外池　12/17
// SQLがまだできてない12/19　by外池
// ログイン用にlogin()を追加  by市橋  1/6


package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.UserInfoBeans;

/*import job.dto.job;*/

public class UserInfoDAO extends DAO {
		/**
		 * ログイン処理（ログインユーザ存在確認）  1/6 市橋
		 *   指定されたユーザIDとパスワードでDB検索し、検索結果を返す
		 *
		 * @param  UserInfoBeans<loginUser>
		 * @return
		 * @throws Exception
		 */
		public UserInfoBeans loginUserCheck(UserInfoBeans loginUser) throws Exception {
			UserInfoBeans returnList = new UserInfoBeans();

			String sql = "SELECT * FROM emp_list "
						+ " WHERE emp_id = ? AND pass = ? " ;

			// プリペアステートメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString( 1, loginUser.getEmp_id() );
			statement.setString( 2, loginUser.getPass() );

			// SQLを実行してその結果を取得する
			ResultSet rs = statement.executeQuery();

			// 検索結果の行数分フェッチを行い、取得結果をUserInfoBeansインスタンスへ格納する
			while (rs.next()) {

				// クエリー結果をVOへ格納
				returnList.setEmp_id(rs.getString("emp_id"));
				returnList.setUser_name(rs.getString("user_name"));
				returnList.setUser_type(rs.getString("user_type"));
				returnList.setAdmin_flg(rs.getString("admin_flg"));
				returnList.setPass(rs.getString("pass"));
				returnList.setDel_flg(rs.getString("del_flg"));

			}

			return returnList;
		}

		//パスワード修正用メソッド
		public void updatePass(String emp_id,String newPass) throws Exception{
			String sql = "UPDATE emp_list SET pass = ? WHERE (emp_id = ?);";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1, newPass);
				statement.setString(2,emp_id);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}

		//ユーザーリスト取得用メソッド
				public HashMap<String,ArrayList<String>> getUserMap() throws Exception{
					String sql = "SELECT * FROM emp_list WHERE admin_flg = 0 AND del_flg = 0";
					HashMap<String,ArrayList<String>> returnMap = new HashMap<String,ArrayList<String>>();

					// SQLを実行してその結果を取得し、実行SQLを渡す
					try {
						// プリペアステーメントを取得し、実行SQLを渡す
						PreparedStatement statement = getPreparedStatement(sql);

						ArrayList<String> user_name_list = new ArrayList<String>();
						ArrayList<String> emp_id_list = new ArrayList<String>();

						ResultSet rs = statement.executeQuery();
						while (rs.next()) {
							user_name_list.add(rs.getString("user_name"));
							emp_id_list.add(rs.getString("emp_id"));
						}

						returnMap.put("user_name_list", user_name_list);
						returnMap.put("emp_id_list", emp_id_list);
						// コミットを行う
						super.commit();
					}catch (Exception e) {
						super.rollback();
						throw e;
					}

					return returnMap;
				}
}
