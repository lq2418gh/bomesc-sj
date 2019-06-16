package dkd.paltform.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dkd.paltform.util.json.JSONUtil;

public class QueryResult<T> implements Serializable{
	
	private static final long serialVersionUID = 6832171471891001030L;
	/** 列表集合 */
	private final List<T> rows = new ArrayList<T>();
	/** 数据集合总数 */
	private final Long total;
	public QueryResult(){
		total = 0L;
	}
	public QueryResult(List<T> data, Long total){
		this.rows.addAll(data);
		this.total = total;
	}
	public List<T> getRows() {
		return this.rows;
	}
	public Long getTotal(){
		return this.total;
	}
	public String toJson() {
		StringBuffer bf = new StringBuffer("{\"total\":");
		bf.append(getTotal());
		bf.append(",");
		bf.append("\"rows\":");
		bf.append(JSONUtil.getJsonByEntity(getRows()));
		bf.append("}");
		return bf.toString();
	}
}