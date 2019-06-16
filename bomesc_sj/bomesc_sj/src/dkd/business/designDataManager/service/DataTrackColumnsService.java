package dkd.business.designDataManager.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dkd.business.designDataManager.dao.DataTrackColumnDao;
import dkd.business.designDataManager.domain.DataTrackColumn;
import dkd.paltform.authority.domain.User;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional
public class DataTrackColumnsService extends BaseService<DataTrackColumn>{

	@Autowired
	private DataTrackColumnDao dataTrackColumnDao;
	
	@Override
	public BaseDao<DataTrackColumn> getDao() {
		return dataTrackColumnDao;
	}

	public void create(String values,String major,User user){
		//新建前查询是否存在
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user", user.getId());
		map.put("major", major.substring(major.indexOf("_")+1));
		Map<String, Object> result = this.findByUserAndMajor(map);
		DataTrackColumn dataTrackColumn;
		if(null!=result){
			dataTrackColumn = findByID(String.valueOf(result.get("id")));
			dataTrackColumn.setShow_columns(values);
			super.update(dataTrackColumn);
		}else{
			dataTrackColumn = new DataTrackColumn();
			dataTrackColumn.setMajor(major.substring(major.indexOf("_")+1));
			dataTrackColumn.setShow_columns(values);
			dataTrackColumn.setUser_id(user.getId());
			super.create(dataTrackColumn);
		}
	}
	
	public Map<String, Object> findByUserAndMajor(Map<String,Object> map){
		return dataTrackColumnDao.findByUserAndMajor(map).size()>0?dataTrackColumnDao.findByUserAndMajor(map).get(0):null;
	}
}
