package dkd.business.dataParamConfig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.dataParamConfig.dao.AllocationPriorityDetailDao;
import dkd.business.dataParamConfig.domain.AllocationPriorityDetail;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;

@Service
@Transactional
public class AllocationPriorityDetailService extends BaseService<AllocationPriorityDetail>{
	@Autowired
	private AllocationPriorityDetailDao allocationPriorityDetailDao;

	@Override
	public BaseDao<AllocationPriorityDetail> getDao() {
		// TODO Auto-generated method stub
		return allocationPriorityDetailDao;
	}

}
