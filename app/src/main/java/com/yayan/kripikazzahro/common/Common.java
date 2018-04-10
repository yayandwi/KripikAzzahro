package com.yayan.kripikazzahro.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yayan.kripikazzahro.Model.Ongkir;
import com.yayan.kripikazzahro.Model.Request;
import com.yayan.kripikazzahro.Model.User;

/**
 * Created by yayan dwi kusuma on 1/25/2018.
 */

public class Common {


    public static User currUser;
    public static Ongkir currOngkir;



    public static String convertCodeStatus(String status) {
        if(status.equals("0"))
            return "Order Dikirim";
        else if(status.equals("1"))
            return "Dalam Proses Pengiriman";
        else
            return "Telah Dikirim";
    }

    public static final String DELETE = "Delete";
    public static final String PILIH = "Pilih";
    public static final String TIDAK = "Tidak";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";

    public static boolean isConnectedInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager !=null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return  true;
                }
            }
        }
        return false;
    }


}


