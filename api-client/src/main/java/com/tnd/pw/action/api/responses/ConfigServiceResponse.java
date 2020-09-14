package com.tnd.pw.action.api.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ConfigServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_user_profile")
    private List<UserRepresentation> userProfiles;

    public ConfigServiceResponse() {
    }

    public ConfigServiceResponse(List<UserRepresentation> userProfiles) {
        this.userProfiles = userProfiles;
    }
}
