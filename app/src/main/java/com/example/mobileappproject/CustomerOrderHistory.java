package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class CustomerOrderHistory extends AppCompatActivity {
    int clientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        clientId = sharedPreferences.getInt("id", 0);
    }
}