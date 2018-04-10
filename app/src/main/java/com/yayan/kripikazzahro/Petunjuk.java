package com.yayan.kripikazzahro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Petunjuk extends AppCompatActivity {

    TextView HyperLink;
    Spanned Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petunjuk);


        HyperLink = (TextView)findViewById(R.id.link_wa);

        Text = Html.fromHtml(
                "<a href='https://api.whatsapp.com/send?phone=6287823224291&text=%20Bukti%20transfer%20pemesanan%20Kripik%20Pisang%20Azzahro'>087823224291</a>");

        HyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        HyperLink.setText(Text);



    }
}
