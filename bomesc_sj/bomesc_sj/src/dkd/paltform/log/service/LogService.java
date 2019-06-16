package dkd.paltform.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.log.dao.LogDao;
import dkd.paltform.log.domain.Log;

@Service
@Transactional
public class LogService extends BaseService<Log>{
	@Autowired
	private LogDao logDao;
	
	@Override
	public BaseDao<Log> getDao() {
		return logDao;
	}
	
}
