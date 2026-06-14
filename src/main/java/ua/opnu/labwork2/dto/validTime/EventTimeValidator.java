package ua.opnu.labwork2.dto.validTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.opnu.labwork2.dto.RideDto;

public class EventTimeValidator
        implements ConstraintValidator<ValidEventTime, RideDto> {
            @Override
            public boolean isValid(
                    RideDto dto,
                    ConstraintValidatorContext context
            ) {
                if (dto == null) {
                    return true;
                }

                if (dto.startTime() == null) {
                    return true;
                }

                if (dto.endTime() == null) {
                    return true;
                }

                return !dto.endTime().isBefore(dto.startTime());
            }
        }