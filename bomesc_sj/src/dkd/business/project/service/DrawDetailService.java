package dkd.business.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dkd.business.project.dao.DrawDetailDetailDaoImpl;
import dkd.business.project.dao.DrawDetailHeadDaoImpl;
import dkd.business.project.domain.DrawDetailDetail;
import dkd.business.project.domain.DrawDetailHead;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.sequence.service.SequenceService;
import dkd.paltform.util.Common;
import dkd.paltform.util.StringUtils;

@Service
@Transactional
public class DrawDetailService extends BaseService<DrawDetailHead>{

	@Autowired
	private DrawDetailHeadDaoImpl drawDetailHeadDaoImpl;

	@Autowired
	private DrawDetailDetailDaoImpl drawDetailDetailDaoImpl;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public BaseDao<DrawDetailHead> getDao() {
		return drawDetailHeadDaoImpl;
	}

	public String saveDrawDetail(DrawDetailHead drawDetailHead) {
		String projectId = projectService.getProjectId(drawDetailHead.getJob_no());
		drawDetailHead.setProject(projectId);
		if(StringUtils.isEmpty(drawDetailHead.getId())){
			String no = sequenceService.getFlowNo("drawDetail", null)+"";
			drawDetailHead.setList_no("DQI"+Common.formatCode(no, 9));
			for(DrawDetailDetail detail:drawDetailHead.getDrawDetailHeads()){
				detail.setDrawDetailHead(drawDetailHead);
				detail.setList_head(drawDetailHead.getList_no());
			}
			drawDetailHeadDaoImpl.create(drawDetailHead);
		}else{
			for(DrawDetailDetail detail:drawDetailHead.getDrawDetailHeads()){
				detail.setDrawDetailHead(drawDetailHead);
				detail.setList_head(drawDetailHead.getList_no());
			}
			drawDetailHeadDaoImpl.update(drawDetailHead);
		}
		return drawDetailHead.getId();
	}
	
	public void delete(String id){
		//drawDetailDetailDaoImpl.delete(id);
		DrawDetailDetail detail = drawDetailDetailDaoImpl.findByID(id);
		drawDetailDetailDaoImpl.delete(id);
		flush();
		DrawDetailHead head = drawDetailHeadDaoImpl.findByID(detail.getDrawDetailHead().getId());
		if(null!=head){
			List<Map<String, Object>> details=drawDetailDetailDaoImpl.findByHead(head.getId());
			if(details.size()==0){
				drawDetailHeadDaoImpl.delete(head.getId());
			}
		}
	}

	public Map<String, Object> find(String project, String major, String month, String drawType,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("major", major);
		map.put("month", month); 
		map.put("drawType", drawType);
		List<Map<String, Object>> list = drawDetailDetailDaoImpl.find(map);
		return list.size()>0?list.get(0):null;
	}

	public Boolean checkValidate(String project, String major, String month,String id) {
		Map<String,Object> map = new HashMap<>();
		map.put("project", project);
		map.put("month", month);
		map.put("id", id);
		map.put("major", dictionaryService.findByID(major));
		return drawDetailHeadDaoImpl.checkValidate(map).isEmpty()?false:true;
	}
	
	public Map<String, Object> getTotalPre(String project, String major, String month, String drawType,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("major", major);
		map.put("month", month); 
		map.put("drawType", drawType);
		List<Map<String, Object>> list = drawDetailDetailDaoImpl.getTotalPre(map);
		return list.size()>0?list.get(0):null;
	}
}
