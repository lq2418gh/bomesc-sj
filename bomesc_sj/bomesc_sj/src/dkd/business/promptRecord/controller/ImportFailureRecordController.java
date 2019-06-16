package dkd.business.promptRecord.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.business.promptRecord.service.ImportFailureRecordService;
import dkd.paltform.base.controller.BaseController;

@Controller
@RequestMapping(value = "/importFailure")
public class ImportFailureRecordController extends BaseController{
	@Autowired
	private ImportFailureRecordService importFailureRecordService;
	/**
	 * @date 2017年11月30日
	 * @author gaoxp
	 * @param request
	 * @return
	 * 描述：查询方法
	 */
	@RequestMapping(value = "/recordList.do")
	@ResponseBody
	public String query(HttpServletRequest request) {
		return importFailureRecordService.getScrollData(getParam(request)).toJson();
	}

}
