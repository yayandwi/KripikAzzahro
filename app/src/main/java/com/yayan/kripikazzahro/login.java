package com.yayan.kripikazzahro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yayan.kripikazzahro.Model.User;
import com.yayan.kripikazzahro.common.Common;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    Button btnSigIn,btnSigUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnSigIn = (Button)findViewById(R.id.btnSignIn);
        btnSigUp = (Button)findViewById(R.id.btnSignUp);

        txtSlogan =(TextView)findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Questrial-Regular.ttf");
        txtSlogan.setTypeface(face);

        //Inisial Paper
        Paper.init(this);

        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin = new Intent(login.this,SignIn.class);
                startActivity(signin);

            }
        });

        btnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(login.this,SignUp.class);
                startActivity(signup);

            }
        });

        //Check remember
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user !=null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login(user,pwd);
        }


    }
    private void login(final String phone, final String pwd){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        if (Common.isConnectedInternet(getBaseContext())) {
            //save user dan password

            final ProgressDialog mDialog = new ProgressDialog(login.this);
            mDialog.setMessage("Silahkan Tunggu...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //cek jika user ada dalam database
                    if (dataSnapshot.child(phone).exists()) {

                        //mendapatkan informasi user

                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone); //set No telepone
                        if (user.getPassword().equals(pwd)) {
                            {

                                Intent menuIntent = new Intent(login.this, menu.class);
                                Common.currUser = user;
                                startActivity(menuIntent);
                                finish();
                            }
                            Toast.makeText(login.this, "Login Berhasil !!!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(login.this, "Password Salah!!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    } else {
                        mDialog.dismiss();
                        Toast.makeText(login.this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        else

        {
            Toast.makeText(login.this, "Ceck Koneksi Interne Anda", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
