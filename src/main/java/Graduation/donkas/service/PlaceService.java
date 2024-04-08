package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    /*public PlaceDto GetPlaceById(String placeId) throws Exception {
        Connection connection = new Connection();
        JsonArray places = connection.GetPlaceById(placeId).getAsJsonArray();
        PlaceDto placeDto = new PlaceDto();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            placeDto = new PlaceDto(asset);
        }
        return placeDto;
    }*/

    public PlaceDto GetPlaceById(String placeId) throws Exception {
        Connection connection = new Connection();
        JsonArray places = connection.GetAllPlaces().getAsJsonArray();
        PlaceDto returnPlaceDto = new PlaceDto();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            PlaceDto placeDto = new PlaceDto(asset);
            if(placeDto.getPlaceID().equals(placeId)){
                returnPlaceDto =placeDto;
                break;
            }
        }
        return returnPlaceDto;
    }

    public List<PlaceDto> searchByAddress(String address) throws Exception {
        Connection connection = new Connection();
        JsonArray places = connection.GetAllPlaces().getAsJsonArray();
        List<PlaceDto> placeDtos = new ArrayList<>();
        for(int i=0;i<places.size();++i){
            JsonObject asset = places.get(i).getAsJsonObject();
            PlaceDto placeDto = new PlaceDto(asset);
            if(placeDto.getAddress().equals(address)){
                placeDtos.add(placeDto);
            }
        }
        return placeDtos;
    }

}
