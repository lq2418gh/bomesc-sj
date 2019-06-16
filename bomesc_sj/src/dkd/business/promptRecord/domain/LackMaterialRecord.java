package dkd.business.promptRecord.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.business.dataParamConfig.domain.DataManagerDetail;
import dkd.paltform.base.domain.BusinessEntity;
/**
 * 缺料提醒记录
 * @author gaoxp
 */
@Entity
@Table(name = "p_lack_material")
public class LackMaterialRecord extends BusinessEntity{
	private static final long serialVersionUID = 1L;
	//提醒日期
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date prompt_date;
	@ManyToOne
	@JoinColumn(name="manager_detail",nullable=false)
	private DataManagerDetail manager_detail;
	//提醒内容
	@Column(nullable = false,length=4000)
	private String prompt_content;
	public Date getPrompt_date() {
		return prompt_date;
	}
	public void setPrompt_date(Date prompt_date) {
		this.prompt_date = prompt_date;
	}
	public DataManagerDetail getManager_detail() {
		return manager_detail;
	}
	public void setManager_detail(DataManagerDetail manager_detail) {
		this.manager_detail = manager_detail;
	}
	public String getPrompt_content() {
		return prompt_content;
	}
	public void setPrompt_content(String prompt_content) {
		this.prompt_content = prompt_content;
	}
}
