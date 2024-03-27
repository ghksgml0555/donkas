package Graduation.donkas.service;

import Graduation.donkas.connection.Connection;
import Graduation.donkas.dto.PlaceDto;
import Graduation.donkas.responseResult.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class PlaceService {

    public boolean registration() throws Exception {
        Connection connection = new Connection();
        connection.createBooking();
        return true;
    }
}
