/**
 * 作成者：？？？
 *
 * 2019/1/6 市橋追記：valueCheck()
 */


package DTO;

public class UserInfoBeans {

	private String emp_id;
	private String user_name;
	private String user_type;
	private String admin_flg;
	private String pass;
	private String del_flg;
	private int paid_vacations;
	public int getPaid_vacations() {
		return paid_vacations;
	}
	public void setPaid_vacations(int paid_vacations) {
		this.paid_vacations = paid_vacations;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getAdmin_flg() {
		return admin_flg;
	}
	public void setAdmin_flg(String admin_flg) {
		this.admin_flg = admin_flg;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getDel_flg() {
		return del_flg;
	}
	public void setDel_flg(String del_flg) {
		this.del_flg = del_flg;
	}
}