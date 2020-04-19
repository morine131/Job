package DTO;

import java.sql.Timestamp;

public class FeedBackBeans {
	private int fbid;	// 就活体験談ID

	private String fbcontent;//就活体験談内容

	private int jobid;//

	private String userid;//

	private String nickname;//ニックネーム

	private String company; 	//会社名

	private Timestamp fblastupdate;//最終更新日

	public int getFbid() {
		return fbid;
	}

	public void setFbid(int fbid) {
		this.fbid = fbid;
	}


	public String getFbcontent() {
		return fbcontent;
	}

	public void setFbcontent(String fbcontent) {
		this.fbcontent = fbcontent;
	}

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Timestamp getFblastupdate() {
		return fblastupdate;
	}

	public void setFblastupdate(Timestamp fblastupdate) {
		this.fblastupdate = fblastupdate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}