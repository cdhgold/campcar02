package com.cdh.campcar.util;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public class ApiClient {
    public interface ApiInterface {

        @Multipart
        @POST("/car/carIns/saveMultiApp")
        Call<ResponseBody> uploadProduct(
                @PartMap Map<String, RequestBody> params,
                @PartMap Map<String, RequestBody> files);

    }

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), s);
    }

}
