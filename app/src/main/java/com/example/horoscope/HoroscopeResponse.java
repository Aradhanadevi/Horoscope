package com.example.horoscope;

public class HoroscopeResponse {
    private Data data;
    private boolean success;

    public static class Data {
        private String date;
        private String horoscope_data;

        public String getDate() {
            return date;
        }

        public String getHoroscopeData() {
            return horoscope_data;
        }
    }

    public Data getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
