package dkd.business.designDataManager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;

@Entity
@Table(name="c_data_track_columns")
public class DataTrackColumn extends BusinessEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,length=32)
	private String user_id;//用户ID
	
	@Column(name="major")
	private String major;//专业
	
	@Lob
	@Column(nullable=false,length=255)
	private String show_columns;//显示列(例:a,b,c,d)

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getShow_columns() {
		return show_columns;
	}

	public void setShow_columns(String show_columns) {
		this.show_columns = show_columns;
	}

}
