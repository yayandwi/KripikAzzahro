package com.yayan.kripikazzahro;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yayan.kripikazzahro.Model.User;
import com.yayan.kripikazzahro.common.Common;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignIn;
    TextView txtLogin;
    com.rey.material.widget.CheckBox cek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);

        txtLogin =(TextView)findViewById(R.id.txtlogin);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Questrial-Regular.ttf");
        txtLogin.setTypeface(face);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        cek = (com.rey.material.widget.CheckBox) findViewById(R.id.ckbRemember);

        //inisial Paper
        Paper.init(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Common.isConnectedInternet(getBaseContext())) {
                    //save user dan password
                    if(cek.isChecked())
                    {
                        Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY,edtPassword.getText().toString());
                    }


                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Silahkan Tunggu...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //cek jika user ada dalam database
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                                //mendapatkan informasi user

                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString()); //set No telepone
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent menuIntent = new Intent(SignIn.this, menu.class);
                                    Common.currUser = user;
                                    startActivity(menuIntent);
                                    finish();

                                    Toast.makeText(SignIn.this, "Login Berhasil !!!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SignIn.this, "Password Salah!!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else

                {
                    Toast.makeText(SignIn.this, "Ceck Koneksi Interne Anda", Toast.LENGTH_SHORT).show();
                    return;
                }




            }
        });


    }

}