package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import DTO.WorkHistoryBeans;
import myClass.ProcessedTime;

public class WorkHistoryDAO extends DAO {

	//出勤打刻用メソッド
	public void workStart(String emp_id,Date date,Time time,String holiday) throws Exception{
		String sql = "INSERT INTO work_history (emp_id, DATE, start_time,holiday) VALUES (?, ?, ? , ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2,date);
			statement.setTime(3, time);
			statement.setString(4,holiday);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}

	//退勤打刻用メソッド
	public void workFinish(String emp_id,Date date,Time time,String feeling,String user_type) throws Exception{
		String sql = "UPDATE work_history SET finish_time = ?, feeling = ? WHERE (emp_id = ?) and (date = ?);";

		ProcessedTime pTime = new ProcessedTime(time.toString());
		Calendar cal = new Calendar.Builder().setInstant(date).build();
		Date safeDate = date; //自動計算で使うdateをエスケープしておく

		if(pTime.getIndex() <= 16) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			date.setTime(cal.getTimeInMillis());
		}

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, time);
			statement.setString(2,feeling);
			statement.setString(3, emp_id);
			statement.setDate(4, date);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		String sql2 = "SELECT * FROM work_history WHERE emp_id = ? AND `date` = ?";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql2);
			statement.setString(1, emp_id);
			statement.setDate(2, safeDate);

			// SQLを実行してその結果を取得する
			ResultSet rs2 = statement.executeQuery();

			String start_time = "";
			String finish_time = "";
			String holiday = "";

			while (rs2.next()) {
				start_time = rs2.getString("start_time");
				finish_time = rs2.getString("finish_time");
				holiday = rs2.getString("holiday");
			}
			ProcessedTime pt_start = new ProcessedTime(start_time,"start");
			ProcessedTime pt_finish = new ProcessedTime(finish_time);

			automaticCalculation(pt_start,pt_finish,emp_id,safeDate,holiday,user_type); //休憩時間　基本時間　残業時間　深夜残業時間　作業時間　の自動計算


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}

	private void automaticCalculation(ProcessedTime pt_start, ProcessedTime pt_finish,String emp_id,Date date,String holiday,String user_type) throws Exception  {

		int startTime = pt_start.getIndex();
		int finishTime = pt_finish.getIndex();
		int breakTime = 0;
		int workTime = 0;
		int standardTime = 0;
		int baseOverTime = 0;
		int lateOverTime = 0;
		int normalOverTime = 0;
		ProcessedTime pBreakTime = new ProcessedTime();
		ProcessedTime pWorkTime = new ProcessedTime();
		ProcessedTime pStandardTime = new ProcessedTime();
		ProcessedTime pNormalOverTime = new ProcessedTime();
		ProcessedTime pLateOverTime = new ProcessedTime();

		System.out.println(user_type);
		System.out.println(holiday);

		//通常勤務者の平日出勤
		if(holiday.equals("0") && user_type.equals("1")) {
			//休憩時間を求める

			//12:30に出勤打刻したとき or 12:30に退勤打刻したとき休憩時間は30分
			if(startTime == 25 || finishTime == 25) {
				breakTime ++;
			}
			if(finishTime >= startTime) {
				//出勤時刻と退勤時刻が12:00~13:00の1時間をまたいだとき、休憩時間に1時間プラス
				if(startTime <= 24 && finishTime >= 26) {
					breakTime += 2;
				}

				//退勤時刻が18:00を超えた時
				if(finishTime >= 36) {
					breakTime++;
				}
			}else {
				if(startTime <= 24) {
					breakTime += 2;
				}
				if(startTime <= 36) {
					breakTime++;
				}
			}
			//休憩時間を元に作業時間を求める
			if(startTime <= finishTime) {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}else {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime + 48;
			}
			//作業時間を元に基本時間を求める

			if(workTime >= 15) {
				standardTime = 15;
			}else {
				standardTime = workTime;
			}

			//通常残業時間と深夜残業時間を求める

			baseOverTime += workTime - standardTime;

			if(startTime <= 10) {
				lateOverTime += 10 - startTime;
			}
			if(finishTime >= 44) {
				lateOverTime += finishTime - 44;
			}
			if(finishTime <= 16) {
				lateOverTime += finishTime + 4;
			}

			normalOverTime = baseOverTime - lateOverTime;


		}
		//通常勤務者の休日出勤
		if(holiday.equals("1") && user_type.equals("1")) {
			int restraintTime = 0;
			//拘束時間を求める
			if(finishTime>=startTime) {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex();
			}else {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() + 48;
			}
			//拘束時間が6時間以上の時、１時間休憩がはいる
			if(restraintTime == 13) {
				breakTime ++;
			}
			if(restraintTime >= 14) {
				breakTime += 2;
			}

			workTime += restraintTime - breakTime;

			//通常残業時間と深夜残業時間を求める

			if(startTime <= 10) {
				lateOverTime += 10 - startTime;
			}
			if(finishTime >= 44) {
				lateOverTime += finishTime - 44;
			}
			if(finishTime <= 10) {
				lateOverTime += finishTime + 4;
			}


			normalOverTime += workTime - lateOverTime;


		}

		//フレックス勤務者の平日出勤
		if(holiday.equals("0") && user_type.equals("2")) {
			//休憩時間を求める

			//12:30に出勤打刻したとき or 12:30に退勤打刻したとき休憩時間は30分
			if(startTime == 25 || finishTime == 25) {
				breakTime ++;
			}
			if(finishTime >= startTime) {
				//出勤時刻と退勤時刻が12:00~13:00の1時間をまたいだとき、休憩時間に1時間プラス
				if(startTime <= 24 && finishTime >= 26) {
					breakTime += 2;
				}

				//退勤時刻が18:00を超えた時
				if(finishTime >= 36) {
					breakTime++;
				}
			}else {
				if(startTime <= 24) {
					breakTime += 2;
				}
				if(startTime <= 36) {
					breakTime++;
				}
			}
			//休憩時間を元に作業時間を求める
			if(startTime <= finishTime) {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}else {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime + 48;
			}
			//作業時間を元に基本時間を求める

			if(workTime >= 15) {
				standardTime = 15;
			}else {
				standardTime = workTime;
			}

			//フレックス適用者は深夜残業時間という考え方はないので、全て通常残業時間とする

			normalOverTime  += workTime - standardTime;

		}

		//フレックス勤務者の休日出勤
		if(holiday.equals("1") && user_type.equals("2")) {
			//休憩時間を求める

			int restraintTime = 0;
			//拘束時間を求める
			if(finishTime>=startTime) {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex();
			}else {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() + 48;
			}
			//拘束時間が6時間以上の時、１時間休憩がはいる
			if(restraintTime == 13) {
				breakTime ++;
			}
			if(restraintTime >= 14) {
				breakTime += 2;
			}

			workTime += restraintTime - breakTime;

			//通常残業時間と深夜残業時間を求める

			if(workTime >= 24) {
				lateOverTime += workTime - 24;
				if(lateOverTime >= 12) {
					lateOverTime = 12;
				}
			}

			normalOverTime += workTime - lateOverTime;
		}
		//Time型に変換
		pBreakTime.setIndex(breakTime);
		pWorkTime.setIndex(workTime);
		pStandardTime.setIndex(standardTime);
		pNormalOverTime.setIndex(normalOverTime);
		pLateOverTime.setIndex(lateOverTime);


		String sql = "UPDATE work_history SET break_time = ?  , work_time = ?  ,standard_time = ? ,over_time = ? ,late_over_time = ? WHERE (emp_id = ?) and (date = ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1,pBreakTime.convertSqlTime());
			statement.setTime(2,pWorkTime.convertSqlTime());
			statement.setTime(3,pStandardTime.convertSqlTime());
			statement.setTime(4,pNormalOverTime.convertSqlTime());
			statement.setTime(5,pLateOverTime.convertSqlTime());
			statement.setString(6,emp_id);
			statement.setDate(7, date);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

	}

	//退勤打刻用メソッド 退勤打刻が0:00をすぎた時用
	public void workOverFinish(String emp_id,Date date,Time time,String feeling) throws Exception{
		String sql = "UPDATE work_history SET finish_time = ?, feeling = ? WHERE (emp_id = ?) and (date = ?);";

		//dateの日付を1日前に設定する
		Calendar cal = new Calendar.Builder().setInstant(date).build();
		cal.add(Calendar.DAY_OF_MONTH, 0);
		Date startDate = new Date(0);
		startDate.setTime(cal.getTimeInMillis());

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {

			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, time);
			statement.setString(2,feeling);
			statement.setString(3, emp_id);
			statement.setDate(4, startDate);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		String sql2 = "SELECT * FROM work_history WHERE emp_id = ? AND `date` = ?";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql2);
			statement.setString(1, emp_id);
			statement.setDate(2, date);

			// SQLを実行してその結果を取得する
			ResultSet rs2 = statement.executeQuery();

			String start_time = "";
			String finish_time = "";

			while (rs2.next()) {
				start_time = rs2.getString("start_time");
				finish_time = rs2.getString("finish_time");
			}
			ProcessedTime pt_start = new ProcessedTime(start_time,"start");
			ProcessedTime pt_finish = new ProcessedTime(finish_time);

			automaticOverCalculation(pt_start,pt_finish,emp_id,date); //休憩時間　基本時間　残業時間　深夜残業時間　作業時間　の自動計算


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}

	//自動計算　退勤打刻が0:00をすぎた時用
	private void automaticOverCalculation(ProcessedTime pt_start, ProcessedTime pt_finish,String emp_id,Date date) throws Exception  {


		int startTime = pt_start.getIndex();
		int finishTime = pt_finish.getIndex();

		//休憩時間を求める
		int breakTime = 0;

		//12:30に出勤打刻したとき or 12:30に退勤打刻したとき休憩時間は30分
		if(startTime == 25 ) {
			breakTime ++;
		}

		//出勤時刻と退勤時刻が12:00~13:00の1時間をまたいだとき、休憩時間に1時間プラス
		if(startTime <= 24 ) {
			breakTime += 2;
		}

		//退勤時刻が18:00を超えた時
		if(startTime <= 35) {
			breakTime++;
		}

		//休憩時間を元に作業時間を求める
		int workTime = 0;

		workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;

		//作業時間を元に基本時間を求める
		int standardTime = 0;

		if(workTime >= 15) {
			standardTime = 15;
		}else {
			standardTime = workTime;
		}

		//通常残業時間と深夜残業時間を求める
		int baseOverTime = 0;
		if(startTime <= 17) {
			baseOverTime += 18-startTime;
		}

		baseOverTime += finishTime -36 +48;


		int lateOverTime = 0;
		if(startTime <= 10) {
			lateOverTime += 10 - startTime;
		}

		lateOverTime += finishTime - 44 + 48;


		int normalOverTime = baseOverTime - lateOverTime;

		//Time型に変換
		ProcessedTime pBreakTime = new ProcessedTime();
		pBreakTime.setIndex(breakTime);

		ProcessedTime pWorkTime = new ProcessedTime();
		pWorkTime.setIndex(workTime);

		ProcessedTime pStandardTime = new ProcessedTime();
		pStandardTime.setIndex(standardTime);

		ProcessedTime pNormalOverTime = new ProcessedTime();
		pNormalOverTime.setIndex(normalOverTime);

		ProcessedTime pLateOverTime = new ProcessedTime();
		pLateOverTime.setIndex(lateOverTime);

		String sql = "UPDATE work_history SET break_time = ?  , work_time = ?  ,standard_time = ? ,over_time = ? ,late_over_time = ? WHERE (emp_id = ?) and (date = ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1,pBreakTime.convertSqlTime());
			statement.setTime(2,pWorkTime.convertSqlTime());
			statement.setTime(3,pStandardTime.convertSqlTime());
			statement.setTime(4,pNormalOverTime.convertSqlTime());
			statement.setTime(5,pLateOverTime.convertSqlTime());
			statement.setString(6,emp_id);
			statement.setDate(7, date);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

	}

	//出勤打刻がされているか判定するメソッド
	public String checkStart(String emp_id,Date date) throws Exception{
		String sql = "SELECT start_time  FROM work_history WHERE emp_id = ? AND date = ?";
		String result = "";

		List <WorkHistoryBeans> list = new ArrayList<WorkHistoryBeans>();

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, date);

			// SQLを実行してその結果を取得する
			ResultSet rs = statement.executeQuery();


			while (rs.next()) {
				WorkHistoryBeans dto = new WorkHistoryBeans();
				dto.setStart_time(rs.getTime("start_time"));

				list.add(dto);
			}

			if( list == null || list.size() == 0) {
				result = "0";
			}else {
				result = "1";
			}

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		return result;
	}

	//月ごとのfeelList取得メソッド
	public ArrayList<String> getFeelList(String emp_id,int year, int month) throws Exception{
		String sql = "SELECT feeling ,`date` FROM work_history WHERE emp_id = ? AND `date` BETWEEN ? AND ?";

		ArrayList<String>list = new ArrayList<String>();//月の日数分の気分を返すリスト（良好：０、普通：１、イマイチ：２、データ無し：４）
		int intEndDay = getEndOfMonth(year,month);

		Date startDay = new Date(year - 1900,month-1,1);
		Date endDay = new Date(year,month-1,intEndDay);

		HashMap <String,String> map = new HashMap<String,String>();

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, startDay);
			statement.setDate(3, endDay);

			// SQLを実行してその結果を取得する
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				map.put(rs.getString("date"),rs.getString("feeling"));
			}

			String processedMonth = addZero(month);

			String base = year + "-" + processedMonth +"-";
			for(int i=1;i<=intEndDay;i++) {
				String iStr = addZero(i);
				if(map.containsKey(base+iStr)) {
					list.add(map.get(base+iStr));
				}else {
					list.add("3");
				}
			}


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
		return list;
	}

	public ArrayList<WorkHistoryBeans> getMonthHistory(String emp_id,int targetYear,int targetMonth) throws Exception{

		int intEndDay = getEndOfMonth(targetYear,targetMonth);

		Date startDay = new Date(targetYear - 1900,targetMonth-1,1);
		Date endDay = new Date(targetYear,targetMonth-1,intEndDay);

		//最終的に返すリスト
		ArrayList<WorkHistoryBeans>returnList = new ArrayList<WorkHistoryBeans>();


		String sql = "SELECT * FROM work_history WHERE emp_id = ? AND `date` BETWEEN ? AND ?";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, startDay);
			statement.setDate(3, endDay);

			// SQLを実行してその結果を取得する
			ResultSet rs = statement.executeQuery();

			HashMap <String,WorkHistoryBeans> map = new HashMap<String,WorkHistoryBeans>();

			while (rs.next()) {
				WorkHistoryBeans wb = new WorkHistoryBeans();
				wb.setDate(rs.getDate("date"));
				wb.setStart_time(rs.getTime("start_time"));
				wb.setFinish_time(rs.getTime("finish_time"));
				wb.setFeeling(rs.getString("feeling"));
				wb.setHoliday(rs.getString("holiday"));
				wb.setBreak_time(rs.getTime("break_time"));
				wb.setStandard_time(rs.getTime("standard_time"));
				wb.setOver_time(rs.getTime("over_time"));
				wb.setLate_over_time(rs.getTime("late_over_time"));
				wb.setWork_time(rs.getTime("work_time"));
				wb.setNote(rs.getString("note"));
				wb.setReason(rs.getString("reason"));

				map.put(rs.getDate("date").toString(),wb);

			}

			String processedMonth = addZero(targetMonth);
			String base = targetYear + "-" + processedMonth +"-";

			//打刻がない日にリストに詰める用の空Beans
			WorkHistoryBeans emp = new WorkHistoryBeans();

			for(int i=1;i<=intEndDay;i++) {
				String iStr = addZero(i);
				String dateStr = base + iStr;
				if(map.containsKey(dateStr)) {
					returnList.add(map.get(dateStr));
				}else {
					returnList.add(emp);
				}
			}


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
		return returnList;
	}

	//月末日を取得するメソッド
	public int getEndOfMonth(int year,int month) {
		//取得処理
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		int result = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return result;
	}

	//1〜9までのintを"01"~"09"のStringにするメソッド
	public String addZero(int num) {
		String a = num+"";
		if(a.length() == 1) {
			a = "0" +a;
		}
		return a;
	}
}
