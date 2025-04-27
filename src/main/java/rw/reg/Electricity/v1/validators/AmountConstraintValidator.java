package rw.reg.Electricity.v1.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Implement the custom validation logic
public class AmountConstraintValidator implements ConstraintValidator<ValidAmount, Double> {

    @Override
    public void initialize(ValidAmount constraintAnnotation) {
        // Initialization if needed (not required in this case)
    }

    @Override
    public boolean isValid(Double amount, ConstraintValidatorContext context) {
        if (amount == null) {
            return true; // You can return false here if you want to reject null values as invalid
        }

        // Check if the amount is divisible by 100 (i.e., remainder is 0 when divided by 100)
        return amount % 100 == 0;
    }
}
