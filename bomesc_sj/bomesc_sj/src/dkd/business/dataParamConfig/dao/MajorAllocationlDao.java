package dkd.business.dataParamConfig.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.dataParamConfig.domain.MajorAllocation;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class MajorAllocationlDao extends BaseDao<MajorAllocation>{

	public QueryResult<Map<String, Object>> findMA() {
		// TODO Auto-generated method stub
		String sql="select m.id,m.major_no,m.version,m.entity_createdate,m.entity_createuser,m.entity_modifyuser,m.entity_modifydate,"
				+ "m.major,b.name "
				+ "from  major_allocation m left join base_dictionary b on m.major=b.id order by m.major_no";
		return getScrollData(sql);
	}


}
