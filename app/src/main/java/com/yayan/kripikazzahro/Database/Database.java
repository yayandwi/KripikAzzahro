package com.yayan.kripikazzahro.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.yayan.kripikazzahro.Model.Ongkir;
import com.yayan.kripikazzahro.Model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yayan dwi kusuma on 1/29/2018.
 */

public class Database extends SQLiteAssetHelper{
    private static final String DB_NAME="Coba.db";
    private static final int DB_VER=2;


    public Database(Context context) {
        super(context, DB_NAME,null, DB_VER);
    }

    public List<Order> getCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductName","ProductId","Quantity","Price","Berat","Image"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

         List<Order> result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Berat")),
                        c.getString(c.getColumnIndex("Image"))
                       // c.getString(c.getColumnIndex("Tarif"))

                ));




            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Berat,Image) VALUES('%s','%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getBerat(),
                order.getImage());
         //       order.getTarif());
        db.execSQL(query);
    }

    public void cleanCart()
    {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public int getCountCart() {

        int count=0;

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetail");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do{
                    count = cursor.getInt(0);

        }while (cursor.moveToNext());
        }
        return count;
    }



    }

