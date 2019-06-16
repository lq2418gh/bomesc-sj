package dkd.paltform.log.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.paltform.base.controller.BaseController;
import dkd.paltform.log.service.LogService;

@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController{
	@Autowired
	private LogService logService;
	@RequestMapping(value = "/query.do")
	@ResponseBody
	public String seachRole(HttpServletRequest request) {
		return logService.getScrollData(getParam(request)).toJson();
	}
}
