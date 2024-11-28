package com.example.horoscope;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("GET https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign={zodiac_sign}&day={date}") // Replace with your API endpoint
    Call<HoroscopeResponse> getHoroscope(@Path("sign") String zodiacSign);
}