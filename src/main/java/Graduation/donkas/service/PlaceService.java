package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.VerifyHostDto;
import Graduation.donkas.dto.BookingDto.BookingDto;
import Graduation.donkas.dto.PlaceDto.PlaceDto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private static final String BASEURL = "https://api.odcloud.kr/api/nts-businessman/v1/validate";

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    public boolean registration(PlaceDto placeDto) throws Exception {
        Connection connection = new Connection();
        connection.createPlace(placeDto);
        return true;
    }

    public boolean verifyHost(VerifyHostDto verifyHostDto) throws Exception {
        URI uri = UriComponentsBuilder.fromUriString(BASEURL)
            .queryParam("serviceKey", "{serviceKey}")
            .build(serviceKey);

        ResponseEntity<String> responseEntity = RestClient.create()
            .post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(verifyHostDto)
            .retrieve()
            .toEntity(String.class);

        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();
            if (responseBody != null) {
                return isValidResponse(responseBody);
            }
        }
        return false;
    }

    private boolean isValidResponse(String responseBody) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(responseBody);

        String statusCode = jsonObject.get("status_code").toString();
        if (!statusCode.equals("OK")) return false;

        Object data = ((JSONArray)jsonObject.get("data")).get(0);
        String validValue = ((JSONObject)data).get("valid").toString();

        return validValue.equals("01");
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
