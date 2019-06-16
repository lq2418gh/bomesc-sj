package dkd.business.designDataChange.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.designDataChange.dao.StDataTrackChangeDao;
import dkd.business.designDataManager.domain.StDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional
public class StDataTrackChangeService extends BaseService<StDataTrack> {
	
	@Autowired
	private StDataTrackChangeDao dataTrackChangeDao;
	
	@Override
	public BaseDao<StDataTrack> getDao() {
		return dataTrackChangeDao;
	}

	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> param,User user) {
		return dataTrackChangeDao.getScrollDataByUser(param,user);
	}

	public List<Map<String, Object>> findByProfessional(String update_date,String project_name,String module) {
		return dataTrackChangeDao.findByProfessional(update_date,project_name,module);
	}
	public List<Map<String, Object>> findChangeDetails(String project_name,String shop_draw_no,String module_name,String level_no,
			String update_date,String mtoNo,String mtoRowNo,String module){
		return dataTrackChangeDao.findChangeDetails(project_name,shop_draw_no,module_name,level_no,update_date,mtoNo,mtoRowNo,module);
	}
	/**
	 * @date 2017年12月26日
	 * @author gaoxp
	 * @param stDataTrack
	 * @return
	 * 描述：保存标记变更原因
	 */
	public String saveReason(StDataTrack stDataTrack,User currentUser){
		if(dataTrackChangeDao.batchUpdateReason(stDataTrack, currentUser)>0){
			return "保存成功！";
		}else{
			return "保存失败";
		}
	}
	public String saveState(StDataTrack stDataTrack,User currentUser){
		if(dataTrackChangeDao.saveState(stDataTrack, currentUser)>0){
			return "保存成功！";
		}else{
			return "保存失败";
		}
	}

	public String stateConfirm(String id,User user) {
		if(dataTrackChangeDao.stateConfirm(id,user)>0){
			return "确认成功！";
		}else{
			return "确认失败";
		}
	}

	public List<Map<String, Object>> findTotalTask() {
		List<Map<String, Object>> list =  dataTrackChangeDao.findTotalTask();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("professional","allProfessional");
		int total=0;
		for(int i=0;i<list.size();i++){
			total+=Integer.parseInt(list.get(i).get("num").toString());
		}
		map.put("num", total);
		list.add(map);
		return list;
	}
}
