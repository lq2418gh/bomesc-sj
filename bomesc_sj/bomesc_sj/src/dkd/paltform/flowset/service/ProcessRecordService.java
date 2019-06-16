package dkd.paltform.flowset.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.dao.ProcessRecordDao;
import dkd.paltform.flowset.domain.Flowset;
import dkd.paltform.flowset.domain.ProcessRecord;

@Service
@Transactional
public class ProcessRecordService extends BaseService<ProcessRecord>{
	@Autowired
	private ProcessRecordDao processRecordDao;
	@Override
	public BaseDao<ProcessRecord> getDao() {
		// TODO Auto-generated method stub
		return processRecordDao;
	}
	public void deleteByWorkNo(String work_no){
		processRecordDao.deleteByWorkNo(work_no);
	}
	public void deleteByUnPass(String work_no){
		processRecordDao.deleteByUnPass(work_no);
	}
	public ProcessRecord selectByWorkNo(String work_no){
		return processRecordDao.selectByWorkNo(work_no);
	}
	public void updateSqlByPass(Flowset flowset,String work_no){
		processRecordDao.updateSqlByPass(flowset,work_no);
	}
	public void updateSqlByUnPass(Flowset flowset,String work_no){
		processRecordDao.updateSqlByUnPass(flowset,work_no);
	}
	public QueryResult<Map<String,Object>> getTaskScrollData(){
		return processRecordDao.getTaskScrollData();
	}
}
