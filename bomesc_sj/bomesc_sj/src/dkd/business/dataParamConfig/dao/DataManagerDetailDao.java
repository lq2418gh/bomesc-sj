package dkd.business.dataParamConfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.dataParamConfig.domain.DataManagerDetail;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class DataManagerDetailDao extends BaseDao<DataManagerDetail>{

	public List<Map<String, Object>> findByHead(String id) {
		String sql = "select id from c_data_manager_detail where dataManagerHead =:id";
		Map<String,Object> map = new HashMap<>();
		map.put("id", id);
		return super.findBySql(sql, map);
	}

}
