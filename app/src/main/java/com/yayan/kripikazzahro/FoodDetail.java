package com.yayan.kripikazzahro;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;
import com.yayan.kripikazzahro.Database.Database;
import com.yayan.kripikazzahro.Model.Food;
import com.yayan.kripikazzahro.Model.Order;
import com.yayan.kripikazzahro.common.Common;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ColabLig.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_food_detail);


        // Firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //init view
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
      

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //

                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getNama(),
                        numberButton.getNumber(),
                        currentFood.getHarga(),
                        currentFood.getBerat(),
                        currentFood.getGambar()

                ));

                Toast.makeText(FoodDetail.this, "Menambahkan Keranjang Belanja", Toast.LENGTH_SHORT).show();

               // mBadge.setNumber(++count);

            }
        });





        food_description = (TextView)findViewById(R.id.food_description);
        food_name = (TextView)findViewById(R.id.food_name);
        food_price = (TextView)findViewById(R.id.food_price);
        food_image = (ImageView)findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() != null)
                foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
        {
            if(Common.isConnectedInternet(getBaseContext()))
            getDetailFood(foodId);

            else
            {
                Toast.makeText(FoodDetail.this, "Ceck Koneksi Interne Anda", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);

                //Gambar
                Picasso.with(getBaseContext()).load(currentFood.getGambar())
                .into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getNama());

                food_price.setText(currentFood.getHarga());

                food_name.setText(currentFood.getNama());

                food_description.setText(currentFood.getDeskripsi());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
