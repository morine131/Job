package DTO;

import java.sql.Date;

public class ReportBeans {

	Date date;
	String dateStr;
	String emp_id;
	String report;
	String text;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
		this.dateStr = date.toString();
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}


}
