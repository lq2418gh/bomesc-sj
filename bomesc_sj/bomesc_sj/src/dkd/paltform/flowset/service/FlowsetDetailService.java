package dkd.paltform.flowset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.dao.FlowsetDetailDao;
import dkd.paltform.flowset.domain.Flowset;
import dkd.paltform.flowset.domain.FlowsetDetail;
import dkd.paltform.flowset.domain.FlowsetDetailRole;

@Service
@Transactional
public class FlowsetDetailService extends BaseService<FlowsetDetail>{
	@Autowired
	private FlowsetDetailDao flowsetDetailDao;
	@Autowired
	private FlowsetService flowsetService;

	@Override
	public BaseDao<FlowsetDetail> getDao() {
		return flowsetDetailDao;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveDetail(FlowsetDetail flowsetDetail){
		if (validateOrderNo(flowsetDetail)) {
			throw new BusinessException(-1, "code", "该流程下审批节点序号已经存在");
		}
		for(FlowsetDetailRole detail_role : flowsetDetail.getDetail_roles()){
			detail_role.setFlowset_detail(flowsetDetail);
		}
		if(StringUtils.isEmpty(flowsetDetail.getId())){
			flowsetDetailDao.create(flowsetDetail);
			Flowset flowset = flowsetService.findByID(flowsetDetail.getFlowset().getId());
			flowset.setLevels(flowset.getLevels() + 1);
			flowsetService.update(flowset);
		}else{
			flowsetDetailDao.update(flowsetDetail);
		}
	}
	protected boolean validateOrderNo(FlowsetDetail flowsetDetail) {
		List<FlowsetDetail> details = flowsetDetailDao.findByOrderNo(flowsetDetail);
		if(details != null && !details.isEmpty()){
			return true;
		}else{
			return false;			
		}
	}
	public void deleteDetail(String detailId,String flowsetId){
		delete(detailId);
		Flowset flowset = flowsetService.findByID(flowsetId);
		flowset.setLevels(flowset.getLevels() - 1);
		flowsetService.update(flowset);
	}
	public void setValidate(String detailId,boolean is_validate){
		FlowsetDetail detail = findByID(detailId);
		detail.setIs_validate(is_validate);
		update(detail);
	}
}
