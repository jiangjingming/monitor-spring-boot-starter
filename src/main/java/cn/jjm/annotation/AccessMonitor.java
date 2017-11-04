package cn.jjm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by jiangjingming on 2017/10/26.
 */
@Target({ TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessMonitor {

    /**
     * 注解方法名
     * @return
     */
    String methodName() default "";

}
