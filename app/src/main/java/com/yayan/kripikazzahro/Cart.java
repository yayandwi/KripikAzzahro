package com.yayan.kripikazzahro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yayan.kripikazzahro.Database.Database;
import com.yayan.kripikazzahro.Model.MessageEvent;
import com.yayan.kripikazzahro.Model.Ongkir;
import com.yayan.kripikazzahro.Model.Order;
import com.yayan.kripikazzahro.Model.Request;

import com.yayan.kripikazzahro.ViewHolder.CartAdapter;
import com.yayan.kripikazzahro.ViewHolder.OngkirViewHolder;
import com.yayan.kripikazzahro.common.Common;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import info.hoang8f.widget.FButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests, ongkir;

    TextView txtTotalPrice, txtOngkir, txtTotalBelanja;
    FButton btnPlace, btnOngkir;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this); // Eventbus
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ColabLig.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_cart);


        //Firebase

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Inisialisasi

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = findViewById(R.id.total);
        txtOngkir = findViewById(R.id.ongkir);
        txtTotalBelanja = findViewById(R.id.totalBelanja);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnOngkir = findViewById(R.id.btnCekOngkir);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cart.size() > 0)

                    showAlertDialog();
                else
                    Toast.makeText(Cart.this, "Keranjang Belanja Masing Kosong  !!!", Toast.LENGTH_SHORT).show();

            }
        });

        loadListFood();
        btnOngkir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ongkirku = new Intent(Cart.this, ListProvinsi.class);
                startActivity(Ongkirku);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // Eventbus
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.e("EVENTBUS", event.toString());
        Locale locale = new Locale("in", "ID");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtOngkir.setText(String.valueOf(fmt.format(event.getOngkir())));
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Inputkan");
        alertDialog.setMessage("Alamat Lengkap :");

        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );

        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //buat new Request
                Request request = new Request(
                        Common.currUser.getPhone(),
                        Common.currUser.getNama(),
                        edtAddress.getText().toString(),
                        txtTotalBelanja.getText().toString(),
                        cart
                );

                //Tambah ke Firebase request
                //jika akan memakai system.currentMill dengan key
                // String order_number = );
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                //hapus Keranjang
                new Database(getBaseContext()).cleanCart();

                Toast.makeText(Cart.this, "Terima Kasih Sudah berbelanja,Pesanan Anda Akan Segera Dikirim", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                dialoginterface.dismiss();

            }
        });
        alertDialog.show();
    }

    //  private void sendNotificationOrder(String order_number) {
    //    DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
    //  Query data = tokens.orderByChild("isServerToken").equalTo(true);// mendapatkan semua node dengan serveristoken true
    //data.addValueEventListener(new ValueEventListener() {
    //  @Override
    //public void onDataChange(DataSnapshot dataSnapshot) {
    //  for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
    // {
    //   Token serverToken = postSnapShot.getValue(Token.class);
    //}

    //}

    //   @Override
    // public void onCancelled(DatabaseError databaseError) {

//            }
    //      });
    //}

    private void loadListFood() {
        cart = new Database(this).getCart();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        int totalWeight = 0;
        for (Order item : cart) {
            totalWeight = totalWeight + (Integer.parseInt(item.getBerat()) * Integer.parseInt(item.getQuantity()));
        }

        //kalkluasi ongkir
        int ongkir = 0;
        try {
            ongkir = getOngkir(501, 501, totalWeight, "jne");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //kalkulasi perhitungan pembelian

        int total = 0;
        for (Order order : cart)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("in", "ID");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        int totalBelanja = total + ongkir;

        txtTotalPrice.setText(fmt.format(total));
        txtOngkir.setText(String.valueOf(fmt.format(ongkir)));
        txtTotalBelanja.setText(String.valueOf(fmt.format(totalBelanja)));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int posotion) {
        cart.remove(posotion);
        new Database(this).cleanCart();
        for (Order item : cart)
            new Database(this).addToCart(item);

        loadListFood();
    }

    private int getOngkir(int origin, int destionation, int weight, String courier) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "origin=" + origin + "&destination=" + destionation + "&weight=" + weight + "&courier=" + courier);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://api.rajaongkir.com/starter/cost")
                .post(body)
                .addHeader("key", "a1db3093f82a112d84f38383f7119efb")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject obj = new JSONObject(responseBody);
        JSONObject rajaOngkir = obj.getJSONObject("rajaongkir");
        JSONArray results = rajaOngkir.getJSONArray("results");
        JSONArray costs = results.getJSONObject(0).getJSONArray("costs");
        JSONArray cost = costs.getJSONObject(0).getJSONArray("cost");
        int value = cost.getJSONObject(0).getInt("value");
        return value;
    }
}




