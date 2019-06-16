package dkd.business.promptRecord.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.promptRecord.dao.ImportFailureRecordDao;
import dkd.business.promptRecord.domain.ImportFailureRecord;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional	
public class ImportFailureRecordService extends BaseService<ImportFailureRecord>{
	@Autowired
	private ImportFailureRecordDao importFailureRecordDao;
	@Override
	public BaseDao<ImportFailureRecord> getDao() {
		return importFailureRecordDao;
	}

}
