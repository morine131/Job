package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.UserInfoBeans;


public class UserInfoDAO extends DAO {

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
			returnList.setPaid_vacations(rs.getInt("paid_vacations"));

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
			ArrayList<String> user_type_list = new ArrayList<String>();

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				user_name_list.add(rs.getString("user_name"));
				emp_id_list.add(rs.getString("emp_id"));
				user_type_list.add(rs.getString("user_type"));
			}

			returnMap.put("user_name_list", user_name_list);
			returnMap.put("emp_id_list", emp_id_list);
			returnMap.put("user_type_list", user_type_list);
			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		return returnMap;
	}
	//有給日数更新メソッド
	public void updatePaidVacations(String emp_id,String plus_or_minus) throws Exception{
		String sql = "";

		if(plus_or_minus.equals("plus")) {
			sql = "UPDATE emp_list SET paid_vacations  = paid_vacations +1 WHERE emp_id = ?;";
		}else {
			sql = "UPDATE emp_list SET paid_vacations  = paid_vacations -1 WHERE emp_id = ?;";
		}

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1,emp_id);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}
}
