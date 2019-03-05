package com.example.adindariztiaputri.creditapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    private List<ListTemp> arrayProvinsi, arrayKabupaten, arrayKecamatan, arrayKelurahan;
    private List<ListKodepos> arrayKodepos;
    private String mProvinsi, mKabupaten, mKecamatan, mKelurahan, mKodepos, mDate;

    Spinner spinnerProvince, spinnerKabupaten, spinnerKecamatan, spinnerKelurahan, spinnerPostalCode;
    private TextView showDate;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText mName = findViewById(R.id.nama);
        final EditText mPhone = findViewById(R.id.phone_number);
        final EditText mEmail = findViewById(R.id.email);
        final EditText mStreetAddress = findViewById(R.id.street_address);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = mName.getText().toString();
                String sPhone = mPhone.getText().toString();
                String sEmail = mEmail.getText().toString();
                String sStreetAddress = mStreetAddress.getText().toString();
                String addressCity = mProvinsi + " " + mKabupaten + " " + mKecamatan + " " + mKelurahan;



                DataJson dataJson = new DataJson(sName, sPhone,sEmail, mDate, sStreetAddress, addressCity, mKodepos );
                sendApplication(dataJson);

                showApplicationPage();
            }
        });

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        spinnerProvince = findViewById(R.id.province);
        spinnerKabupaten = findViewById(R.id.kabupaten);
        spinnerKecamatan = findViewById(R.id.kecamatan);
        spinnerKelurahan = findViewById(R.id.kelurahan);
        spinnerPostalCode = findViewById(R.id.kodepos);

        showDate = findViewById(R.id.show_date);

        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int id_prov = arrayProvinsi.get(position).getId();
                mProvinsi= arrayProvinsi.get(position).getNama();

                if (id_prov != 0) {
                    showKabupaten(id_prov);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerKabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "Id kab: " + id, Toast.LENGTH_SHORT).show();
//                long id_kab = id + 1;
                int id_kab = arrayKabupaten.get(position).getId();
                mKabupaten = arrayKabupaten.get(position).getNama();
//                Toast.makeText(mContext, "kabupaten: " + mKabupaten, Toast.LENGTH_SHORT).show();
                if (id_kab != 0){
                    showKecamatan(id_kab);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "Id Kec : "+ id, Toast.LENGTH_SHORT).show();
//                long id_kec = id + 1;
                int id_kec = arrayKecamatan.get(position).getId();
                mKecamatan = arrayKecamatan.get(position).getNama();
//                Toast.makeText(mContext, "kecamatan: "+ mKecamatan, Toast.LENGTH_SHORT).show();
                if (id_kec != 0){
                    showKelurahan(id_kec);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "Id kel: "+ id, Toast.LENGTH_SHORT).show();
//                long id_kel = id + 1;
                String kelurahan = arrayKelurahan.get(position).getNama();
                mKelurahan = arrayKelurahan.get(position).getNama();
