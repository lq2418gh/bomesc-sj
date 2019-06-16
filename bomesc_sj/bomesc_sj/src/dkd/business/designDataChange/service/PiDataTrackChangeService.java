package dkd.business.designDataChange.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.designDataChange.dao.PiDataTrackChangeDao;
import dkd.business.designDataManager.domain.PiDataTrack;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional
public class PiDataTrackChangeService extends BaseService<PiDataTrack>{
	@Autowired
	private PiDataTrackChangeDao piDataTrackChangeDao;

	@Override
	public BaseDao<PiDataTrack> getDao() {
		// TODO Auto-generated method stub
		return piDataTrackChangeDao;
	}

	public QueryResult<Map<String, Object>> getScrollDataByUser(Map<String, String> param, User currentUser) {
		return piDataTrackChangeDao.getScrollDataByUser(param,currentUser);
	}

	public List<Map<String, Object>> findByProfessional(String update_date,String project_name) {
		return piDataTrackChangeDao.findByProfessional(update_date,project_name);
	}

	public List<Map<String, Object>> findChangeDetails(String project_name,String shop_draw_no,String module_no,String update_date){
		return piDataTrackChangeDao.findChangeDetails(project_name,shop_draw_no,module_no,update_date);
	}

	public String saveReason(PiDataTrack piDataTrack, User currentUser) {
		if(piDataTrackChangeDao.batchUpdateReason(piDataTrack, currentUser)>0){
			return "保存成功！";
		}else{
			return "保存失败";
		}
	}
	
}
