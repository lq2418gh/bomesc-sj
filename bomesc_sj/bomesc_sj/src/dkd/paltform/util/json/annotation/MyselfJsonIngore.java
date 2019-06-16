package dkd.paltform.util.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author MAOHT
 * 一个实体序列化为json对象时，定义忽略序列化输出的字段
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyselfJsonIngore {
    boolean value() default true;
}
