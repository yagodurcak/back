package ar.com.indumet.workload.models.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.auth.UserRecord;
import lombok.Data;

import java.util.Map;

@Data
public class UserDataVO {

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("photo_url")
    private String photoURL;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("custom_claims")
    private Map<String, Object> customClaims;

    public UserDataVO(){}

    public UserDataVO(UserRecord userRecord){

        displayName = userRecord.getDisplayName();
        email = userRecord.getEmail();
        phoneNumber = userRecord.getPhoneNumber();
        photoURL = userRecord.getPhotoUrl();
        providerId = userRecord.getProviderId();
        uid = userRecord.getUid();
        customClaims = userRecord.getCustomClaims();
    }
}
