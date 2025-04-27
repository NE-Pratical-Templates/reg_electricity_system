package rw.reg.Electricity.v1.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Define the custom annotation
@Documented
@Constraint(validatedBy = AmountConstraintValidator.class) // Specify the validator
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE}) // Where the annotation can be applied
@Retention(RetentionPolicy.RUNTIME) // Keep it at runtime for reflection
public @interface ValidAmount {

    // Default error message
    String message() default "Amount must be a valid number and divisible by 100";

    // Grouping constraints
    Class<?>[] groups() default {};

    // To carry additional metadata during validation
    Class<? extends Payload>[] payload() default {};
}
