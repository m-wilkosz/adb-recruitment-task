package com.tvprogram.utils;

import com.tvprogram.model.Program;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeConverter {

    public static void convertToCurrentDate(List<Program> programs) {
        LocalDate today = LocalDate.now();
        for (Program program : programs) {
            LocalDate programDate = program.getStartTime().toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(programDate, today);

            if (daysBetween != 0) {
                program.setStartTime(program.getStartTime().plusDays(daysBetween));
                program.setEndTime(program.getEndTime().plusDays(daysBetween));
            }
        }
    }
}