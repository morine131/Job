package DAO;

import java.sql.PreparedStatement;

import DTO.ReportBeans;


public class ReportsDAO extends DAO {

	public void createReport(ReportBeans rb) throws Exception {
		String sql = "INSERT INTO reports (emp_id, `date`,report,text) VALUES (?,?,?,?)";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, rb.getEmp_id());
			statement.setDate(2, rb.getDate());
			statement.setString(3, rb.getReport());
			statement.setString(4, rb.getText());

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

	}
}
