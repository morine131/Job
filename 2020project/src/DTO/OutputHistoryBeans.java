package DTO;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;

import myClass.ProcessedTime;

public class OutputHistoryBeans {

	private int date; //1~31までの数字
	private String day; //月〜日までの漢字１文字
	private int notExist;
	private Time start_time;
	private Time finish_time;
	private String feeling;
	private String holiday;
	private String break_time;
	private String standard_time; //DB作成時のスペルミス
	private String over_time;
	private String late_over_time;
	private String work_time;
	private String note;
	private String reason;
	private String division;
	private String much_or_little;
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
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getMuch_or_little() {
		return much_or_little;
	}
	public void setMuch_or_little(String much_or_little) {
		String mark = "";
		int intMOL = Integer.parseInt(much_or_little);
		if(intMOL < 0) {
			mark = "-";
			intMOL *= -1;
		}
		ProcessedTime pt = new ProcessedTime();
		pt.setIndex(intMOL);

		this.much_or_little = mark + pt.convertHHHTime();
	}
	public int getDate() {
		return date;
	}
	public void setDate(String yyyymmdd) {
		int aDay = Integer.parseInt(yyyymmdd.substring(8, 10));
		this.date = aDay;
	}
	public void setDate(int aDay) {
		this.date = aDay;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String str,String dammy) {
		this.day = str;
	}
	public void setDay(String yyyymmdd) {
		int year = Integer.parseInt(yyyymmdd.substring(0, 4));
		int month = Integer.parseInt(yyyymmdd.substring(5, 7));
		int aDay = Integer.parseInt(yyyymmdd.substring(8, 10));
		Calendar cal = new Calendar.Builder().setDate(year,month-1,aDay).build();
		String day = getDayOfTheWeek(cal);
		this.day = day;
	}
	public void setDay(int year,int month, int aDay) {
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
	public void setHoliday(int targetYear,int targetMonth,int targetDay) {
		Calendar cal = new Calendar.Builder().setDate(targetYear, targetMonth-1 , targetDay).build();
		this.holiday = getDayOfTheWeek2(cal);
	}

	public String getBreak_time() {
		return break_time;
	}
	public void setBreak_time(Time break_time) {
		this.break_time = toOutputStr(break_time);
	}
	public String getStandard_time() {
		return standard_time;
	}
	public void setStandard_time(Time standard_time) {
		this.standard_time = toOutputStr(standard_time);
	}
	public String getOver_time() {
		return over_time;
	}
	public void setOver_time(Time over_time) {
		this.over_time = toOutputStr(over_time);
	}
	public String getLate_over_time() {
		return late_over_time;
	}
	public void setLate_over_time(Time late_over_time) {
		this.late_over_time = toOutputStr(late_over_time);
	}
	public String getWork_time() {
		return work_time;
	}
	public void setWork_time(Time work_time) {
		this.work_time = toOutputStr(work_time);
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
	public int getNotExist() {
		return notExist;
	}
	public void setNotExist(int notExist) {
		this.notExist = notExist;
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
	public static String getDayOfTheWeek2(Calendar cal) {

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: return "1";
		case Calendar.MONDAY: return "0";
		case Calendar.TUESDAY: return "0";
		case Calendar.WEDNESDAY: return "0";
		case Calendar.THURSDAY: return "0";
		case Calendar.FRIDAY: return "0";
		case Calendar.SATURDAY: return "1";
		}
		throw new IllegalStateException();
	}
	private String toOutputStr(Time time) {
		String result = time.toString().substring(0, 5);
		return result;
	}
}


