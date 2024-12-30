package com.xrm.tickly.ticketing_app.validation;

import com.xrm.tickly.ticketing_app.dto.TicketDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class TicketDatesValidator implements ConstraintValidator<ValidTicketDates, TicketDTO> {
    @Override
    public boolean isValid(TicketDTO ticket, ConstraintValidatorContext context) {
        if (ticket.getStartDate() == null || ticket.getDueDate() == null) {
            return true; // Let @NotNull handle null validation
        }
        return ticket.getDueDate().isAfter(ticket.getStartDate());
    }
}