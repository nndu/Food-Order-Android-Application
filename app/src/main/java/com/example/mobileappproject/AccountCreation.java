package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AccountCreation extends AppCompatActivity {
    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        dbh = new DatabaseHelper(this);
        RadioButton customer = findViewById(R.id.radbtnCustomer);
        RadioButton restaurant = findViewById(R.id.radbtnRestaurant);
        EditText name = findViewById(R.id.editAccountCreationName);
        EditText city = findViewById(R.id.editAccountCreationCity);
        EditText phone = findViewById(R.id.editAccountCreationPhone);
        EditText email = findViewById(R.id.editAccountCreationEmailAddress);
        EditText username = findViewById(R.id.editAccountCreationUsername);
        EditText password = findViewById(R.id.editAccountCreationPassword);
        EditText confirmPassword = findViewById(R.id.editAccountCreationConfirmPassword);
        TextView restaurantPrompt = findViewById(R.id.txtAccountCreationRestaurant);
        EditText restaurantDescription = findViewById(R.id.editRestaurantDescription);
        Button createAccount = findViewById(R.id.btnAccountCreationCreateAccount);

        customer.setOnClickListener(view -> {
            restaurantPrompt.setVisibility(View.GONE);
            restaurantDescription.setVisibility(View.GONE);
        });

        restaurant.setOnClickListener(view -> {
            restaurantPrompt.setVisibility(View.VISIBLE);
            restaurantDescription.setVisibility(View.VISIBLE);
        });

        createAccount.setOnClickListener(view -> {

            // If name is empty
            if (name.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();

            // If city is empty
            else if (city.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a city", Toast.LENGTH_SHORT).show();

            // If phone is empty
            else if (phone.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();

            // If email is empty
            else if (email.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a email", Toast.LENGTH_SHORT).show();

            // If username is empty
            else if (username.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();

            // If password is empty
            else if (password.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show();

            // If confirm password is empty
            else if (confirmPassword.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Confirm your password", Toast.LENGTH_SHORT).show();

            // If user is setting up a restaurant account and restaurant description is empty
            else if (restaurant.isChecked() && restaurantDescription.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a description for your restaurant", Toast.LENGTH_SHORT).show();

            // If passwords are not the same
            else if (!password.getText().toString().equals(confirmPassword.getText().toString())) Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();

            // If username is already in use
            else if (dbh.containsUsername(username.getText().toString())) Toast.makeText(getApplicationContext(), "Username already in use", Toast.LENGTH_SHORT).show();

            // If all fields have had a value entered, passwords are the same, and username is not already in use
            else {

                // If user is creating a customer account
                if (customer.isChecked()){

                    // If adding data is successful
                    if (dbh.addCustomerData(name.getText().toString(), city.getText().toString(),
                            phone.getText().toString(), email.getText().toString(), username.getText().toString(),
                            password.getText().toString().hashCode())){
                        Toast.makeText(getApplicationContext(), "Customer account created", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    // If adding data is unsuccessful
                    else {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }

                // If user is creating a restaurant account
                else {

                    // If adding data is successful
                    if (dbh.addRestaurantData(name.getText().toString(), city.getText().toString(),
                            phone.getText().toString(), email.getText().toString(), username.getText().toString(),
                            password.getText().toString().hashCode(), restaurantDescription.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Restaurant account created", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    // If adding data is unsuccessful
                    else {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}