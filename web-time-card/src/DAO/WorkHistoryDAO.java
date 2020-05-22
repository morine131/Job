package DAO;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import DTO.OutputHistoryBeans;
import DTO.WorkHistoryBeans;
import myClass.ProcessedTime;

public class WorkHistoryDAO extends DAO {

	//出勤打刻用メソッド
	public void workStart(String emp_id,Date date,Time time,String holiday,BigDecimal latitude,BigDecimal longitude ,String exist) throws Exception{

		if(exist.equals("0")) {
			String sql = "INSERT INTO work_history (emp_id, DATE, start_time,holiday,start_latitude,start_longitude) VALUES (?, ?, ? , ?,?,?);";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1, emp_id);
				statement.setDate(2,date);
				statement.setTime(3, time);
				statement.setString(4,holiday);
				statement.setBigDecimal(5, latitude);
				statement.setBigDecimal(6, longitude);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}else {
			String sql = "UPDATE work_history SET start_time = ?,start_latitude = ?,start_longitude = ? WHERE emp_id = ? AND `date` = ?;";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);

				statement.setTime(1, time);
				statement.setBigDecimal(2, latitude);
				statement.setBigDecimal(3, longitude);
				statement.setString(4, emp_id);
				statement.setDate(5,date);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}
	}

	public void workStart(String emp_id,Date date,Time time,String holiday,BigDecimal latitude,BigDecimal longitude ,String exist , String reason) throws Exception{

		if(exist.equals("0")) {
			String sql = "INSERT INTO work_history (emp_id, DATE, start_time,holiday,start_latitude,start_longitude,reason) VALUES (?, ?, ? , ?,?,?,?);";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setString(1, emp_id);
				statement.setDate(2,date);
				statement.setTime(3, time);
				statement.setString(4,holiday);
				statement.setBigDecimal(5, latitude);
				statement.setBigDecimal(6, longitude);
				statement.setString(7,reason);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}else {
			String sql = "UPDATE work_history SET start_time = ?,start_latitude = ?,start_longitude = ? ,holiday = ?WHERE emp_id = ? AND `date` = ?;";

			// SQLを実行してその結果を取得し、実行SQLを渡す
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);

				statement.setTime(1, time);
				statement.setBigDecimal(2, latitude);
				statement.setBigDecimal(3, longitude);
				statement.setString(4, holiday);
				statement.setString(5, emp_id);
				statement.setDate(6,date);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				super.rollback();
				throw e;
			}
		}
	}


	//出勤打刻がされている時の退勤打刻用メソッド
	public void workFinish(String emp_id,Date date,Time time,String feeling,String user_type ,BigDecimal latitude,BigDecimal longitude ,String note) throws Exception{


		String sql = "UPDATE work_history SET finish_time = ?, feeling = ?,finish_latitude = ?,finish_longitude = ?,note = ? WHERE (emp_id = ?) and (date = ?);";

		Date safeDate = date; //自動計算で使うdateをエスケープしておく

		if(note == null) {
			note = "";
		}

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, time);
			statement.setString(2,feeling);
			statement.setBigDecimal(3, latitude);
			statement.setBigDecimal(4, longitude);
			statement.setString(5, note);
			statement.setString(6, emp_id);
			statement.setDate(7, date);

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
			String holiday = "";

			while (rs2.next()) {
				start_time = rs2.getString("start_time");
				finish_time = rs2.getString("finish_time");
				holiday = rs2.getString("holiday");
			}

			System.out.println(date + " " + start_time );
			ProcessedTime pt_start = new ProcessedTime(start_time,"start");
			ProcessedTime pt_finish = new ProcessedTime(finish_time);

			automaticCalculation(pt_start,pt_finish,emp_id,safeDate,holiday,user_type); //遅刻・早退・午前休・午後休の判定　休憩時間　基本時間　超過・不足　残業時間　深夜残業時間　作業時間　の自動計算


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}
	}

	//出勤打刻も平日休日の登録もされていない時の退勤打刻用メソッド
	public void workFinish_new(String emp_id, Date date, Time time, String feeling, String user_type,
			BigDecimal latitude, BigDecimal longitude, String note) throws Exception {
		String sql = "INSERT INTO work_history (emp_id, DATE, finish_time,finish_latitude,finish_longitude,feeling) VALUES (?, ?, ? ,?,?,?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2,date);
			statement.setTime(3, time);
			statement.setBigDecimal(4, latitude);
			statement.setBigDecimal(5, longitude);
			statement.setString(6, feeling);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

	}

	//出勤打刻がなしで、退勤打刻されている時の退勤打刻上書きメソッド
	public void workFinish_update(String emp_id, Date date, Time time, String feeling, String user_type,
			BigDecimal latitude, BigDecimal longitude) throws Exception {

		String sql = "UPDATE work_history SET finish_time = ?, feeling = ?,finish_latitude = ?,finish_longitude = ? WHERE (emp_id = ?) and (`date` = ?);";

		try {
			PreparedStatement  statement = getPreparedStatement(sql);
			System.out.println("time " +time);
			System.out.println("date " +date);
			System.out.println("emo_id "+emp_id);
			statement.setTime(1, time);
			statement.setString(2, feeling);
			statement.setBigDecimal(3, latitude);
			statement.setBigDecimal(4, longitude);
			statement.setString(5, emp_id);
			statement.setDate(6, date);

			statement.executeUpdate();
			System.out.println("updateされた");
			// コミットを行う
			super.commit();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			System.out.println("エラー発生");
			e.printStackTrace();
			super.rollback();
			throw e;
		}

	}

	public void automaticCalculation(ProcessedTime pt_start, ProcessedTime pt_finish,String emp_id,Date date,String holiday,String user_type) throws Exception  {

		int startTime = pt_start.getIndex();
		int finishTime = pt_finish.getIndex();
		int breakTime = 0;
		int workTime = 0;
		int standardTime = 0;
		int lateOverTime = 0;
		int normalOverTime = 0;
		int muchOrLittle = 0;
		String division = "";
		ProcessedTime pBreakTime = new ProcessedTime();
		ProcessedTime pWorkTime = new ProcessedTime();
		ProcessedTime pStandardTime = new ProcessedTime();
		ProcessedTime pNormalOverTime = new ProcessedTime();
		ProcessedTime pLateOverTime = new ProcessedTime();
		ProcessedTime pMuchOrLittle = new ProcessedTime();

		//通常勤務者の平日出勤
		if(holiday.equals("0") && user_type.equals("1")) {
			//divisionの判定
			//出勤時刻が12:00以降なら午前休
			if(startTime >= 24) {
				division += "午前休";
			}
			//24時までに退社している、かつ、13:00以前に退勤している時
			if(finishTime >= startTime && finishTime <= 26) {
				division += "午後休";
			}
			//午前休の場合の遅刻早退判定
			if(division.equals("午前休")) {
				//13:00より出勤が遅いなら
				if(startTime > 26) {
					division += "遅刻";
				}
				//17:30より退勤が早いなら
				if(finishTime < 35 && finishTime >= startTime) {
					division += "早退";
				}
				//午後休の時の遅刻早退判定
			}else if(division.equals("午後休")) {
				//9:00より出勤が遅いなら
				if(startTime > 18) {
					division += "遅刻";
				}
				//12:00より退勤が早いなら
				if(finishTime < 24) {
					division += "早退";
				}
				//通常出勤の時の遅刻早退判定
			}else {
				//9:00より遅い出勤なら
				if(startTime > 18) {
					division += "遅刻";
				}
				//17:30より早い退勤なら
				if(finishTime >= startTime && finishTime < 35) {
					division += "早退";
				}
			}

			//休憩時間を求める
			//12:30に出勤打刻したとき or 12:30に退勤打刻したとき休憩時間は30分
			if(startTime == 25 || finishTime == 25) {
				breakTime ++;
			}
			//退勤が0:00を超えている時
			if(finishTime < startTime) {
				//出勤時刻が12:00以前なら、休憩時間に1時間プラス
				if(startTime <= 24) {
					breakTime += 2;
				}
				//出勤時刻が17:30以前なら、休憩時間に30分プラス
				if(startTime <= 35 ) {
					breakTime++;
				}
				//23:30までに退勤している時
			}else {
				//出勤時刻と退勤時刻が12:00~13:00を跨ぐなら
				if(startTime <= 24 && finishTime >= 26) {
					breakTime += 2;
				}
				//出勤時刻と退勤時刻が17:30~18:00を跨ぐなら
				if(startTime <= 35 && finishTime >= 36) {
					breakTime++;
				}
			}

			//休憩時間を元に作業時間を求める
			if(startTime <= finishTime) {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}else {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}
			//作業時間を元に基本時間を求める

			if(division.indexOf("午前休") != -1) {
				if(workTime >= 9) {
					standardTime = 9;
				}else {
					standardTime = workTime;
				}
			}
			else if(division.indexOf("午後休") != -1) {
				if(workTime >= 6) {
					standardTime = 6;
				}else {
					standardTime = workTime;
				}
			}else {
				if(workTime >= 15) {
					standardTime = 15;
				}else {
					standardTime = workTime;
				}
			}

			//超過・不足と通常残業時間と深夜残業時間を求める

			if(division.indexOf("午前休") != -1 ) {
				muchOrLittle = workTime -9;
				if(muchOrLittle > 0) {
					if(finishTime > 44) {
						lateOverTime += finishTime - 44;
					}
					if(finishTime <= 16) {
						if(finishTime >= 10) {
							lateOverTime += 14;
						}else {
							lateOverTime += 4 + finishTime;
						}
					}
					normalOverTime = muchOrLittle - lateOverTime ;
				}
			}
			else if(division.indexOf("午後休") != -1 ) {
				muchOrLittle = workTime - 6;
				if(workTime - 6 > 0) {
					if(startTime <= 10 ) {
						lateOverTime +=  10 - startTime;
					}
					normalOverTime = muchOrLittle - lateOverTime ;
				}
			}else {
				muchOrLittle = workTime - 15;
				if(muchOrLittle > 0) {
					if(finishTime > 44) {
						lateOverTime += finishTime - 44;
					}
					if(finishTime <= 16) {
						if(finishTime >= 10) {
							lateOverTime += 14;
						}else {
							lateOverTime += 4 + finishTime;
						}
					}
					if(startTime <= 10 ) {
						lateOverTime +=  10 - startTime;
					}

					normalOverTime = muchOrLittle - lateOverTime ;
				}
			}



		}
		//通常勤務者の休日出勤
		if(holiday.equals("1") && user_type.equals("1")) {

			division = "休日出勤";

			int restraintTime = 0;
			//拘束時間を求める
			if(finishTime>=startTime) {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex();
			}else {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex();
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
			//勤務区分を求める
			if(startTime > 22) {
				division += "遅刻";
			}
			if(finishTime < 30 && finishTime >= startTime) {
				division += "早退";
			}
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
				if(startTime <= 35 && finishTime >= 36) {
					breakTime++;
				}
			}else {
				if(startTime <= 24 && finishTime >=26) {
					breakTime += 2;
				}
				if(startTime <= 35 && finishTime >=36 ) {
					breakTime++;
				}
			}
			//休憩時間を元に作業時間を求める
			if(startTime <= finishTime) {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}else {
				workTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() - breakTime;
			}
			//作業時間を元に基本時間を求める

			if(workTime >= 15) {
				standardTime = 15;
			}else {
				standardTime = workTime;
			}

			//作業時間を元に日毎の超過・不足を求める
			muchOrLittle = workTime -15;

			//フレックス適用者は深夜残業時間という考え方はないので、全て通常残業時間とする

			normalOverTime  += workTime - standardTime;

		}

		//フレックス勤務者の休日出勤
		if(holiday.equals("1") && user_type.equals("2")) {
			division = "休日出勤";

			int restraintTime = 0;
			//拘束時間を求める
			if(finishTime>=startTime) {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex();
			}else {
				restraintTime = ProcessedTime.getDiff(pt_finish, pt_start).getIndex() ;
			}
			//拘束時間が6時間以上の時、１時間休憩がはいる
			if(restraintTime == 13) {
				breakTime ++;
			}
			if(restraintTime >= 14) {
				breakTime += 2;
			}

			workTime += restraintTime - breakTime;
			System.out.println("発生中");
			//通常残業時間と深夜残業時間を求める

			if(workTime >= 24) {
				lateOverTime += workTime - 24;
				System.out.println("lateOverTime: " + lateOverTime + " workTime: " + workTime);
				if(lateOverTime >= 12) {
					lateOverTime = 12;
				}
			}

			normalOverTime += workTime - lateOverTime;
		}

		//有給の時
		if(holiday.equals("2")) {
			division = "有給休暇";
		}
		//Time型に変換
		pBreakTime.setIndex(breakTime);
		pWorkTime.setIndex(workTime);
		pStandardTime.setIndex(standardTime);
		pNormalOverTime.setIndex(normalOverTime);
		pLateOverTime.setIndex(lateOverTime);
		pMuchOrLittle.setIndex(muchOrLittle);


		String sql = " UPDATE work_history SET break_time = ?  , work_time = ?  ,standard_time = ? ,over_time = ? ,late_over_time = ? , much_or_little = ?,division = ? WHERE (emp_id = ?) and (date = ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1,pBreakTime.convertSqlTime());
			statement.setTime(2,pWorkTime.convertSqlTime());
			statement.setTime(3,pStandardTime.convertSqlTime());
			statement.setTime(4,pNormalOverTime.convertSqlTime());
			statement.setTime(5,pLateOverTime.convertSqlTime());
			statement.setString(6,muchOrLittle + "");
			statement.setString(7, division);
			statement.setString(8,emp_id);
			statement.setDate(9, date);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

	}


	//レコードがあるか判定するメソッド
	public WorkHistoryBeans checkStart(String emp_id,Date date) throws Exception{
		String sql = "SELECT *  FROM work_history WHERE emp_id = ? AND date = ?";

		WorkHistoryBeans dto = new WorkHistoryBeans();


		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, date);

			// SQLを実行してその結果を取得する
			ResultSet rs = statement.executeQuery();


			while (rs.next()) {
				dto.setHoliday(rs.getString("holiday"));
				dto.setStart_time(rs.getTime("start_time"));
			}


			// コミットを行う
			super.commit();
		}catch (Exception e) {
			super.rollback();
			throw e;
		}

		return dto;
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

	//勤務表を表示するメソッド
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
				wb.setMuch_or_little(rs.getString("much_or_little"));
				wb.setOver_time(rs.getTime("over_time"));
				wb.setLate_over_time(rs.getTime("late_over_time"));
				wb.setWork_time(rs.getTime("work_time"));
				wb.setNote(rs.getString("note"));
				wb.setReason(rs.getString("reason"));
				wb.setDivision(rs.getString("division"));
				wb.setStart_latitude(rs.getBigDecimal("start_latitude"));
				wb.setStart_longitude(rs.getBigDecimal("start_longitude"));
				wb.setFinish_latitude(rs.getBigDecimal( "finish_latitude"));
				wb.setFinish_longitude(rs.getBigDecimal("finish_longitude"));

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

	//対象日付の勤務データを取得するメソッド
	public OutputHistoryBeans getDateHistory(Date date, String emp_id) {
		OutputHistoryBeans ob = new OutputHistoryBeans();

		String sql = "SELECT * FROM work_history WHERE emp_id = ? AND `date`= ?";
		try {
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2, date);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				ob.setFinish_time_hhmm(rs.getTime("finish_time"));
				ob.setDate(rs.getDate("date").toString());
				ob.setDay(rs.getDate("date").toString());
				ob.setStart_time(rs.getTime("start_time"));
				ob.setStart_latitude(rs.getBigDecimal("start_latitude"));
				ob.setStart_longitude(rs.getBigDecimal("start_longitude"));
				ob.setFinish_latitude(rs.getBigDecimal("finish_latitude"));
				ob.setFinish_longitude(rs.getBigDecimal("finish_longitude"));
				ob.setFinish_time(rs.getTime("finish_time"));
				ob.setStart_time_hhmm(rs.getTime("start_time"));
				System.out.println("ここまで発生");
				ob.setFeeling(rs.getString("feeling"));
				ob.setHoliday(rs.getString("holiday"));
				ob.setBreak_time(rs.getTime("break_time"));
				ob.setStandard_time(rs.getTime("standard_time"));
				ob.setMuch_or_little(rs.getString("much_or_little"));
				ob.setOver_time(rs.getTime("over_time"));
				ob.setLate_over_time(rs.getTime("late_over_time"));
				ob.setWork_time(rs.getTime("work_time"));
				ob.setDivision(rs.getString("division"));
				ob.setNote(rs.getString("note"));
				ob.setReason(rs.getString("reason"));
			}


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("ob.getFinish" + ob.getFinish_time());
		System.out.println("ob.getFinish_hhmm " + ob.getFinish_time_hhmm());
		return ob;
	}
	//データベースの最小年と最大年を取得するメソッド
	public ArrayList<Integer> getYearList() {
		ArrayList<Integer> list = new ArrayList<Integer>();

		String maxStr = "";
		String minStr = "";


		try {
			String sql = "select  max(`date`) as max_date, min(`date`) as min_date from work_history;";

			PreparedStatement statement = getPreparedStatement(sql);
			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				maxStr = rs.getDate("max_date").toString();
				minStr = rs.getDate("min_date").toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		int maxYear = Integer.parseInt(maxStr.substring(0, 4));
		int minYear = Integer.parseInt(minStr.substring(0, 4));

		while(minYear <= maxYear) {
			list.add(minYear);
			minYear++;
		}
		return list;
	}
	//修正用メソッド
	public void updateHistory(String emp_id,Date date,Time start_time, Time finish_time, String feeling, Time break_time,
			Time standard_time, Time over_time, Time late_over_time, Time work_time, String note, String reason,
			String division, String much_or_little,BigDecimal start_latitude,BigDecimal start_longitude,BigDecimal finish_latitude, BigDecimal finish_longitude,Boolean isAuto,int flag,String user_type) throws Exception {
		// TODO 自動生成されたメソッド・スタブ


		String exist = "";
		if(flag == 2) {
			exist = "0";
		}
		//出勤退勤打刻あり、出勤打刻のみ、自動計算オフ
		if ((flag == 0 || flag == 1)  && !isAuto ) {

			String sql = " UPDATE work_history SET start_time = ?,finish_time = ?, feeling = ? ,break_time = ? , work_time = ?  ,standard_time = ? ,over_time = ? ,late_over_time = ? , much_or_little = ?, division = ? ,reason = ? , note = ? ,start_latitude = ?,start_longitude = ?,finish_latitude = ?,finish_longitude = ? WHERE (emp_id = ?) and (date = ?);";

			//出退勤済みのメソッド
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, start_time);
			statement.setTime(2, finish_time);
			statement.setString(3, feeling);
			statement.setTime(4, break_time);
			statement.setTime(5, work_time);
			statement.setTime(6,standard_time);
			statement.setTime(7, over_time);
			statement.setTime(8, late_over_time);
			statement.setString(9, much_or_little);
			statement.setString(10, division);
			statement.setString(11, reason);
			statement.setString(12, note);
			statement.setBigDecimal(13, start_latitude);
			statement.setBigDecimal(14, start_longitude);
			statement.setBigDecimal(15, finish_latitude);
			statement.setBigDecimal(16, finish_longitude);
			statement.setString(17, emp_id);
			statement.setDate(18,date);

			// コミットを行う
			try {
				statement.executeUpdate();

				super.commit();
			}catch (Exception e) {
				super.rollback();
				e.printStackTrace();
				throw e;

			}
			//打刻なし、自動計算オフ
		} else if ((flag == 2 || flag == 3) && !isAuto ) {

			String holiday = getHoliday(date);
			workStart(emp_id,date,start_time,holiday,start_latitude, start_longitude,exist);

			String sql = " UPDATE work_history SET start_time = ?,finish_time = ?, feeling = ? ,break_time = ? , work_time = ?  ,standard_time = ? ,over_time = ? ,late_over_time = ? , much_or_little = ?, division = ? ,reason = ? , note = ? ,start_latitude = ?,start_longitude = ?,finish_latitude = ?,finish_longitude = ? WHERE (emp_id = ?) and (date = ?);";

			//出退勤済みのメソッド
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, start_time);
			statement.setTime(2, finish_time);
			statement.setString(3, feeling);
			statement.setTime(4, break_time);
			statement.setTime(5, work_time);
			statement.setTime(6,standard_time);
			statement.setTime(7, over_time);
			statement.setTime(8, late_over_time);
			statement.setString(9, much_or_little);
			statement.setString(10, division);
			statement.setString(11, reason);
			statement.setString(12, note);
			statement.setBigDecimal(13, start_latitude);
			statement.setBigDecimal(14, start_longitude);
			statement.setBigDecimal(15, finish_latitude);
			statement.setBigDecimal(16, finish_longitude);
			statement.setString(17, emp_id);
			statement.setDate(18,date);

			// コミットを行う
			try {
				statement.executeUpdate();

				super.commit();
			}catch (Exception e) {
				super.rollback();
				e.printStackTrace();
				throw e;

			}
			//出勤退勤打刻あり、出勤打刻のみ、自動計算オン
		} else if ((flag == 0 || flag == 1)  && isAuto ) {
			String sql = "UPDATE work_history SET start_time = ?,holiday = ?,start_latitude = ? ,start_longitude = ? ,reason = ? WHERE emp_id = ? AND `date` = ?;";
			try {
				// プリペアステーメントを取得し、実行SQLを渡す
				PreparedStatement statement = getPreparedStatement(sql);
				statement.setTime(1, start_time);
				statement.setString(2,getHoliday(date));
				statement.setBigDecimal(3, start_latitude);
				statement.setBigDecimal(4, start_longitude);
				statement.setString(5, reason);
				statement.setString(6, emp_id);
				statement.setDate(7,date);

				statement.executeUpdate();

				// コミットを行う
				super.commit();
			}catch (Exception e) {
				e.printStackTrace();
				super.rollback();
				throw e;
			}

			workFinish(emp_id,date,finish_time,feeling,user_type ,finish_latitude,finish_longitude,note);

			//打刻なし、自動計算オン
		}else if((flag == 2 || flag == 3 )&& isAuto ) {
			String holiday = getHoliday(date);
			workStart(emp_id,date,start_time,holiday,start_latitude, start_longitude,exist,reason);
			workFinish(emp_id,date,finish_time,feeling,user_type ,finish_latitude,finish_longitude,note);

		}
	}
	private String getHoliday(Date date) {
		String dateStr = date.toString();
		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(5, 7));
		int day = Integer.parseInt(dateStr.substring(8, 10));
		Calendar  cal =  new Calendar.Builder().setDate(year, month-1, day).build();

		String result = "0";
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY: result =  "1" ;
		break;
		case Calendar.MONDAY: result =  "0";
		break;
		case Calendar.TUESDAY: result =  "0";
		break;
		case Calendar.WEDNESDAY: result =  "0";
		break;
		case Calendar.THURSDAY: result =  "0";
		break;
		case Calendar.FRIDAY: result =  "0";
		break;
		case Calendar.SATURDAY: result =  "1";
		break;
		}
		return result;
	}

	//残業一覧を取得するメソッド
	public HashMap<String, ArrayList<String>> getOverTimeMap(int target_year,ArrayList<String> user_list,ArrayList<String> name_list) throws Exception {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		String sql = "SELECT sec_to_time(sum(time_to_sec(over_time))+sum(time_to_sec(late_over_time))) as total_over_time FROM work_history WHERE emp_id = ? and `date` between ? and ?;";

		for(int n = 0; n < user_list.size();n++) {
			ArrayList<String>list = new ArrayList<String>();//月の残業時間の入ったリスト
			//ArrayList<String>cautionList = new ArrayList<String>();//20時間未満なら"0",20~40は"1",40~45は"2",45~は"3"の入ったリスト
			//12回分のループ
			for(int i = 0;i<12;i++) {
				int intEndDay = getEndOfMonth(target_year,i+1);

				Date startDay = new Date(target_year - 1900,i,1);
				Date endDay = new Date(target_year - 1900,i,intEndDay);
				// SQLを実行してその結果を取得し、実行SQLを渡す
				try {
					// プリペアステーメントを取得し、実行SQLを渡す
					PreparedStatement statement = getPreparedStatement(sql);
					statement.setString(1, user_list.get(n));
					statement.setDate(2, startDay);
					statement.setDate(3, endDay);

					// SQLを実行してその結果を取得する
					ResultSet rs = statement.executeQuery();

					while (rs.next()) {
						if(rs.getString("total_over_time") != null) {
							list.add(rs.getString("total_over_time").toString().substring(0, 5));
							String timeStr = rs.getString("total_over_time").substring(0, 2);
							int time = Integer.parseInt(timeStr);
							if(time>=45) {
								list.add("3");
								//cautionList.add("3");
							}else if(time>=40) {
								list.add("2");
								//cautionList.add("2");
							}else if(time >= 20) {
								list.add("1");
								//cautionList.add("1");
							}else {
								list.add("0");
								//cautionList.add("0");
							}
						}else {
							list.add("00:00");
							list.add("0");
							//	cautionList.add("0");
						}


					}
					// コミットを行う
					super.commit();
				}catch (SQLException e) {
					super.rollback();
					throw e;
				}

			}
			map.put(name_list.get(n), list);
			//map.put(user_list.get(n)+"caution" ,cautionList);
		}
		return map;
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

	//レコードがない対象日付に対して、emp＿idとdateとholidayのみのレコードを新規作成する
	public void setHoliday(String emp_id, Date date, String holiday) throws Exception {
		String sql = "INSERT INTO work_history (emp_id, DATE,holiday) VALUES (?, ?, ?);";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1, emp_id);
			statement.setDate(2,date);
			statement.setString(3,holiday);

			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (SQLException e) {
			super.rollback();
			throw e;
		}
	}
	//レコードのみの対象日付に対して、holidayの更新をする

	public void updateHoliday(String emp_id, Date date, String holiday)throws Exception {
		String sql = "UPDATE  work_history SET holiday = ? WHERE emp_id = ? AND `date` = ?;";

		// SQLを実行してその結果を取得し、実行SQLを渡す
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setString(1,holiday);
			statement.setString(2, emp_id);
			statement.setDate(3,date);


			statement.executeUpdate();

			// コミットを行う
			super.commit();
		}catch (SQLException e) {
			super.rollback();
			throw e;
		}

	}

	//holidayを変更して、自動計算するメソッド
	public void updateAuto(String emp_id,Date date,Time start_time,Time finish_time , String holiday,String feeling,String user_type ,BigDecimal finish_latitude,BigDecimal finish_longitude ,String note) throws Exception {
		String sql = "UPDATE work_history SET start_time = ?,holiday = ? WHERE emp_id = ? AND `date` = ?;";
		try {
			// プリペアステーメントを取得し、実行SQLを渡す
			PreparedStatement statement = getPreparedStatement(sql);
			statement.setTime(1, start_time);
			statement.setString(2,holiday);
			statement.setString(3, emp_id);
			statement.setDate(4,date);

			statement.executeUpdate();

			ProcessedTime pTime = new ProcessedTime(finish_time.toString());
			Calendar cal = new Calendar.Builder().setInstant(date).build();

			if(pTime.getIndex() <= 16) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date.setTime(cal.getTimeInMillis());
			}
			workFinish(emp_id,date,finish_time,feeling,user_type ,finish_latitude,finish_longitude,note);
			// コミットを行う
			super.commit();
		}catch (Exception e) {
			e.printStackTrace();
			super.rollback();
			throw e;
		}

	}

	//打刻データを削除するメソッド
	public Boolean delete(Date date, String emp_id) throws Exception{

		Boolean isPaidHoliday = false;

		String sql1 = "SELECT  * FROM work_history WHERE (`emp_id` = ?) and (`date` = ?);";
		try {
			PreparedStatement statement = getPreparedStatement(sql1);

			statement.setString(1, emp_id);
			statement.setDate(2, date);

			ResultSet rs =  statement.executeQuery();

			String holiday = "";
			while(rs.next()) {
				holiday = rs.getString("holiday");
			}
			if(holiday.equals("2")) {
				isPaidHoliday = true;
			}

		} catch (Exception e) {
			System.out.println("エラー１");
			e.printStackTrace();
			super.rollback();
			throw e;
		}
		String sql = "DELETE FROM work_history WHERE `emp_id` = ? AND `date` = ?;";

		try {

			PreparedStatement statement = getPreparedStatement(sql);

			statement.setString(1, emp_id);
			statement.setDate(2, date);

			statement.executeUpdate();

			super.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("エラー");

			e.printStackTrace();
			super.rollback();
			throw e;
		}

		return isPaidHoliday;
	}
	//退勤打刻がされているか調べるメソッド
	public Time finishCheck(String emp_id, Date date) throws Exception{
		// TODO 自動生成されたメソッド・スタブ
		String sql = "SELECT * FROM work_history WHERE (`emp_id` = ?) and (`date` = ?);";

		Time time = null;

		try {
			PreparedStatement statement = getPreparedStatement(sql);

			statement.setString(1, emp_id);
			statement.setDate(2, date);

			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				time = rs.getTime("finish_time");
			}

			super.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			super.rollback();
			throw e;
		}

		return time;

	}




}
