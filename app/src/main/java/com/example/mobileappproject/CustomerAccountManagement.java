package com.example.mobileappproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerAccountManagement extends AppCompatActivity {
    DatabaseHelper dbh;
    int clientId;
    String name;
    String city;
    String phone;
    String email;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_account_management);

        dbh = new DatabaseHelper(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.clientId = sharedPreferences.getInt("id", 0);
        this.name = sharedPreferences.getString("name", "");
        this.city = sharedPreferences.getString("city", "");
        this.phone = sharedPreferences.getString("phone", "");
        this.email = sharedPreferences.getString("email", "");
        this.username = sharedPreferences.getString("username", "");

        Button updateCity = findViewById(R.id.btnUpdateCustomerCity);
        Button updatePhone = findViewById(R.id.btnUpdateCustomerPhone);
        Button updateEmail = findViewById(R.id.btnUpdateCustomerEmail);
        Button updateUsername = findViewById(R.id.btnUpdateCustomerUsername);
        Button updatePassword = findViewById(R.id.btnUpdateCustomerPassword);
        Button deleteAccount = findViewById(R.id.btnDeleteCustomerAccount);
        TextView name = findViewById(R.id.txtCustomerAccountManagmentName);
        TextView city = findViewById(R.id.txtCustomerAccountManagmentCity);
        TextView phone = findViewById(R.id.txtCustomerAccountManagmentPhone);
        TextView email = findViewById(R.id.txtCustomerAccountManagmentEmail);
        TextView username = findViewById(R.id.txtCustomerAccountManagmentUsername);
        EditText newCity = findViewById(R.id.editCustomerAccountManagementCity);
        EditText newPhone = findViewById(R.id.editCustomerAccountManagmentPhone);
        EditText newEmail = findViewById(R.id.editCustomerAccountManagmentEmail);
        EditText newUsername = findViewById(R.id.editCustomerAccountManagementUsername);
        EditText oldPassword = findViewById(R.id.editCustomerAccountManagementOldPassword);
        EditText newPassword = findViewById(R.id.editCustomerAccountManagementNewPassword);
        EditText confirmNewPassword = findViewById(R.id.editCustomerAccountManagementConfirmNewPassword);
        EditText deleteCustomerAccount = findViewById(R.id.editDeleteCustomerAccount);

        String text = "Name: " + this.name;
        name.setText(text);

        text = "City: " + this.city;
        city.setText(text);

        text = "Phone: " + this.phone;
        phone.setText(text);

        text = "Email: " + this.email;
        email.setText(text);

        text = "Username: " + this.username;
        username.setText(text);

        updateCity.setOnClickListener(view ->{
            String s = newCity.getText().toString();
            if(!s.isEmpty()){
                if(dbh.updateColumn(DatabaseHelper.TABLE1_NAME, DatabaseHelper.T1COL_3, s, this.clientId)){
                    this.city = s;
                    sharedPreferences.edit().putString("city", s).apply();
                    s = "City: " + this.city;
                    city.setText(s);
                }
                else Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "City cannot be empty", Toast.LENGTH_SHORT).show();
        });

        updatePhone.setOnClickListener(view -> {
            String s = newPhone.getText().toString();
            if(!s.isEmpty()){
                if(dbh.updateColumn(DatabaseHelper.TABLE1_NAME, DatabaseHelper.T1COL_4, s, this.clientId)){
                    this.phone = s;
                    sharedPreferences.edit().putString("phone", s).apply();
                    s = "Phone: " + this.phone;
                    phone.setText(s);
                }
                else Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Phone cannot be empty", Toast.LENGTH_SHORT).show();
        });

        updateEmail.setOnClickListener(view -> {
            String s = newEmail.getText().toString();
            if(!s.isEmpty()){
                if(dbh.updateColumn(DatabaseHelper.TABLE1_NAME, DatabaseHelper.T1COL_5, s, this.clientId)){
                    this.email = s;
                    sharedPreferences.edit().putString("email", s).apply();
                    s = "Email: " + this.email;
                    email.setText(s);
                }
                else Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
        });

        updateUsername.setOnClickListener(view -> {
            String s = newUsername.getText().toString();
            if(!s.isEmpty()){
                if (!dbh.containsUsername(s)) {
                    if (dbh.updateColumn(DatabaseHelper.TABLE1_NAME, DatabaseHelper.T1COL_6, s, this.clientId)) {
                        this.username = s;
                        sharedPreferences.edit().putString("username", s).apply();
                        s = "Username: " + this.username;
                        username.setText(s);
                    } else
                        Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Username already in use", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
        });

        updatePassword.setOnClickListener(view -> {
            if (!oldPassword.getText().toString().isEmpty()){
                if (!newPassword.getText().toString().isEmpty()){
                    if (!confirmNewPassword.getText().toString().isEmpty()){
                        if (newPassword.getText().toString().equals(confirmNewPassword.getText().toString())){
                            if (dbh.verifyPassword(this.clientId, DatabaseHelper.TABLE1_NAME, oldPassword.getText().toString().hashCode())){
                                int i  = newPassword.getText().toString().hashCode();
                                if(dbh.updateColumn(DatabaseHelper.TABLE1_NAME, DatabaseHelper.T1COL_7, i, this.clientId)){
                                    sharedPreferences.edit().putInt("password", i).apply();
                                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                    oldPassword.setText("");
                                    newPassword.setText("");
                                    confirmNewPassword.setText("");
                                }
                                else Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(getApplicationContext(), "New password and confirm new password are not the same", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Confirm new password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "New password cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Old password cannot be empty", Toast.LENGTH_SHORT).show();
        });

        deleteAccount.setOnClickListener(view -> {
            if (!deleteCustomerAccount.getText().toString().isEmpty()){
                if (dbh.verifyPassword(this.clientId, DatabaseHelper.TABLE1_NAME, deleteCustomerAccount.getText().toString().hashCode())){
                    if (dbh.deleteRow(DatabaseHelper.TABLE1_NAME, this.clientId)){
                        sharedPreferences.edit().clear().apply();
                        Intent intent = new Intent(CustomerAccountManagement.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
        });
    }
}