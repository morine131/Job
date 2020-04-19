package DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class JobInfoBeans {

	private int jobid;	// jobのコード

	private String company;//会社名

	private int postcode;//会社所在郵便番号
	private String pref;//会社所在県
	private String city;//会社所在地区

	private int salary1;//給与１
	private int salary2;//給与２

	private int empid;//雇用形態
	private String empstatus;//雇用形態の文字列型

	private int typeid;//職種
	private String typestatus;//職種の文字列型

	private String workarea;//就業県


	private int publish;//公開/非公開

	private Date validperiod;//有効期限
	private  String inputvalidperiod;


	private Timestamp joblastupdate;//最終更新日

	private String pdfname;//PDFファイル名

	public int getJobid() {
		return jobid;
	}

	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getPref() {
		return pref;
	}

	public void setPref(String pref) {
		this.pref = pref;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getSalary1() {
		return salary1;
	}

	public void setSalary1(int salary1) {
		this.salary1 = salary1;
	}

	public int getSalary2() {
		return salary2;
	}

	public void setSalary2(int salary2) {
		this.salary2 = salary2;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpstatus() {
		return empstatus;
	}

	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getTypestatus() {
		return typestatus;
	}

	public void setTypestatus(String typestatus) {
		this.typestatus = typestatus;
	}

	public String getWorkarea() {
		return workarea;
	}

	public void setWorkarea(String workarea) {
		this.workarea = workarea;
	}

	public int getPublish() {
		return publish;
	}

	public void setPublish(int publish) {
		this.publish = publish;
	}

	public Date getValidperiod() {
		return validperiod;
	}

	public void setValidperiod(Date validperiod) {
		this.validperiod = validperiod;
	}

	public Timestamp getJoblastupdate() {
		return joblastupdate;
	}

	public void setJoblastupdate(Timestamp joblastupdate) {
		this.joblastupdate = joblastupdate;
	}

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public String getInputvalidperiod() {
		return inputvalidperiod;
	}

	public void setInputvalidperiod(String inputvalidperiod) {
		this.inputvalidperiod = inputvalidperiod;
	}



}