package dkd.paltform.flowset.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.dao.ProcessRecordRoleDao;
import dkd.paltform.flowset.domain.ProcessRecordRole;

@Service
@Transactional
public class ProcessRecordRoleService extends BaseService<ProcessRecordRole>{
	@Autowired
	private ProcessRecordRoleDao processRecordRoleDao;
	@Override
	public BaseDao<ProcessRecordRole> getDao() {
		return processRecordRoleDao;
	}
	public List<Map<String,Object>> selectByRecord(String recordId){
		return processRecordRoleDao.selectByRecord(recordId);
	}
}