package next.sh.driving_school.exception.constraint;

import javax.validation.Payload;

public @interface ValidUsername {
    String message() default "Username must not contain spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
