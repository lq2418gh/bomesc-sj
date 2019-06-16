package dkd.paltform.base.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @ClassName: DocLine
 * @Description: 单据明细EntityBase
 * @author CST-TONGLZ
 * @date 2015-7-6 下午4:25:50
 * 
 */
/*@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)*/
@MappedSuperclass
public class DocLine extends BusinessEntity {
	private static final long serialVersionUID = 1300879646755655480L;
	/**
	 * 订单明细号，由用户输入
	 */
	@Transient
	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
}
