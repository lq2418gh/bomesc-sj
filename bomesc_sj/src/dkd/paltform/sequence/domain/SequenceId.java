package dkd.paltform.sequence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 序号
 * @author 周渤涛
 * @date 2015年11月27日
 */
@Embeddable
public class SequenceId  implements Serializable {
	
    private static final long serialVersionUID = 1L; 
	

	/**
	 * 实体类型
	 */
	@Column(nullable = false, length = 150)
	private String entity_type;
	
	/**
	 * 序号值
	 */
	@Column(nullable = false, length = 150)
	private String sequence_by_value;
	

	public String getEntity_type() {
		return entity_type;
	}

	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}

	public String getSequence_by_value() {
		return sequence_by_value;
	}

	public void setSequence_by_value(String sequence_by_value) {
		this.sequence_by_value = sequence_by_value;
	}

	@Override 
	public boolean equals(Object obj) { 
		if (obj == null) 
			return false; 
		if (getClass() != obj.getClass())
			return false; 
		final SequenceId other = (SequenceId) obj; 
		if ((this.entity_type == null) ? (other.entity_type != null) : !this.entity_type.equals(other.entity_type)) 
			return false; 
		if ((this.sequence_by_value == null) ? (other.sequence_by_value != null) : !this.sequence_by_value.equals(other.sequence_by_value))
			return false; 
		return true; 
	} 

	@Override 
	public int hashCode() { 
		int hash = 5; 
		hash = 41 * hash + (this.entity_type != null ? this.entity_type.hashCode() : 0); 
		hash = 41 * hash + (this.sequence_by_value != null ? this.sequence_by_value.hashCode() : 0); 
		return hash; 
	} 
	
}
