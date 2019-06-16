/**
 * 该注解用于绑定请求参数（JSON字符串）
 */
package dkd.paltform.util.json.request;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: RequestJsonNode
 * @Description: 该注解用于绑定请求参数（JSON字符串）
 * @author CST-TONGLZ
 * @date 2015-9-16 下午3:18:51
 * 
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJsonNode {

	/**
	 * 用于绑定的请求参数名字
	 */
	String value() default "";

	/**
	 * 是否必须，默认是
	 */
	boolean required() default true;

}
