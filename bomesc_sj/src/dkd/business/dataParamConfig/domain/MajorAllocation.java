package dkd.business.dataParamConfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;
/**
 *多专业公用优先级配置
 */
@Entity
@Table(name = "major_allocation")
public class MajorAllocation extends BusinessEntity{

	private static final long serialVersionUID = 1L;
	@Column(nullable=false,length=20)
	private Integer major_no;//顺序
	
	@ManyToOne
	@JoinColumn(name="major", nullable=false)
	private Dictionary major;//专业ID

	public Integer getMajor_no() {
		return major_no;
	}

	public void setMajor_no(Integer major_no) {
		this.major_no = major_no;
	}

	public Dictionary getMajor() {
		return major;
	}

	public void setMajor(Dictionary major) {
		this.major = major;
	}
	
	
}
