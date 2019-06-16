package dkd.business.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.project.dao.DrawDaoImpl;
import dkd.business.project.domain.Draw;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.sequence.service.SequenceService;
import dkd.paltform.util.Common;

@Service
@Transactional
public class DrawService extends BaseService<Draw>{

	@Autowired
	private DrawDaoImpl drawDaoImpl;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public BaseDao<Draw> getDao() {
		return drawDaoImpl;
	}

	public String saveDraw(Draw draw){
		String projectId = projectService.getProjectId(draw.getJob_no());
		draw.setProject(projectId);
		if(null==draw.getId()||"".equals(draw.getId())){
			String no = sequenceService.getFlowNo("draw",null)+"";
			draw.setOverview_no("OID"+Common.formatCode(no, 9));
			create(draw);
		}else{
			update(draw);
		}
		return draw.getId();
	}

	public Draw findPreOld(Map<String, Object> map) {
		List<Draw> list = drawDaoImpl.findPreOld(map);
		return list.size()==0?null:list.get(0);
	}
	
	public Map<String, Object> findPre(Map<String, Object> map) {
		List<Map<String, Object>> list = drawDaoImpl.findPre(map);
		return list.size()==0?null:list.get(0);
	}

	public Map<String, Object> findTotalPre(Map<String, Object> map) {
		List<Map<String, Object>> list = drawDaoImpl.findTotalPre(map);
		return list.size()==0?null:list.get(0);
	}
}
