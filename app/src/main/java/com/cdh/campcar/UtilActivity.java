package com.cdh.campcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cdh.campcar.Fragment.CartFragment;
import com.cdh.campcar.Fragment.DdFragment;
import com.cdh.campcar.Fragment.HomeFragment;
import com.cdh.campcar.Fragment.MyFragment;
import com.cdh.campcar.Fragment.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UtilActivity   {

    public static void showALert(String nm, Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("필수입력");
        builder.setMessage(nm+ " 을 입력하세요!");

        builder.show();
    }

}
