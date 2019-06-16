package dkd.business.dataParamConfig.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import dkd.business.dataParamConfig.dao.ColumnDisplaySettingDao;
import dkd.business.dataParamConfig.domain.ColumnDisplaySetting;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class ColumnDisplaySettingService extends BaseService<ColumnDisplaySetting>{
	@Autowired
	private ColumnDisplaySettingDao columnDisplaySettingDao;

	@Override
	public BaseDao<ColumnDisplaySetting> getDao() {
		return columnDisplaySettingDao;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveData(ColumnDisplaySetting columnDisplaySetting){
		columnDisplaySetting.validateModel();
		String id = columnDisplaySetting.getId();
		ColumnDisplaySetting temp = columnDisplaySettingDao.findByValidate(columnDisplaySetting);
		if(temp!=null){
			throw new BusinessException(-1, "项目编号和专业", "已存在相同项目、专业的数据，保存失败！");	
		}
		if(StringUtils.isEmpty(id)){
			create(columnDisplaySetting);
		}else{
			update(columnDisplaySetting);
		}
	}

	/**
	 * @date 2017年11月22日
	 * @author shanjy
	 * @param id
	 * 描述：删除
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteData(String id){
		delete(id);
	}
	/**
	 * @date 2017年11月30日
	 * @author zhaolw
	 * @param professional
	 * @return 
	 * 描述：professional为专业编号
	 */
	public Map<String, Object> findByMajor(String jobNo,String professional) {
		return columnDisplaySettingDao.findByMajor(jobNo,professional).isEmpty()?null:columnDisplaySettingDao.findByMajor(jobNo,professional).get(0);
	}
}
