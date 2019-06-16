package dkd.business.designDataPicking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.designDataPicking.dao.StDataPickingHeadDao;
import dkd.business.designDataPicking.domain.StDataPickingDetail;
import dkd.business.designDataPicking.domain.StDataPickingHead;
import dkd.business.project.service.ProjectService;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.flowset.service.FlowsetService;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class StDataPickingHeadService extends BaseService<StDataPickingHead>{

	@Autowired
	private StDataPickingHeadDao stDataPickingDao;
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private FlowsetService flowsetService;
	@Override
	public BaseDao<StDataPickingHead> getDao() {
		return stDataPickingDao;
	}
	/**
	 * @date 2018年1月10日
	 * @param stDataPickingHead
	 * 描述：保存
	 */
	public void saveStDataPicking(StDataPickingHead stDataPickingHead) {
		String projectId = projectService.getProjectId(stDataPickingHead.getJob_no());
		stDataPickingHead.setProject(projectId);
		stDataPickingHead.setState("新建");
		if(StringUtils.isEmpty(stDataPickingHead.getId())){
			for (StDataPickingDetail stDataPickingDetail : stDataPickingHead.getStDataPickingDetail()) {
				stDataPickingDetail.setStDataPickingHead(stDataPickingHead);
			}
			stDataPickingDao.create(stDataPickingHead);
		}else{
			stDataPickingDao.update(stDataPickingHead);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void submit(String picking_no ,String job_no){
		StDataPickingHead stDataPicking = findByField("picking_no", picking_no);
		stDataPicking.setState("提交");
		update(stDataPicking);
		flowsetService.initRecord("stPicking", picking_no,job_no);
	}

}
