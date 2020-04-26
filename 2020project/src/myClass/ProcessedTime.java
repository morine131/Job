package myClass;

import java.sql.Time;

public class ProcessedTime {

	private int index;

	public ProcessedTime() {

	}

	public ProcessedTime(String HHMMSS){
		String HH = "";
		String MM = "";
		int num = 0;//最終的にindexになる数

		HH = HHMMSS.substring(0, 2);
		MM = HHMMSS.substring(3,5);

		//00~09までの場合、頭の0を取る
		if(HH.substring(0) == "0") {
			HH = HH.substring(1);
		}

		int intHH = Integer.parseInt(HH);
		int intMM = Integer.parseInt(MM);

		num += intHH * 2;

		if(intMM >= 30) {
			num ++;
		}

		this.setIndex(num);

	}
	//出社時刻を受け取るコンストラクタ　第二引数に"start"を受け取ることを想定
	public ProcessedTime(String HHMMSS,String str){
		String HH = "";
		String MM = "";
		int num = 0;//最終的にindexになる数

		HH = HHMMSS.substring(0, 2);
		MM = HHMMSS.substring(3,5);

		//00~09までの場合、頭の0を取る
		if(HH.substring(0) == "0") {
			HH = HH.substring(1);
		}

		int intHH = Integer.parseInt(HH);
		int intMM = Integer.parseInt(MM);

		num += intHH * 2;

		if(intMM >= 30) {
			num ++;
		}
		num++;
		this.setIndex(num);

	}

	//差を返すメソッド
	public static ProcessedTime getDiff(ProcessedTime pt1,ProcessedTime pt2) {

		ProcessedTime pt = new ProcessedTime("00:00:00");
		int ptIndex = 0;

		if(pt1.getIndex() >= pt2.getIndex()) {
			ptIndex =  pt1.getIndex() - pt2.getIndex();
		}else {
			ptIndex =  pt1.getIndex() - pt2.getIndex() + 48 ;
		}
		pt.setIndex(ptIndex);
		return pt;
	}

	//合計を返すメソッド
	public static ProcessedTime getSum(ProcessedTime pt1,ProcessedTime pt2) {

		ProcessedTime pt = new ProcessedTime("00:00:00");
		int ptIndex = 0;
											//etc 9:00~2:00の時間を調べたいとき（退社が0:00を過ぎた時）、pt1に9:00 pt2に2:00を入れる
			ptIndex =  pt1.getIndex() + pt2.getIndex()  ;

		pt.setIndex(ptIndex);
		return pt;
	}

	public Time convertSqlTime() {
		int ptIndex = this.getIndex();

		int hour = 0;
		int minute = 0;
		int second = 0;

		hour += ptIndex/2;

		if(ptIndex % 2 !=0) {
			minute += 30;
		}

		Time time = new Time(hour,minute,second);

		return time;
	}
	public String convertHHHTime() {
		int ptIndex = this.getIndex();

		int hour = 0;
		String minute = "";

		hour += ptIndex/2;

		if(ptIndex % 2 !=0) {
			minute = "30";
		}else {
			minute = "00";
		}

		String time = hour+":"+minute;

		return time;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