//                Toast.makeText(mContext, "kelurahan: " + mKelurahan, Toast.LENGTH_SHORT).show();
                if (kelurahan != null){
                    showPostalCode(kelurahan);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerPostalCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mKodepos = arrayKodepos.get(position).getKodepos();
//                Toast.makeText(mContext, "kodepos: "+ mKodepos, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, "Id Kodepos: " + id, Toast.LENGTH_SHORT).show();
//                long id_kodepos = id + 1;
//                if (id_kodepos != 0){
//
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showProvince();

    }

    private void showApplicationPage() {
        Intent intent = new Intent(this, ShowApplication.class);
        startActivity(intent);
    }

    public void showProvince() {
        Call<ResponsTemp> call = mApiService.getProvinsi();
        call.enqueue(new Callback<ResponsTemp>() {
            @Override
            public void onResponse(Call<ResponsTemp> call, Response<ResponsTemp> response) {
                arrayProvinsi = new ArrayList<>();
                List<ListTemp> listTemps = response.body().getAlltempat();
                for (ListTemp lt : listTemps){
                    arrayProvinsi.add(lt);
                }

                ArrayAdapter<ListTemp> adapter = new ArrayAdapter<ListTemp>(mContext,android.R.layout.simple_spinner_dropdown_item, arrayProvinsi);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProvince.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsTemp> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showKabupaten(long id) {
        Call<ResponsTemp> call = mApiService.getKabupaten(id);
        call.enqueue(new Callback<ResponsTemp>() {
            @Override
            public void onResponse(Call<ResponsTemp> call, Response<ResponsTemp> response) {
                arrayKabupaten = new ArrayList<>();
                List<ListTemp> listTemps = response.body().getAlltempat();
                for (ListTemp listKab : listTemps){
                    arrayKabupaten.add(listKab);
                }

                ArrayAdapter<ListTemp> adapter = new ArrayAdapter<ListTemp>(mContext,android.R.layout.simple_spinner_dropdown_item, arrayKabupaten);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKabupaten.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsTemp> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showKecamatan(long id) {
        Call<ResponsTemp> call = mApiService.getKecamatan(id);
        call.enqueue(new Callback<ResponsTemp>() {
            @Override
            public void onResponse(Call<ResponsTemp> call, Response<ResponsTemp> response) {
                arrayKecamatan = new ArrayList<>();
                List<ListTemp> listTemps = response.body().getAlltempat();
                for (ListTemp listKec : listTemps){
                    arrayKecamatan.add(listKec);
                }

                ArrayAdapter<ListTemp> adapter = new ArrayAdapter<ListTemp>(mContext,android.R.layout.simple_spinner_dropdown_item, arrayKecamatan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKecamatan.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsTemp> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showKelurahan(long id) {
        Call<ResponsTemp> call = mApiService.getKelurahan(id);
        call.enqueue(new Callback<ResponsTemp>() {
            @Override
            public void onResponse(Call<ResponsTemp> call, Response<ResponsTemp> response) {
                arrayKelurahan = new ArrayList<>();
                List<ListTemp> listTemps = response.body().getAlltempat();
                for (ListTemp listKel : listTemps){
                    arrayKelurahan.add(listKel);
                }

                ArrayAdapter<ListTemp> adapter = new ArrayAdapter<ListTemp>(mContext,android.R.layout.simple_spinner_dropdown_item, arrayKelurahan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKelurahan.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsTemp> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showPostalCode(String kelurahan) {
        Call<ResponsKodepos> call = mApiService.getKodepos(kelurahan);
        call.enqueue(new Callback<ResponsKodepos>() {
            @Override
            public void onResponse(Call<ResponsKodepos> call, Response<ResponsKodepos> response) {
                arrayKodepos = new ArrayList<>();
                List<ListKodepos> listKodepos = response.body().getAllKodePos();
                for (ListKodepos listKel : listKodepos){
                    arrayKodepos.add(listKel);
                }

                ArrayAdapter<ListKodepos> adapter = new ArrayAdapter<ListKodepos>(mContext,android.R.layout.simple_spinner_dropdown_item, arrayKodepos);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPostalCode.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponsKodepos> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string +
                "/" + day_string + "/" + year_string);
        mDate = dateMessage;

        Toast.makeText(this, "Date: " + dateMessage,
                Toast.LENGTH_SHORT).show();

        showDate.setText(dateMessage);
    }

    public void sendApplication(DataJson dataJson){
        mApiService.sendApp(dataJson).enqueue(new Callback<Output>() {
            @Override
            public void onResponse(Call<Output> call, Response<Output> response) {
                if(response.isSuccessful()){

                    String id = response.body().getOutput();
                    SharedPreferences preferences = getSharedPreferences("CreditApp", MODE_PRIVATE);
                    preferences.edit().putString("id", id).commit();

                    Toast.makeText(MainActivity.this, "ini id nya: "+id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, response.message() + " " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Output> call, Throwable t) {

            }
        });
    }


}
