package column;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Column {
    String label();
    boolean isPrimeKey() default false;
    boolean isNullable() default false;
    int max() default 255;
    int min() default 0;
}
