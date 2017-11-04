package cn.jjm.annotation;

import java.lang.annotation.*;


/**
 * baili
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableMonitorConfiguration {
}
