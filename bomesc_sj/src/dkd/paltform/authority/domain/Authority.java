package dkd.paltform.authority.domain;

import java.io.Serializable;

import dkd.paltform.base.domain.TreeEntity;
import dkd.paltform.util.json.annotation.RefJsonWrite;

public class Authority extends TreeEntity<Authority> implements Serializable {
	private static final long serialVersionUID = 1L;

	// 权限对应的URL
	private String url;

	@RefJsonWrite
	private String sort_no;
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort_no() {
		return this.sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
}