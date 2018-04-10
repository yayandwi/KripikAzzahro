package com.yayan.kripikazzahro;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone,edtNama,edtPassword;
    Button btnSignUp;
    TextView txtReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtNama = (MaterialEditText) findViewById(R.id.edtNama);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);

        txtReg =(TextView)findViewById(R.id.txtregister);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Questrial-Regular.ttf");
        txtReg.setTypeface(face);



        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Common.isConnectedInternet(getBaseContext())) {

                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Silahkan Tunggu...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Nomer telepon sudah digunakan ", Toast.LENGTH_SHORT);
                            } else {
                                mDialog.dismiss();
                                User user = new User(edtNama.getText().toString(), edtPassword.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Sign Up Berhasil!!! ", Toast.LENGTH_LONG);
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(SignUp.this, "Ceck Koneksi Interne Anda", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
    }
}
