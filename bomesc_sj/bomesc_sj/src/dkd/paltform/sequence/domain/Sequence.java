package dkd.paltform.sequence.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 序号
 * @author 周渤涛
 * @date 2015年11月27日
 */
@Entity
@Table(name="base_sequence")
public class Sequence implements Serializable { 
    private static final long serialVersionUID = 1L; 
	
	/**
	 * 序号值
	 */
	@Id
	@EmbeddedId 
	private SequenceId sequenceId;
	
	/**
	 * 次数
	 */
	@Column(nullable = false)
	private int flow_no;

	public SequenceId getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(SequenceId sequenceId) {
		this.sequenceId = sequenceId;
	}

	public int getFlow_no() {
		return flow_no;
	}

	public void setFlow_no(int flow_no) {
		this.flow_no = flow_no;
	}
	
}
