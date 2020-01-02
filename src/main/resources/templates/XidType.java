package org.freedesktop.xjb;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({FIELD, LOCAL_VARIABLE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XidType {
    String value();
}
