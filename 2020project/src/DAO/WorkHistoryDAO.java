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
	public void workFinish(String emp_id,Date date,Time time,String feeling) throws Exception{
		String sql = "UPDATE work_history SET finish_time = ?, feeling = ? WHERE (emp_id = ?) and (date = ?);";

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

			automaticCalculation(pt_start,pt_finish,emp_id,date); //休憩時間　基本時間　残業時間　深夜残業時間　作業時間　の自動計算


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}

	private void automaticCalculation(ProcessedTime pt_start, ProcessedTime pt_finish,String emp_id,Date date) throws Exception  {
		System.out.println("start_timeのインデックスは"+pt_start.getIndex());
		System.out.println("finish_timeのインデックスは"+pt_finish.getIndex());

		int startTime = pt_start.getIndex();
		int finishTime = pt_finish.getIndex();

		//休憩時間を求める
		int breakTime = 0;

		//12:30に出勤打刻したとき or 12:30に退勤打刻したとき休憩時間は30分
		if(startTime == 25 || finishTime == 25) {
			breakTime ++;
		}

		//出勤時刻と退勤時刻が12:00~13:00の1時間をまたいだとき、休憩時間に1時間プラス
		if(startTime <= 24 && finishTime >= 26) {
			breakTime += 2;
		}

		//退勤時刻が18:00を超えた時
		if(finishTime >= 36) {
			breakTime++;
		}
		//退勤時刻が22:30を超えた時
		if(finishTime >= 45) {
			breakTime++;
		}

		//休憩時間を元に作業時間を求める
		int workTime = 0;

		workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;

		//Time型に変換
		ProcessedTime pBreakTime = new ProcessedTime();
		pBreakTime.setIndex(breakTime);

		ProcessedTime pWorkTime = new ProcessedTime();
		pWorkTime.setIndex(workTime);

		String sql = "UPDATE work_history SET break_time = ?  , work_time = ? WHERE (emp_id = ?) and (date = ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1,pBreakTime.convertSqlTime());
			statement.setTime(2,pWorkTime.convertSqlTime());
			statement.setString(3,emp_id);
			statement.setDate(4, date);

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
				System.out.println("getTimeしたstart_time: " + rs.getTime("start_time"));
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
