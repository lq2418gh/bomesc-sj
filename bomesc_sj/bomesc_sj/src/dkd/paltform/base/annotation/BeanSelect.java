package dkd.paltform.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanSelect {
	String fieldName() default "";
	String searchType() default "";
	boolean isSearch() default true;//是否搜索条件
	boolean isShow() default true;//配置显示列
	boolean gain() default true;//反射时是否取值
}