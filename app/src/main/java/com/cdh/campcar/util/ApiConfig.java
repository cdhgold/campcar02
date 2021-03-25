package com.cdh.campcar.util;

import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConfig {

    @Multipart
    @POST("/car/carIns/saveMultiApp")
    Call<ResponseBody> uploadProduct(
            @PartMap Map<String, RequestBody> params
            ,@Part MultipartBody.Part img1
            ,@Part MultipartBody.Part img2
            ,@Part MultipartBody.Part img3
            ,@Part MultipartBody.Part img4
            ,@Part MultipartBody.Part img5
            ,@Part MultipartBody.Part img6
            ,@Part MultipartBody.Part img7
            ,@Part MultipartBody.Part img8
            ,@Part MultipartBody.Part img9
            ,@Part MultipartBody.Part img10


    );
}
