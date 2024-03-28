package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<BookingDto> myLease(String id) throws Exception {
        Connection connection = new Connection();
        JsonArray bookings = connection.GetAllBookings().getAsJsonArray();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            BookingDto bookingDto = new BookingDto(asset);
            if(bookingDto.getGuestID().equals(id)){
                bookingDtos.add(bookingDto);
            }
        }
        return bookingDtos;
    }

}
