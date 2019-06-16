package dkd.business.dataParamConfig.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.dataParamConfig.domain.MajorAllocation;
import dkd.business.dataParamConfig.service.MajorAllocationService;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.controller.BaseController;
import dkd.paltform.dictionary.domain.Dictionary;
import dkd.paltform.dictionary.service.DictionaryService;
@Controller
@RequestMapping(value = "/majorAllocation")
public class MajorAllocationController extends BaseController  {
	@Autowired
	private MajorAllocationService majorAllocationService;
	
	@Autowired
	private DictionaryService dictionaryService;
	/**
	 * @date 2017年11月29日
	 * @param request
	 * @return
	 * 描述：查看功能
	 */
	@RequestMapping(value="/view.do")
	@ResponseBody
	public String query(HttpServletRequest request){
 		QueryResult<Map<String,Object>> findMA = majorAllocationService.findMA();
 		if (findMA.getRows().size()==0) {
			Dictionary findByCode = dictionaryService.findByCode("professional");
			String id = findByCode.getId();
			QueryResult<Map<String,Object>> findByParent = dictionaryService.findAllByParent(id);//根据专业code  查询字典
			return findByParent.toJson();
		}else{
			return findMA.toJson();
		}
	}
	/**
	 * @date 2017年11月29日
	 * @param model
	 * @return
	 * 描述：跳转编辑页面
	 */
	@RequestMapping(value="/edit.do")
	public String edit(ModelMap model) {
		return "business/dataParamConfig/majorAllocation/majorAllocationEdit";
	}
	/**
	 * @date 2017年11月29日
	 * @param model
	 * @param majorAllocation
	 * @return
	 * 描述：保存方法
	 */
	@RequestMapping(value="/save.do")
	@ResponseBody
	public String save(ModelMap model,@RequestBody List<MajorAllocation> majorAllocationList) {
		QueryResult<Map<String,Object>> findMA = majorAllocationService.findMA();
		if (findMA.getRows().size()==0) {
			for (MajorAllocation majorAllocation : majorAllocationList) {
				majorAllocationService.save(majorAllocation);
			}
		} else{
			for (MajorAllocation majorAllocation : majorAllocationList) {
				
				majorAllocationService.update(majorAllocation);
			}
		}
		return toWriteSuccess("保存成功！");
	}
}
