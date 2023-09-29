package com.example.userrestservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
}
