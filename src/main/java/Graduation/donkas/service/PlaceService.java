package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.responseResult.ResponseResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    public boolean registration(PlaceDto placeDto) throws Exception {
        Connection connection = new Connection();
        connection.createPlace(placeDto);
        return true;
    }

    public List<PlaceDto> myPlace(String id) throws Exception {
        Connection connection = new Connection();
        JsonArray places = connection.GetAllPlaces().getAsJsonArray();
        List<PlaceDto> placeDtos = new ArrayList<>();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            PlaceDto placeDto = new PlaceDto(asset);
            if(placeDto.getHostID().equals(id)){
                placeDtos.add(placeDto);
            }
        }
        return placeDtos;
    }
}
