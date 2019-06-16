package dkd.business.dataParamConfig.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.dictionary.domain.Dictionary;
/**
 * 物资分配优先级体表
 */
@Entity
@Table(name = "c_allocation_priority_detail")
public class AllocationPriorityDetail extends BusinessEntity{

	private static final long serialVersionUID = 1L;

	@Column(nullable=false,length=20)
	private Integer sort_no;//顺序
	
	@ManyToOne
	@JoinColumn(name="sort_column",nullable=false)
	private Dictionary sort_column;//数据字典id
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="allocationPriorityHead", nullable=false)
	private AllocationPriorityHead allocationPriorityHead;//

	public Integer getSort_no() {
		return sort_no;
	}

	public void setSort_no(Integer sort_no) {
		this.sort_no = sort_no;
	}

	public Dictionary getSort_column() {
		return sort_column;
	}

	public void setSort_column(Dictionary sort_column) {
		this.sort_column = sort_column;
	}

	public AllocationPriorityHead getAllocationPriorityHead() {
		return allocationPriorityHead;
	}

	public void setAllocationPriorityHead(AllocationPriorityHead allocationPriorityHead) {
		this.allocationPriorityHead = allocationPriorityHead;
	}
	
}
