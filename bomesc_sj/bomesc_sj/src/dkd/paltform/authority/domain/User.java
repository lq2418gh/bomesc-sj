package dkd.paltform.authority.domain;


import java.io.Serializable;
import java.util.Date;

import dkd.paltform.base.domain.BusinessEntity;

public class User extends BusinessEntity implements Serializable{
	private static final long serialVersionUID = 7298926716228453699L;
	//姓名
	private String name;
	//生日
	private Date birthday;
	//性别（数据字典）
	private String sex;
	private String sex_name;
	//邮箱
	private String email;
	//工号
	private String job_number;
	//专业
	private String specialty;
	private String specialty_name;
	//职位
	private String post;
	private String post_name;
	//电话
	private String phone;
	//座机
	private String tel;
	//数字签名
	private String sign_path;
	//部门
	private Department department;
	
	//登陆名称
	private String username;
	// 密码,MD5加密
	private String password;
	// 是否有效.Y有效,N无效
	private String isvalidate = "Y";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsvalidate() {
		return isvalidate;
	}
	public void setIsvalidate(String isvalidate) {
		this.isvalidate = isvalidate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob_number() {
		return job_number;
	}
	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSign_path() {
		return sign_path;
	}
	public void setSign_path(String sign_path) {
		this.sign_path = sign_path;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSex_name() {
		return sex_name;
	}
	public void setSex_name(String sex_name) {
		this.sex_name = sex_name;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getSpecialty_name() {
		return specialty_name;
	}
	public void setSpecialty_name(String specialty_name) {
		this.specialty_name = specialty_name;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPost_name() {
		return post_name;
	}
	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}
	
	/*public String toJson(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer json = new StringBuffer("{\"id\":\"");
		json.append(this.getId());
		json.append("\",\"name\":\"");
		json.append(this.getName());
		json.append("\",\"sex\":\"");
		json.append(this.getSex() == null ? "" :this.getSex().getName());
		json.append("\",\"department\":\"");
		json.append(this.getDepartment() == null ? "" : this.getDepartment().getName());
		json.append("\",\"birthday\":\"");
		json.append(this.getBirthday() == null ? "" : df.format(this.getBirthday()));
		json.append("\",\"email\":\"");
		json.append(this.getEmail());
		json.append("\",\"username\":\"");
		json.append(this.getUsername());
		json.append("\",\"password\":\"");
		json.append(this.getPassword());
		json.append("\"}");
		return json.toString();
	}*/
}