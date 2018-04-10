package com.yayan.kripikazzahro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yayan.kripikazzahro.Model.Category;
import com.yayan.kripikazzahro.Model.Ongkir;
import com.yayan.kripikazzahro.Model.Request;
import com.yayan.kripikazzahro.ViewHolder.MenuViewHolder;
import com.yayan.kripikazzahro.ViewHolder.OngkirViewHolder;
import com.yayan.kripikazzahro.ViewHolder.OrderViewHolder;
import com.yayan.kripikazzahro.common.Common;

import io.paperdb.Paper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Cek_Ongkir extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Ongkir,OngkirViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference ongkir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek__ongkir);



        database = FirebaseDatabase.getInstance();
        ongkir = database.getReference("Ongkir");

        recyclerView = (RecyclerView)findViewById(R.id.listOngkir);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        loadOngkir();
        //  else
        // loadOrders(getIntent().getStringExtra("userPhone"));
        //loadOrders(Common.currUser.getPhone());




    }


    private void loadOngkir() {
        adapter = new FirebaseRecyclerAdapter<Ongkir, OngkirViewHolder>(
                Ongkir.class,
                R.layout.ongkir_layout,
                OngkirViewHolder.class,
                ongkir

        ) {
            @Override
            protected void populateViewHolder(OngkirViewHolder viewHolder, final Ongkir model, final int position) {

                viewHolder.CekTujuan.setText(model.getTujuan());
                viewHolder.Cektarifku.setText(model.getTarif());



                viewHolder.BtnPilih.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent ongkirku = new Intent(Cek_Ongkir.this,Cart.class);
                        Common.currOngkir = model;
                        ongkirku.putExtra("MenuId",adapter.getRef(position).getKey());
                        startActivity(ongkirku);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }






}












