package dkd.paltform.log;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import dkd.paltform.authority.domain.User;
import dkd.paltform.log.domain.Log;
import dkd.paltform.log.service.LogService;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.UUIDGenerator;

@Aspect
@Component
public class LogAspect {
	@Autowired
	private LogService logService;
	// 缓存有@OperationDescription方法参数名称
	private static Map<String, String[]> parameterNameCaches = new ConcurrentHashMap<String, String[]>();
	// 缓存SPEL Expression
	private static Map<String, Expression> spelExpressionCaches = new ConcurrentHashMap<String, Expression>();
	private static ExpressionParser parser = new SpelExpressionParser();
	private static LocalVariableTableParameterNameDiscoverer parameterNameDiscovere = new LocalVariableTableParameterNameDiscoverer();

	/**
	 * 定义日志拦截的切面,只要在方法上标注dkd.paltform.log.LogDescription标签,进行日志记录
	 */
	@Pointcut("@annotation(dkd.paltform.log.LogDescription)")
	public void logwrite() {

	}

	@After("logwrite()")
	public void doAfter(JoinPoint joinPoint) {
		LogDescription logDescription = getLogDescription(joinPoint);
		if (logDescription == null)
			return;
		String entityType = logDescription.entityType();
		String operaterType = logDescription.operaterType();
		String descriptionTemp = logDescription.description();
		String entityIdTemplate = logDescription.entityId();
		String entityCodeTemplate = logDescription.entityCode();
		
		String address = SpringUtil.getRequest().getRemoteAddr();
		address = address.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : address;
		User user = SpringUtil.getCurrentUser();
		String username = user.getName();
		Log log = new Log();
		log.setId(UUIDGenerator.getUUID());
		log.setEntity_type(entityType);
		log.setIp_address(address);
		log.setOperater_type(operaterType);
		log.setOperate_time(new Date());
		log.setUser_uame(username);
		log.setDescription(getDescription(joinPoint, descriptionTemp));
		log.setEntity_id(getDescription(joinPoint, entityIdTemplate));
		log.setEntity_code(getDescription(joinPoint, entityCodeTemplate));
		log.setEntity_group(logDescription.entityGroup());
		logService.create(log);
	}

	/**
	 * 取得方法对应的注解
	 * 
	 * @param joinPoint
	 * @return
	 */
	private LogDescription getLogDescription(JoinPoint joinPoint) {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		@SuppressWarnings("rawtypes")
		Class targetClass = null;
		try {
			targetClass = Class.forName(targetName);
		} catch (ClassNotFoundException e) {
			// 写日志产生的异常,暂时不报出 ???
			// throw new RuntimeException(e);
		}
		Method[] methods = targetClass.getMethods();
		LogDescription logDescription = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				@SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					logDescription = method.getAnnotation(LogDescription.class);
					return logDescription;
				}
			}
		}
		return null;
	}

	/**
	 * 根据描述模板,取得描述内容
	 * 
	 * @param joinPoint
	 * @param descriptionTemp
	 *            :描述的模板
	 * @return
	 */
	private String getDescription(JoinPoint joinPoint, String descriptionTemp) {
		// 取得方法中对应的参数
		String methodLongName = joinPoint.getSignature().toLongString();
		String[] parameterNames = parameterNameCaches.get(methodLongName);
		if (parameterNames == null) {
			Method method = getMehtod(joinPoint);
			parameterNames = parameterNameDiscovere.getParameterNames(method);
			parameterNameCaches.put(methodLongName, parameterNames);
		}

		// add args to expression context
		StandardEvaluationContext context = new StandardEvaluationContext();
		Object[] args = joinPoint.getArgs();
		if (args.length == parameterNames.length) {
			for (int i = 0, len = args.length; i < len; i++)
				context.setVariable(parameterNames[i], args[i]);
		}

		// cacha expression
		Expression expression = spelExpressionCaches.get(descriptionTemp);
		if (expression == null) {
			expression = parser.parseExpression(descriptionTemp, new TemplateParserContext());
			spelExpressionCaches.put(descriptionTemp, expression);
		}
		String value = expression.getValue(context, String.class);
		return value;
	}

	private Method getMehtod(JoinPoint joinPoint) {
		String methodLongName = joinPoint.getSignature().toLongString();
		Method[] methods = joinPoint.getTarget().getClass().getMethods();
		Method method = null;

		for (int i = 0, len = methods.length; i < len; i++) {

			if (methodLongName.contains(methods[i].toString())) {
				method = methods[i];
				break;
			}
		}
		return method;
	}

}
