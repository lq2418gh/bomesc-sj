package dkd.business.dataParamConfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.dataParamConfig.domain.DataManagerHead;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class DataManagerHeadDao extends BaseDao<DataManagerHead>{

	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select mh.id,md.id cid,mh.manager_no,mh.project_name,mh.job_no,mh.major,md.entity_createdate,md.entity_createuser,"
				+ "md.job_number,md.manager_user_name,md.manager_user,md.department,"
				+ "md.email,md.export_fail,md.lack_material from c_data_manager_detail md left join c_data_manager_head mh on md.dataManagerHead=mh.id";
		return super.getScrollData(sql, params,new String[]{"major"});
	}

	public List<Map<String, Object>> checkValidate(String project, String major,String id) {
		String sql = "select id from c_data_manager_head where job_no=:project and major = :major ";
		Map<String ,Object> paraMap= new HashMap<String, Object>();
		paraMap.put("project", project);
		paraMap.put("major", major);
		if(null!=id&&!"".equals(id)){
			sql+=" and id !=:id";
			paraMap.put("id", id);
		}
		return super.findBySql(sql, paraMap);
	}
	/**
	 * @date 2017年12月20日
	 * @author gaoxp
	 * @param jobNo
	 * @param major
	 * @return
	 * 描述：通过工作号和专业查询所有的数据负责表体的id,邮箱，是否负责导入失败，是否负责缺料提醒
	 */
	public List<Map<String, Object>> findByJobNoAndMajor(String jobNo,String major){
		String sql="select md.id,md.email,md.export_fail,md.lack_material from c_data_manager_head mh left join c_data_manager_detail md on mh.id=md.dataManagerHead "+
					"where mh.job_no='"+jobNo+"' and major='"+major+"'";
		return findBySql(sql);
	}
}
