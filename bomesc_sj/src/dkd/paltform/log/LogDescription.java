package dkd.paltform.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface LogDescription {
	//实体的类型,例如销售订单,部门等
	public String entityType() default "业务实体";
	//用户进行操作的描述
	public String description() default "描述模板";
	//操作类型,例如订单审核,部门新增,出货单查询
	public String operaterType() default "业务操作";
	//实体ID
	public String entityId() default "";
	//单据编号
	public String entityCode() default "";
	//大分类 --》进港、出港、基础设置、系统设置
	public String entityGroup() default "";
}
