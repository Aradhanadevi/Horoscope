package com.example.horoscope;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView horoscopeTextView;
    private EditText signEditText; // EditText to capture user input
    private Button fetchButton;    // Button to trigger horoscope fetch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        horoscopeTextView = findViewById(R.id.horoscopeTextView);
        signEditText = findViewById(R.id.signEditText);
        fetchButton = findViewById(R.id.buttonFetch);

        // Set an OnClickListener on the button
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the sign entered by the user
                String sign = signEditText.getText().toString().trim();

                // Check if the sign is empty
                if (sign.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a zodiac sign", Toast.LENGTH_SHORT).show();
                    return; // Exit if no sign is entered
                }

                // Fetch horoscope for the entered sign
                fetchHoroscope(sign);
            }
        });
    }

    private void fetchHoroscope(String sign) {
        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://horoscope-app-api.vercel.app/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the HoroscopeApi interface
        HoroscopeApi horoscopeApi = retrofit.create(HoroscopeApi.class);

        // Make the API call with the user-provided sign
        Call<HoroscopeResponse> call = horoscopeApi.getDailyHoroscope(sign, "TODAY");

        call.enqueue(new Callback<HoroscopeResponse>() {
            @Override
            public void onResponse(Call<HoroscopeResponse> call, Response<HoroscopeResponse> response) {
                if (response.isSuccessful()) {
                    HoroscopeResponse horoscopeResponse = response.body();
                    if (horoscopeResponse != null && horoscopeResponse.isSuccess()) {
                        // Handle the horoscope data
                        String horoscope = horoscopeResponse.getData().getHoroscopeData();
                        String date = horoscopeResponse.getData().getDate();
                        // Display the horoscope in the TextView
                        horoscopeTextView.setText("Horoscope for " + date + ": " + horoscope);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to retrieve horoscope", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<HoroscopeResponse> call, Throwable t) {
                // Handle failure
                Log.e("HoroscopeAPI", "Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching horoscope", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
