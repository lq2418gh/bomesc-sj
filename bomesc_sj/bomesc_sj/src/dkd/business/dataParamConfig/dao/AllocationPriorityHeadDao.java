package dkd.business.dataParamConfig.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.dataParamConfig.domain.AllocationPriorityHead;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class AllocationPriorityHeadDao  extends BaseDao<AllocationPriorityHead>{

	public List<AllocationPriorityHead> checkIsData(Map<String, Object> map) {
		String jpql="from AllocationPriorityHead where job_no=:project and major=:major ";
		if (null!=map.get("id")) {
			jpql+=" and id<>:id ";
		}
		return super.findAll(jpql, map);
	}
}
