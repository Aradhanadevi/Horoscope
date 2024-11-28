package com.example.horoscope;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HoroscopeApi {
    @GET("get-horoscope/daily")
    Call<HoroscopeResponse> getDailyHoroscope(@Query("sign") String sign, @Query("day") String day);
}

