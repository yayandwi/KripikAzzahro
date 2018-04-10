package com.yayan.kripikazzahro.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yayan.kripikazzahro.Interface.ItemClickListener;
import com.yayan.kripikazzahro.R;

/**
 * Created by yayan dwi kusuma on 1/31/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder  {

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);



    }
}
