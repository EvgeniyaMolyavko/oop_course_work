package ua.opnu.labwork2.dto.validTime;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventTimeValidator.class)
public @interface ValidEventTime {

    String message() default
            "End time must not be earlier than start time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
