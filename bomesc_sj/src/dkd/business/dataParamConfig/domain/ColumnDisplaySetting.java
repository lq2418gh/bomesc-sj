package dkd.business.dataParamConfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;

@Entity
@Table(name = "b_supplement_columns")
public class ColumnDisplaySetting extends BusinessEntity{
	
	private static final long serialVersionUID = 1L;

	//项目ID
	@Column(length=80, nullable = false)
	private String project;
	
	//专业ID
	@ManyToOne
	@JoinColumn(name="major")
	private Dictionary major;
	
	//项目工作号
	@Column(length=80,nullable = false)
	private String job_no;
	
	//项目名称
	@Column(length=100,nullable = false)
	private String project_name;
	
	//补充列1
	@Column(length=200,nullable = false)
	private String column1;
	
	//补充列2
	@Column(length=200,nullable = false)
	private String column2;
	
	//补充列3
	@Column(length=200,nullable = false)
	private String column3;
	
	//补充列4
	@Column(length=200,nullable = false)
	private String column4;
	
	//补充列5
	@Column(length=200,nullable = false)
	private String column5;
	
	//补充列6
	@Column(length=200,nullable = false)
	private String column6;
	
	//补充列7
	@Column(length=200,nullable = false)
	private String column7;
	
	//补充列8
	@Column(length=200,nullable = false)
	private String column8;
	
	//补充列9
	@Column(length=200,nullable = false)
	private String column9;
	
	//补充列10
	@Column(length=200,nullable = false)
	private String column10;
	
	//补充列11
	@Column(length=200,nullable = false)
	private String column11;
	
	//补充列12
	@Column(length=200,nullable = false)
	private String column12;
	
	//补充列13
	@Column(length=200,nullable = false)
	private String column13;
	
	//补充列14
	@Column(length=200,nullable = false)
	private String column14;
	
	//补充列15
	@Column(length=200,nullable = false)
	private String column15;
	
	//补充列16
	@Column(length=200,nullable = false)
	private String column16;
	
	//补充列17
	@Column(length=200,nullable = false)
	private String column17;
	
	//补充列18
	@Column(length=200,nullable = false)
	private String column18;
	
	//补充列19
	@Column(length=200,nullable = false)
	private String column19;
	
	//补充列20
	@Column(length=200,nullable = false)
	private String column20;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Dictionary getMajor() {
		return major;
	}

	public void setMajor(Dictionary major) {
		this.major = major;
	}

	public String getJob_no() {
		return job_no;
	}

	public void setJob_no(String job_no) {
		this.job_no = job_no;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public String getColumn6() {
		return column6;
	}

	public void setColumn6(String column6) {
		this.column6 = column6;
	}

	public String getColumn7() {
		return column7;
	}

	public void setColumn7(String column7) {
		this.column7 = column7;
	}

	public String getColumn8() {
		return column8;
	}

	public void setColumn8(String column8) {
		this.column8 = column8;
	}

	public String getColumn9() {
		return column9;
	}

	public void setColumn9(String column9) {
		this.column9 = column9;
	}

	public String getColumn10() {
		return column10;
	}

	public void setColumn10(String column10) {
		this.column10 = column10;
	}

	public String getColumn11() {
		return column11;
	}

	public void setColumn11(String column11) {
		this.column11 = column11;
	}

	public String getColumn12() {
		return column12;
	}

	public void setColumn12(String column12) {
		this.column12 = column12;
	}

	public String getColumn13() {
		return column13;
	}

	public void setColumn13(String column13) {
		this.column13 = column13;
	}

	public String getColumn14() {
		return column14;
	}

	public void setColumn14(String column14) {
		this.column14 = column14;
	}

	public String getColumn15() {
		return column15;
	}

	public void setColumn15(String column15) {
		this.column15 = column15;
	}

	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
	}

	public String getColumn17() {
		return column17;
	}

	public void setColumn17(String column17) {
		this.column17 = column17;
	}

	public String getColumn18() {
		return column18;
	}

	public void setColumn18(String column18) {
		this.column18 = column18;
	}

	public String getColumn19() {
		return column19;
	}

	public void setColumn19(String column19) {
		this.column19 = column19;
	}

	public String getColumn20() {
		return column20;
	}

	public void setColumn20(String column20) {
		this.column20 = column20;
	}

	
	
	
	
}
