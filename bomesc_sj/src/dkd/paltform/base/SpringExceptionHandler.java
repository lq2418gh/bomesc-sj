package dkd.paltform.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import dkd.paltform.base.controller.BaseController;

@ControllerAdvice
public class SpringExceptionHandler extends BaseController{
	/**
     * 异常处理
     * @param ex
     * @param request
     * @Description:
     */
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleSystemException(Exception ex,HttpServletRequest request,HttpServletResponse response){
		ex.printStackTrace();
		String causemessage;
		if (ex.getCause() != null) {
			causemessage = ex.getCause().getMessage();
			if(causemessage != null){
				if (causemessage.contains("Session失效")) {
					return toWriteFailAndLoginAgain("session失效");
				} else if (causemessage.contains("Row was updated or deleted by another transaction")) {
					return toWriteFail("并发错误");
				} else if (causemessage.contains("org.hibernate.exception.ConstraintViolationException") && causemessage.contains("DELETE") && causemessage.contains("REFERENCE")) {
					return toWriteFail("当前对象已经使用，不允许删除");
				}
			}
		}
		// 业务异常
		if (ex.getCause() instanceof BusinessException) {
			BusinessException bex = (BusinessException) ex.getCause();
			return toWriteFail(bex.getIndex(),bex.getField(),bex.getMessage());
		}
		// 登录时,用户名 密码错误,抛出业务异常
		else if (ex instanceof BusinessException) {
			BusinessException bex = (BusinessException) ex;
			return toWriteFail(bex.getIndex(),bex.getField(),bex.getMessage());
		}
		// 技术异常,提示发生系统异常,请联系管理员,500
		else {
			return toWriteFail("系统发生异常,请联系管理员");
		}
	}
}