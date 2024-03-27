package Graduation.donkas.controller;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.responseResult.ResponseResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    @PostMapping("/test")
    public ResponseResult<?> test() throws Exception {
        Connection connection = new Connection();
        //connection.initLedger();
        JsonArray bookings = connection.GetAllBookings().getAsJsonArray();
        System.out.println(bookings);
        List<BookingDto> bookingDtos = new ArrayList<>();
        for(int i=0;i<bookings.size();++i){
            JsonObject asset = bookings.get(i).getAsJsonObject();
            BookingDto bookingDto = new BookingDto(asset);
            bookingDtos.add(bookingDto);
        }
        return ResponseResult.body(bookingDtos);
    }

    @PostMapping("/testPlace")
    public ResponseResult<?> testPlace() throws Exception {
        Connection connection = new Connection();
        connection.initLedger();
        JsonArray places = connection.GetAllPlaces().getAsJsonArray();
        System.out.println(places);
        List<PlaceDto> placeDtos = new ArrayList<>();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            PlaceDto placeDto = new PlaceDto(asset);
            placeDtos.add(placeDto);
        }
        return ResponseResult.body(placeDtos);
    }
}
