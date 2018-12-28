package com.gmail.thangvnnc.emi.DBServer.API;

import com.gmail.thangvnnc.emi.DBServer.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://103.7.40.108:8118/api/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

