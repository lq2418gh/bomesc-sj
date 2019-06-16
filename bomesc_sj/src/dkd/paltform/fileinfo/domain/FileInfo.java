package dkd.paltform.fileinfo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import dkd.paltform.base.domain.BusinessEntity;
import dkd.paltform.util.json.annotation.MyselfJsonIngore;

@Entity
@Table(name = "base_fileinfo")
public class FileInfo extends BusinessEntity{
	private static final long serialVersionUID = 4280492523314411009L;
	@Column(nullable = false, length = 40)
	private String entity_code;
	@Column(nullable = false, length = 40)
	private String entity_id;
	@Column(nullable = false, length = 100)
	private String file_name;
	@Column(nullable = false, length = 500)
	private String path;
	//文件
	@Transient
	@MyselfJsonIngore
	private MultipartFile fileL;
	
	public String getEntity_code() {
		return entity_code;
	}
	public void setEntity_code(String entity_code) {
		this.entity_code = entity_code;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultipartFile getFileL() {
		return fileL;
	}
	public void setFileL(MultipartFile fileL) {
		this.fileL = fileL;
	}
}