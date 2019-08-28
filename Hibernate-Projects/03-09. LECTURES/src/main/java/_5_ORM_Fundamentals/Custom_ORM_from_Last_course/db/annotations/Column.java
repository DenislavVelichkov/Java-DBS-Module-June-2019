package ORM_Fundamentals_5.Custom_ORM_from_Last_course.db.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
}
