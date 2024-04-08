package Graduation.donkas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class DateService {

    public long calculateDayOfStay(String checkinDate, String checkoutDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate date1 = LocalDate.parse(checkinDate, formatter);
        LocalDate date2 = LocalDate.parse(checkoutDate, formatter);

        long days = DAYS.between(date1, date2);
        return days;
    }
}
