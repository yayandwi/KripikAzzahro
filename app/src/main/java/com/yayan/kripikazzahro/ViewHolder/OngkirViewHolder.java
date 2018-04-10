package com.yayan.kripikazzahro.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yayan.kripikazzahro.Interface.ItemClickListener;
import com.yayan.kripikazzahro.R;

/**
 * Created by yayan dwi kusuma on 1/31/2018.
 */

public class OngkirViewHolder extends RecyclerView.ViewHolder  {

    public TextView CekTujuan,Cektarifku;

   private ItemClickListener itemClickListener;
    public Button BtnPilih,BtnBatal;


    public OngkirViewHolder(View itemView) {
        super(itemView);

        CekTujuan = (TextView)itemView.findViewById(R.id.kota);
        Cektarifku = (TextView)itemView.findViewById(R.id.tarif);


        BtnPilih = (Button)itemView.findViewById(R.id.btnpilih);


    }


}
