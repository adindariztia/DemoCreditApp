package com.example.adindariztiaputri.creditapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowApplication extends AppCompatActivity {
    BaseApiService mApiService;
    private TextView saName, saPhone, saEmail, saDob, saAddress, saCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_application);

        Button btn = findViewById(R.id.show_application);
        mApiService = UtilsApi.getApiService();

        saName = findViewById(R.id.show_name);
        saPhone = findViewById(R.id.show_phone);
        saEmail = findViewById(R.id.show_email);
        saDob = findViewById(R.id.show_dob);
        saAddress = findViewById(R.id.show_address);
        saCity = findViewById(R.id.show_city);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("CreditApp", MODE_PRIVATE);
                String idPreference = preferences.getString("id","");
                
                showApplicationData(idPreference);

//                Toast.makeText(ShowApplication.this, "idnya jeng jeng: " + idPreference, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showApplicationData(String idPreference) {
        mApiService.showApplication(idPreference).enqueue(new Callback<DataApplication>() {
            @Override
            public void onResponse(Call<DataApplication> call, Response<DataApplication> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(ShowApplication.this, "berhasil", Toast.LENGTH_SHORT).show();
                    saName.setText(response.body().getName());
                    saPhone.setText(response.body().getPhone());
                    saEmail.setText(response.body().getEmail());
                    saDob.setText(response.body().getDateOfBirth());
                    saAddress.setText(response.body().getAddress());
                    saCity.setText(response.body().getRef1Address());

                }else {
                    Toast.makeText(ShowApplication.this, "gagal fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataApplication> call, Throwable t) {

            }
        });
    }
}
