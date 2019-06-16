package dkd.business.dataParamConfig.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.dao.MajorAllocationlDao;
import dkd.business.dataParamConfig.domain.MajorAllocation;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional
public class MajorAllocationService extends BaseService<MajorAllocation>{
	@Autowired
	private MajorAllocationlDao majorAllocationlDao;

	@Override
	public BaseDao<MajorAllocation> getDao() {
		// TODO Auto-generated method stub
		return majorAllocationlDao;
	}

	public QueryResult<Map<String, Object>> findMA() {
		// TODO Auto-generated method stub
		return majorAllocationlDao.findMA();
	}

	public void save(MajorAllocation majorAllocation) {
		// TODO Auto-generated method stub
		majorAllocationlDao.create(majorAllocation);
	}



}
