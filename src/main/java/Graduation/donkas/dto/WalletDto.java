package Graduation.donkas.dto;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    String walletID;
    String userID;
    String money;

    public WalletDto(JsonObject asset){
        this.walletID = asset.get("walletID").getAsString();
        this.userID = asset.get("userID").getAsString();
        this.money = asset.get("money").getAsString();
    }

}
