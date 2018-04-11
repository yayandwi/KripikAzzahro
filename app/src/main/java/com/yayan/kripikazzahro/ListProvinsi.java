package com.yayan.kripikazzahro;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yayan.kripikazzahro.Model.MessageEvent;
import com.yayan.kripikazzahro.adapter.CityAdapter;
import com.yayan.kripikazzahro.adapter.ExpedisiAdapter;
import com.yayan.kripikazzahro.adapter.ProvinceAdapter;
import com.yayan.kripikazzahro.api.ApiService;
import com.yayan.kripikazzahro.api.ApiUrl;
import com.yayan.kripikazzahro.Model.city.ItemCity;
import com.yayan.kripikazzahro.Model.cost.ItemCost;
import com.yayan.kripikazzahro.Model.expedisi.ItemExpedisi;
import com.yayan.kripikazzahro.Model.province.ItemProvince;
import com.yayan.kripikazzahro.Model.province.Result;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListProvinsi extends AppCompatActivity {

    private EditText etToProvince;
    private EditText etToCity;
    private EditText etCourier;

    private EditText searchList;
    private ListView mListView;

    private ProvinceAdapter adapter_province;
    private List<Result> ListProvince = new ArrayList<Result>();

    private CityAdapter adapter_city;
    private List<com.yayan.kripikazzahro.Model.city.Result> ListCity = new ArrayList<com.yayan.kripikazzahro.Model.city.Result>();

    private ExpedisiAdapter adapter_expedisi;
    private List<ItemExpedisi> listItemExpedisi = new ArrayList<ItemExpedisi>();
    private AlertDialog.Builder alert;
    private AlertDialog ad;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_provinsi);

        etToProvince = findViewById(R.id.etToProvince);
        etToCity = findViewById(R.id.etToCity);
        etCourier = findViewById(R.id.etCourier);

        etToProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpProvince(etToProvince, etToCity);
            }
        });

        etToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etToProvince.getTag().equals("")) {
                        etToProvince.setError("Please chooise your to province");
                    } else {
                        popUpCity(etToCity, etToProvince);
                    }

                } catch (NullPointerException e) {
                    etToProvince.setError("Please chooise your to province");
                }
            }
        });

        etCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpExpedisi(etCourier);
            }
        });
        Button btnProcess = findViewById(R.id.btnpilih);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = etToCity.getText().toString();
                String expedisi = etCourier.getText().toString();
                if (destination.equals("")) {
                    etToCity.setError("Please input your destination");
                } else if (expedisi.equals("")) {
                    etCourier.setError("Please input your ItemExpedisi");
                } else {
                    progressDialog = new ProgressDialog(ListProvinsi.this);
                    progressDialog.setMessage("Please wait..");
                    progressDialog.show();
                    // 501 adalah yogyakarta
                    getCoast("501", etToCity.getTag().toString(), 501, expedisi);
                }
            }
        });
    }

    public void popUpProvince(final EditText etProvince, final EditText etCity) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(ListProvinsi.this);
        alert.setTitle("List ListProvince");
        alert.setMessage("select your province");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherProvince(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = alertLayout.findViewById(R.id.listItem);

        ListProvince.clear();
        adapter_province = new ProvinceAdapter(ListProvinsi.this, ListProvince);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                Result cn = (Result) o;

                etProvince.setError(null);
                etProvince.setText(cn.getProvince());
                etProvince.setTag(cn.getProvinceId());

                etCity.setText("");
                etCity.setTag("");

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(ListProvinsi.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        getProvince();
    }

    public void popUpCity(final EditText etCity, final EditText etProvince) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(ListProvinsi.this);
        alert.setTitle("List City");
        alert.setMessage("select your city");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = alertLayout.findViewById(R.id.listItem);

        ListCity.clear();
        adapter_city = new CityAdapter(ListProvinsi.this, ListCity);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                com.yayan.kripikazzahro.Model.city.Result cn = (com.yayan.kripikazzahro.Model.city.Result) o;

                etCity.setError(null);
                etCity.setText(cn.getCityName());
                etCity.setTag(cn.getCityId());

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(ListProvinsi.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getCity(etProvince.getTag().toString());
    }

    public void popUpExpedisi(final EditText etExpedisi) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);
        alert = new AlertDialog.Builder(ListProvinsi.this);
        alert.setTitle("List Expedisi");
        alert.setMessage("select your Expedisi");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = alertLayout.findViewById(R.id.listItem);

        listItemExpedisi.clear();
        adapter_expedisi = new ExpedisiAdapter(ListProvinsi.this, listItemExpedisi);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                ItemExpedisi cn = (ItemExpedisi) o;

                etExpedisi.setError(null);
                etExpedisi.setText(cn.getName());
                etExpedisi.setTag(cn.getId());

                ad.dismiss();
            }
        });

        getExpedisi();

    }

    private class MyTextWatcherProvince implements TextWatcher {

        private View view;

        private MyTextWatcherProvince(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_province.filter(editable.toString());
                    break;
            }
        }
    }

    private class MyTextWatcherCity implements TextWatcher {

        private View view;

        private MyTextWatcherCity(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.searchItem:
                    adapter_city.filter(editable.toString());
                    break;
            }
        }
    }

    public void getProvince() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemProvince> call = service.getProvince();

        call.enqueue(new Callback<ItemProvince>() {
            @Override
            public void onResponse(Call<ItemProvince> call, Response<ItemProvince> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        Result itemProvince = new Result(
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince()
                        );

                        ListProvince.add(itemProvince);
                        mListView.setAdapter(adapter_province);
                    }

                    adapter_province.setList(ListProvince);
                    adapter_province.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(ListProvinsi.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemProvince> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListProvinsi.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCity(String id_province) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCity> call = service.getCity(id_province);
        call.enqueue(new Callback<ItemCity>() {
            @Override
            public void onResponse(Call<ItemCity> call, Response<ItemCity> response) {
                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        com.yayan.kripikazzahro.Model.city.Result itemProvince = new com.yayan.kripikazzahro.Model.city.Result(
                                response.body().getRajaongkir().getResults().get(a).getCityId(),
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince(),
                                response.body().getRajaongkir().getResults().get(a).getType(),
                                response.body().getRajaongkir().getResults().get(a).getCityName(),
                                response.body().getRajaongkir().getResults().get(a).getPostalCode()
                        );

                        ListCity.add(itemProvince);
                        mListView.setAdapter(adapter_city);
                    }

                    adapter_city.setList(ListCity);
                    adapter_city.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(ListProvinsi.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCity> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListProvinsi.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getExpedisi() {
        ItemExpedisi itemItemExpedisi;
        itemItemExpedisi = new ItemExpedisi("1", "pos");
        listItemExpedisi.add(itemItemExpedisi);
        itemItemExpedisi = new ItemExpedisi("2", "tiki");
        listItemExpedisi.add(itemItemExpedisi);
        itemItemExpedisi = new ItemExpedisi("3", "jne");
        listItemExpedisi.add(itemItemExpedisi);
        mListView.setAdapter(adapter_expedisi);
        adapter_expedisi.setList(listItemExpedisi);
        adapter_expedisi.filter("");
    }

    public void getCoast(String origin, String destination, int weight, String courier) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCost> call = service.getCost(
                "a1db3093f82a112d84f38383f7119efb",
                origin,
                destination,
                weight,
                courier
        );

        call.enqueue(new Callback<ItemCost>() {
            @Override
            public void onResponse(Call<ItemCost> call, final Response<ItemCost> response) {
                Log.v("COST", "JSON : " + new Gson().toJson(response));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    int statusCode = response.body().getRajaongkir().getStatus().getCode();
                    if (statusCode == 200) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View alertLayout = inflater.inflate(R.layout.custom_dialog_result, null);
                        alert = new AlertDialog.Builder(ListProvinsi.this);
                        alert.setTitle("Result Cost");
                        alert.setMessage("this result your search");
                        alert.setView(alertLayout);
                        alert.setCancelable(true);

                        ad = alert.show();
                        TextView tv_destination = alertLayout.findViewById(R.id.tv_destination);
                        TextView tv_expedisi = alertLayout.findViewById(R.id.tv_expedisi);
                        final TextView tv_coast = alertLayout.findViewById(R.id.tv_coast);
                        TextView tv_time = alertLayout.findViewById(R.id.tv_time);
                        Button btnPilih = alertLayout.findViewById(R.id.btn_pilih);
                        Button btnTidak = alertLayout.findViewById(R.id.btn_tidak);
                        btnTidak.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ad.dismiss();
                            }
                        });

                        final ItemCost param = response.body();
                        if (param != null) {
                            if (param.getRajaongkir().getResults().size() != 0) {
                                etToProvince.setText("");
                                etToCity.setText("");
                                etCourier.setText("");

                                tv_destination.setText(param.getRajaongkir().getDestinationDetails().getCityName() + " (Postal Code : " + response.body().getRajaongkir().getDestinationDetails().getPostalCode() + ")");
                                tv_expedisi.setText(param.getRajaongkir().getResults().get(0).getCosts().get(0).getDescription() + " (" + response.body().getRajaongkir().getResults().get(0).getName() + ") ");
                                tv_coast.setText("Rp. " + param.getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue().toString());
                                tv_time.setText(param.getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getEtd() + " (Days)");

                                btnPilih.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.e("CLICK PILIH", "PILIH");
                                        EventBus.getDefault().post(new MessageEvent(param.getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue(), "Keterangan yang masuk"));
                                        finish();
                                    }
                                });
                            } else {
                                Toast.makeText(ListProvinsi.this, "Ongkos kirim tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        String message = response.body().getRajaongkir().getStatus().getDescription();
                        Toast.makeText(ListProvinsi.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(ListProvinsi.this, error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCost> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ListProvinsi.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}