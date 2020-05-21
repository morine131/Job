package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DTO.ReportBeans;


public class ReportsDAO extends DAO {

	public void createReport(ReportBeans rb) throws Exception {
		if( getReport(rb.getEmp_id(),rb.getDate()).getDate() == null) {

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
		}else {
			String sql = "UPDATE reports SET report = ?,text = ? WHERE emp_id = ? AND `date` = ?";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);

				statement.setString(1, rb.getReport());
				statement.setString(2, rb.getText());
				statement.setString(3, rb.getEmp_id());
				statement.setDate(4, rb.getDate());

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}
	}

	public ReportBeans getReport(String emp_id, Date date) throws Exception {
		String sql = "SELECT *  FROM reports WHERE emp_id = ? AND `date` = ?";

		ReportBeans rb = new ReportBeans();

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, date);

			ResultSet rs = statement.executeQuery();
			System.out.println("sql実行");
			System.out.println(date + " " + emp_id);

			while(rs.next()) {
				rb.setDate(rs.getDate("date"));
				rb.setEmp_id(rs.getString("emp_id"));
				rb.setReport(rs.getString("report"));
				rb.setText(rs.getString("text"));
				System.out.println(rb.getDate() + " " +rb.getReport());
			}

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		return rb;

	}
}
