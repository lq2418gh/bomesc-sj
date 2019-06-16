package dkd.paltform.base.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dkd.paltform.util.json.annotation.DateJsonFormat;

/**
 * @ClassName: Doc
 * @Description:单据EntityBase
 * @author CST-TONGLZ
 * @date 2015-7-6 下午4:07:13
 */
/*@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)*/
@MappedSuperclass
public class Doc extends BusinessEntity {
	private static final long serialVersionUID = -312959044426281989L;
	@Column
	private String doc_no;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@DateJsonFormat(style = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date doc_date;

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}

	public Date getDoc_date() {
		return doc_date;
	}

	public void setDoc_date(Date doc_date) {
		this.doc_date = doc_date;
	}

}
