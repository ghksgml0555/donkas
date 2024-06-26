package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto.BookingDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
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

    public boolean refuse(BookingDto bookingDto) throws Exception {
        Connection connection = new Connection();
        connection.refuse(bookingDto);
        return true;
    }

    public boolean cancel(BookingDto bookingDto) throws Exception {
        Connection connection = new Connection();
        connection.cancel(bookingDto);
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
    public List<BookingDto> myPlaceLeaseRequestList(String id) throws Exception {
        Connection connection = new Connection();
        JsonArray bookings = connection.GetAllBookings().getAsJsonArray();
        List<BookingDto> bookingDtos = new ArrayList<>();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            BookingDto bookingDto = new BookingDto(asset);
            if(bookingDto.getHostID().equals(id) && bookingDto.getBookingStatus().equals("예약신청")){
                bookingDtos.add(bookingDto);
            }
        }
        return bookingDtos;
    }

    /*public BookingDto GetBookingById(String bookingId) throws Exception {
        Connection connection = new Connection();
        JsonArray bookings = connection.GetBookingById(bookingId).getAsJsonArray();
        BookingDto bookingDto = new BookingDto();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            bookingDto = new BookingDto(asset);
        }
        return bookingDto;
    }*/

    public BookingDto GetBookingById(String bookingId) throws Exception {
        Connection connection = new Connection();
        JsonArray bookings = connection.GetAllBookings().getAsJsonArray();
        BookingDto returnBookingDto = new BookingDto();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            BookingDto bookingDto = new BookingDto(asset);
            if(bookingDto.getBookingID().equals(bookingId)){
                returnBookingDto =new BookingDto(asset);
            }
        }
        return returnBookingDto;
    }
}
