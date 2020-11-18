package com.det.skillinvillage.remote;
import static com.det.skillinvillage.Constants.Constants.BASE_URL;
public class Class_ApiUtils {

    public static Interface_userservice getUserService() {
        return Class_RetrofitClient.getClient(BASE_URL).create(Interface_userservice.class);
    }
    public static String Image_baseUrl="http://mis.detedu.org:8089/api/Authentication";

}
