package com.skillbox.socialnet.validation.annotation;

import com.skillbox.socialnet.util.Constants;
import com.skillbox.socialnet.validation.validator.NotificationTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotificationTypeValidator.class)
public @interface ValidNotificationType {

    String message() default Constants.NOT_VALID_SETTING_TYPE_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
