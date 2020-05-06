package DTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class WorkHistoryBeans {
	private String emp_id;
	private Date date;
	private Time start_time;
	private Time finish_time;
	private String feeling;
	private String holiday;
	private Time break_time;
	private Time standard_time; //DB作成時のスペルミス
	private Time over_time;
	private Time late_over_time;
	private Time work_time;
	private String note;
	private String reason;
	private String much_or_little;
	private String division;
	private BigDecimal start_latitude;
	private BigDecimal finish_latitude;
	private BigDecimal start_longitude;
	private BigDecimal finish_longitude;


	public BigDecimal getStart_latitude() {
		return start_latitude;
	}
	public void setStart_latitude(BigDecimal start_latitude) {
		this.start_latitude = start_latitude;
	}
	public BigDecimal getFinish_latitude() {
		return finish_latitude;
	}
	public void setFinish_latitude(BigDecimal finish_latitude) {
		this.finish_latitude = finish_latitude;
	}
	public BigDecimal getStart_longitude() {
		return start_longitude;
	}
	public void setStart_longitude(BigDecimal start_longitude) {
		this.start_longitude = start_longitude;
	}
	public BigDecimal getFinish_longitude() {
		return finish_longitude;
	}
	public void setFinish_longitude(BigDecimal finish_longitude) {
		this.finish_longitude = finish_longitude;
	}
	public String getMuch_or_little() {
		return much_or_little;
	}
	public void setMuch_or_little(String much_or_little) {
		this.much_or_little = much_or_little;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStart_time() {
		return start_time;
	}
	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}
	public Time getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(Time finish_time) {
		this.finish_time = finish_time;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public Time getBreak_time() {
		return break_time;
	}
	public void setBreak_time(Time break_time) {
		this.break_time = break_time;
	}
	public Time getStandard_time() {
		return standard_time;
	}
	public void setStandard_time(Time standard_time) {
		this.standard_time = standard_time;
	}
	public Time getOver_time() {
		return over_time;
	}
	public void setOver_time(Time over_time) {
		this.over_time = over_time;
	}
	public Time getLate_over_time() {
		return late_over_time;
	}
	public void setLate_over_time(Time late_over_time) {
		this.late_over_time = late_over_time;
	}
	public Time getWork_time() {
		return work_time;
	}
	public void setWork_time(Time work_time) {
		this.work_time = work_time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
