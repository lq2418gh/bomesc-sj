package dkd.business.promptRecord.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.promptRecord.dao.LackMaterialRecordDao;
import dkd.business.promptRecord.domain.LackMaterialRecord;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional	
public class LackMaterialRecordService extends BaseService<LackMaterialRecord>{
	@Autowired
	private LackMaterialRecordDao LackMaterialRecordDao;
	@Override
	public BaseDao<LackMaterialRecord> getDao() {
		return LackMaterialRecordDao;
	}

}
