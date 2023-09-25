package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DatabaseHelper(this);
        Button logIn = findViewById(R.id.btnLogIn);
        Button createAccount = findViewById(R.id.btnCreateAccount);
        EditText userName = findViewById(R.id.editMainUsername);
        EditText password = findViewById(R.id.editMainPassword);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        logIn.setOnClickListener(view -> {
            // If username and password are empty
            if (userName.getText().toString().isEmpty() && password.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a username and password", Toast.LENGTH_SHORT).show();

            // If username is empty
            else if (userName.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();

            // If password is empty
            else if (password.getText().toString().isEmpty()) Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show();

            // If both username and password have had something entered
            else {

                // If username is found
                if (dbh.containsUsername(userName.getText().toString())) {
                    int id = dbh.logIn(userName.getText().toString(), password.getText().toString().hashCode(), DatabaseHelper.TABLE1_NAME);

                    // If customer login is successful
                    if (id > 0) {
                        Cursor cursor = dbh.getDataById(id, DatabaseHelper.TABLE1_NAME);

                        editor.clear();
                        editor.putInt("id", cursor.getInt(0));
                        editor.putString("name", cursor.getString(1));
                        editor.putString("city", cursor.getString(2));
                        editor.putString("phone", cursor.getString(3));
                        editor.putString("email", cursor.getString(4));
                        editor.putString("username", cursor.getString(5));
                        editor.putInt("password", cursor.getInt(6));
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, CustomerMain.class));
                    }

                    // If customer login is not successful
                    else {
                        id = dbh.logIn(userName.getText().toString(), password.getText().toString().hashCode(), DatabaseHelper.TABLE2_NAME);

                        // If restaurant login is successful
                        if (id > 0){
                            Cursor cursor = dbh.getDataById(id, DatabaseHelper.TABLE2_NAME);
                            editor.clear();
                            editor.putInt("id", cursor.getInt(0));
                            editor.putString("name", cursor.getString(1));
                            editor.putString("city", cursor.getString(2));
                            editor.putString("phone", cursor.getString(3));
                            editor.putString("email", cursor.getString(4));
                            editor.putString("username", cursor.getString(5));
                            editor.putInt("password", cursor.getInt(6));
                            editor.putString("description", cursor.getString(7));
                            editor.apply();
                            startActivity(new Intent(MainActivity.this, RestaurantMain.class));
                        }

                        // If restaurant login is not successful
                        else {
                            Toast.makeText(getApplicationContext(), "Password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                // if username is not found
                else {
                    Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccount.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AccountCreation.class)));
    }
}