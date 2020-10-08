package com.tnd.pw.action.dbservice;

import com.google.gson.Gson;
import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.common.constants.Protocol;
import com.tnd.dbservice.common.representation.DBServiceRequest;
import com.tnd.dbservice.common.representation.DBServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DBServiceApiClient extends AbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBServiceApiClient.class);
    private Gson gson = new Gson();

    public BaseResponse<DBServiceResponse.QueryResult> selectSQL(String url, DBServiceRequest request) throws IOException {
        LOGGER.info("DBServiceApiClient instance = {} --- {}", this.toString(), this.hashCode());
        BaseResponse<DBServiceResponse.QueryResult> response = client.sendRequest(url, Protocol.GET.name(), request);
        response.setData(gson.fromJson(gson.toJson(response.getData()), DBServiceResponse.QueryResult.class));
        return response;
    }

    public BaseResponse<Boolean> executeSQL(String url, DBServiceRequest request) throws IOException {
        LOGGER.info("DBServiceApiClient instance = {} --- {}", this.toString(), this.hashCode());
        return client.sendRequest(url, Protocol.POST.name(), request);
    }
}
