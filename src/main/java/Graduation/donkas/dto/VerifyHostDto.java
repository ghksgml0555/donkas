package Graduation.donkas.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyHostDto {

    List<BusinessDto> businesses = new ArrayList<>();

    public VerifyHostDto(BusinessDto businessDto) {
        businesses.add(businessDto);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class BusinessDto {
        String b_no;
        String start_dt;
        String p_nm;
    }
}