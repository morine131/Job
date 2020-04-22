package DTO;

import java.sql.Time;
import java.util.Calendar;

public class OutputHistoryBeans {

	private int date; //1~31までの数字
	private String day; //月〜日までの漢字１文字
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

	public int getDate() {
		return date;
	}
	public void setDate(String yyyymmdd) {
		int aDay = Integer.parseInt(yyyymmdd.substring(8, 10));
		System.out.println("aDay: " + aDay);
		this.date = aDay;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String yyyymmdd) {
		int year = Integer.parseInt(yyyymmdd.substring(0, 4));
		int month = Integer.parseInt(yyyymmdd.substring(5, 7));
		int aDay = Integer.parseInt(yyyymmdd.substring(8, 10));
		Calendar cal = new Calendar.Builder().setDate(year,month-1,aDay).build();
		String day = getDayOfTheWeek(cal);
		this.day = day;
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


	public static String getDayOfTheWeek(Calendar cal) {

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: return "日";
		case Calendar.MONDAY: return "月";
		case Calendar.TUESDAY: return "火";
		case Calendar.WEDNESDAY: return "水";
		case Calendar.THURSDAY: return "木";
		case Calendar.FRIDAY: return "金";
		case Calendar.SATURDAY: return "土";
		}
		throw new IllegalStateException();
	}
}


