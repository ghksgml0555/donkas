package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    public boolean LeaseRequest(BookingDto bookingDto) throws Exception {
        Connection connection = new Connection();
        connection.createBooking(bookingDto);
        return true;
    }

    public boolean accept(BookingDto bookingDto) throws Exception {
        Connection connection = new Connection();
        connection.accept(bookingDto);
        return true;
    }
}
