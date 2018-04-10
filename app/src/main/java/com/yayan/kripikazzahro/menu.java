package com.yayan.kripikazzahro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;
import com.yayan.kripikazzahro.Database.Database;
import com.yayan.kripikazzahro.Interface.ItemClickListener;
import com.yayan.kripikazzahro.Model.Category;

import com.yayan.kripikazzahro.Model.Order;
import com.yayan.kripikazzahro.Service.ListenOrder;
import com.yayan.kripikazzahro.ViewHolder.MenuViewHolder;
import com.yayan.kripikazzahro.common.Common;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;

    TextView txtFullName;
    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;


    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    CounterFab fab;

    ImageButton button1;

    ImageButton HyperLink;
    Spanned Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Bellico.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Inisialisais Firebase

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        Paper.init(this);


        //   button1.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //   public void onClick(View v) {

        //     HyperLink = (ImageButton) findViewById(R.id.BtnChat);

        //   Text = Html.fromHtml(
        //         "<a href='https://api.whatsapp.com/send?phone=6287823224291&text=%20Bukti%20transfer%20pemesanan%20Kripik%20Pisang%20Azzahro'>087823224291</a>");

        //HyperLink.setImageMo(LinkMovementMethod.getInstance());
        //HyperLink.setText(Text);
        // Intent intent = new Intent(menu.this, Alternatifmotor.class);
        //     menu.this.startActivity(intent);

        //
        fab = (CounterFab) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu.this, Cart.class);
                menu.this.startActivity(intent);

                Toast.makeText(getApplicationContext(), "Keranjang Belanja", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setCount(new Database(this).getCountCart());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set nama dari user

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currUser.getNama());

        //Load Menu
        recyler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);
        // layoutManager = new LinearLayoutManager(this);
        // recyler_menu.setLayoutManager(layoutManager);

        recyler_menu.setLayoutManager(new GridLayoutManager(this, 2));

        if (Common.isConnectedInternet(this))
            loadMenu();
        else {
            Toast.makeText(this, "Ceck Koneksi Interne Anda", Toast.LENGTH_SHORT).show();
            return;
        }

        //Register Service
        Intent service = new Intent(menu.this, ListenOrder.class);
        startService(service);


        //  updateToken(FirebaseInstanceId.getInstance().getToken());


    }


    // private void updateToken(String token) {
    //    FirebaseDatabase db = FirebaseDatabase.getInstance();
    //    DatabaseReference tokens = db.getReference("Tokens");
    //  Token data = new Token(token,false);// false karena token mengirim client spp
    //    tokens.child(Common.currUser.getPhone()).setValue(data);


    // }

//CTRL+O


    @Override
    protected void onPostResume() {
        super.onPostResume();

        fab.setCount(new Database(this).getCountCart());
    }

    private void loadMenu() {

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.txtMenuName.setText(model.getNama());
                Picasso.with(getBaseContext()).load(model.getGambar())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(menu.this, "" + clickItem.getNama(), Toast.LENGTH_SHORT).show();
                        //Sourcode untuk mendapatkan ID categori dan mengirim ke activity baru
                        Intent foodDetail = new Intent(menu.this, FoodDetail.class);

                        //Karena Categori ID adalah key, jadi kita harus mendapatkan key item tersebut
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);


                    }
                });


            }
        };

        recyler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh)
            loadMenu();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(menu.this, home.class);
            startActivity(intent);


            Toast.makeText(getApplicationContext(), "Deskripsi tentang kripik Azzahro", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.petunjuk) {
            Intent petunjukIntent = new Intent(menu.this, Petunjuk.class);
            startActivity(petunjukIntent);
            Toast.makeText(this, "Petunjuk penggunaan Kripik Azzahro", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.produk) {
            Intent produkIntent = new Intent(menu.this, menu.class);
            startActivity(produkIntent);
            Toast.makeText(this, "Produk Kripik Azzahro", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.order) {
            Intent orderIntent = new Intent(menu.this, OrderStatus.class);
            startActivity(orderIntent);
            Toast.makeText(this, "Status Orderan", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.cek) {
            Intent orderIntent = new Intent(menu.this, ListProvinsi.class);
            startActivity(orderIntent);
            Toast.makeText(this, "Cek Ongkos kirim JNE", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.lokasi) {
            Intent orderIntent = new Intent(menu.this, Lokasi.class);
            startActivity(orderIntent);
            Toast.makeText(this, "Lokasi Kripik pisang Azzahro", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.info) {
            Intent orderIntent = new Intent(menu.this, Info.class);
            startActivity(orderIntent);
            Toast.makeText(this, "Info Kripik pisang Azzahro", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.keluar) {

            //Delete Remeber dan Password
            Paper.book().destroy();

            Intent keluar = new Intent(menu.this, SignIn.class);
            keluar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(keluar);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

}
