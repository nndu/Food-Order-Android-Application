package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CustomerMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        Button logOut = findViewById(R.id.btnCustomerLogOut);
        Button accountManagement = findViewById(R.id.btnCustomerAccountManagement);
        Button orderHistory = findViewById(R.id.btnCustomerOrderHistory);
        Button newOrder = findViewById(R.id.btnCustomerNewOrder);

        logOut.setOnClickListener(view -> finish());

        accountManagement.setOnClickListener(view -> startActivity(new Intent(CustomerMain.this, CustomerAccountManagement.class)));

        orderHistory.setOnClickListener(view -> startActivity(new Intent(CustomerMain.this, CustomerOrderHistory.class)));

        newOrder.setOnClickListener(view -> startActivity(new Intent(CustomerMain.this, CustomerNewOrder.class)));
    }
}