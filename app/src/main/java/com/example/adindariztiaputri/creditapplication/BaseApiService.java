package com.example.adindariztiaputri.creditapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("/creditapplication/1.0/DaftarProvinsi")
    Call<ResponsTemp> getProvinsi();

    @GET("/creditapplication/1.0/DaftarKabupaten")
    Call<ResponsTemp> getKabupaten(@Query("provinsi_id") long id);

    @GET("/creditapplication/1.0/DaftarKecamatan")
    Call<ResponsTemp> getKecamatan(@Query("kabupaten_id") long id);

    @GET("/creditapplication/1.0/DaftarKelurahan")
    Call<ResponsTemp> getKelurahan(@Query("kecamatan_id") long id);

    @GET("creditapplication/1.0/DaftarKodepos")
    Call<ResponsKodepos> getKodepos(@Query("kelurahan") String kelurahan);


//    @Headers({
//            "accept: application/json",
//            "Content-type: application/json"
//    })
//    @FormUrlEncoded
//    @POST("/creditapplication/1.0/CreateApplication")
//    Call<Output> sendApp(@Field("name_") String name,
//                                 @Field("phone_") String phone,
//                                 @Field("email_") String email,
//                                 @Field("date_of_birth_") String date,
//                                 @Field("address_") String address,
//                                 @Field("ref1_address_") String address_city,
//                                 @Field("zip_code_") String zip_code);

    @Headers({
            "accept: application/json",
            "Content-type: application/json"
    })
    @POST("/creditapplication/1.0/CreateApplication")
    Call<Output> sendApp(@Body DataJson dataJson);

    @GET("/creditapplication/1.0/GetApplication")
    Call<DataApplication> showApplication(@Query("id") String id);

}
