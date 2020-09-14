package com.tnd.pw.action.api.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.authens.TokenRequest;
import jdk.nashorn.internal.parser.Token;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigServiceRequest extends TokenRequest {

    @SerializedName("id")
    private Long id;
}
