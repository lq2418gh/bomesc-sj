package dkd.paltform.util.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @author MAOHT
 * 当一个实体被另一个实体引用，被引用的实体定义需要JSON输出的字段
 * 例如订单引用部门，订单JSON序列化时，需要序列化部门的字段
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RefJsonWrite {
   boolean value() default true;
}
