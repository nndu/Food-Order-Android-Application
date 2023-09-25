package com.example.mobileappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "Information.db";
    final static int DATABASE_VERSION = 9;
    final static String TABLE1_NAME ="Customer_table";
    final static String T1COL_1 = "Id";
    final static String T1COL_2 = "Name";
    final static String T1COL_3 = "City";
    final static String T1COL_4 = "Phone";
    final static String T1COL_5 = "Email";
    final static String T1COL_6 = "Username";
    final static String T1COL_7 = "Password";
    final static String TABLE2_NAME = "Restaurant_table";
    final static String T2COL_1 = "Id";
    final static String T2COL_2 = "Name";
    final static String T2COL_3 = "City";
    final static String T2COL_4 = "Phone";
    final static String T2COL_5 = "Email";
    final static String T2COL_6 = "Username";
    final static String T2COL_7 = "Password";
    final static String T2COL_8 = "Description";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE1_NAME + "(" + T1COL_1 + " INTEGER PRIMARY KEY, " +
                T1COL_2 + " TEXT, " + T1COL_3 + " TEXT, " + T1COL_4 + " TEXT, " + T1COL_5 + " TEXT, " + T1COL_6 + " TEXT, " + T1COL_7 + " INTEGER)";
        String query2 = "CREATE TABLE " + TABLE2_NAME + "(" + T2COL_1 + " INTEGER PRIMARY KEY, " +
                T2COL_2 + " TEXT, " + T2COL_3 + " TEXT, " + T2COL_4 + " TEXT, " + T2COL_5 + " TEXT, " + T2COL_6 + " TEXT, " + T2COL_7 + " INTEGER, " + T2COL_8 + " TEXT)";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    /**
     *
     * @param username The username to be searched in the database
     * @return true if username found, false if not found
     */
    public boolean containsUsername(String username){
        boolean contains = this.getIdByUsername(username, TABLE1_NAME) > 0;
        if (!contains) contains = this.getIdByUsername(username, TABLE2_NAME) > 0;
        return contains;
    }

    /**
     *
     * @param username Username used to searched in the database
     * @param tableName  The table to be searched
     * @return The id of the customer or restaurant with this username, or -1 if username not found
     */
    private int getIdByUsername(String username , String tableName){
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+ "Id" + ", " + "Username" + " FROM " + tableName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                if (cursor.getString(1).equals(username)) {
                    id = Integer.parseInt(cursor.getString(0));
                }
            }
        }
        cursor.close();
        return id;
    }

    /**
     *
     * Adds an entry to the Customer table, {@link #containsUsername(String)} should be ran
     * prior to check for username conflicts
     * @param name The name of the customer
     * @param city The city of the customer
     * @param phone The phone number of the customer
     * @param email The email of the customer
     * @param username The username of the customer
     * @param password The Hashed password of the customer
     * @return true if successful, false if unsuccessful
     */
    public boolean addCustomerData(String name, String city, String phone, String email, String username, int password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL_2, name);
        values.put(T1COL_3, city);
        values.put(T1COL_4, phone);
        values.put(T1COL_5, email);
        values.put(T1COL_6, username);
        values.put(T1COL_7, password);

        long l = db.insert(TABLE1_NAME, null, values);
        return l > 0;
    }

    /**
     *
     * Adds an entry to the Restaurant table, {@link #containsUsername(String)} should be ran
     * prior to check for username conflicts
     * @param name The name of the restaurant
     * @param city The city of the restaurant
     * @param phone The phone number of the restaurant
     * @param email The email of the restaurant
     * @param username The username of the restaurant
     * @param password The Hashed password of the restaurant
     * @param description The description of the restaurant
     * @return true if successful, false if unsuccessful
     */
    public boolean addRestaurantData(String name, String city, String phone, String email, String username, int password, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T2COL_2, name);
        values.put(T2COL_3, city);
        values.put(T2COL_4, phone);
        values.put(T2COL_5, email);
        values.put(T2COL_6, username);
        values.put(T2COL_7, password);
        values.put(T2COL_8, description);

        long l = db.insert(TABLE2_NAME, null, values);
        return l > 0;
    }

    /**
     *
     * @param id The id used to search the table
     * @param tableName The Table to be searched
     * @return A Cursor pointing to the result set for the id
     */
    public Cursor getDataById(int id, String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE Id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor;
    }

    /**
     *
     * Attempts to login a  user
     * @param username The username used for the login
     * @param password The password being checked against what is on record, should be the hashed version of the string password
     * @param tableName Table being queried
     * @return The id of the user if successful, -1 if username is not found, -2 if password is incorrect
     */
    public int logIn(String username, int password, String tableName){
        int id = getIdByUsername(username, tableName);
        if (id > 0){
            if (verifyPassword(id, tableName, password)){
                return id;
            }
            else {
                return -2;
            }
        }
        else {
            return -1;
        }
    }

    /**
     *
     * @param id The id of the user, password is being checked against
     * @param tableName The table where id is going to be checked in
     * @param password The password being verified
     * @return true if password is correct, false if incorrect
     */
    public boolean verifyPassword(int id, String tableName, int password){
        Cursor cursor = getDataById(id, tableName);
        if (cursor.getInt(6) == password){
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    /**
     *
     * @param tableName Table to be updated
     * @param colName Column being updated
     * @param value Value being entered
     * @param id ID of entry being updated
     * @return true if successful, false if unsuccessful
     */
    public boolean updateColumn(String tableName, String colName, String value, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colName, value);
        int rows = db.update(tableName, contentValues, "Id=?", new String[]{Integer.toString(id)});
        return rows > 0;
    }

    /**
     *
     * @param tableName Table to be updated
     * @param colName Column being updated
     * @param value Value being entered
     * @param id ID of entry being updated
     * @return true if successful, false if unsuccessful
     */
    public boolean updateColumn(String tableName, String colName, int value, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colName, value);
        int rows = db.update(tableName, contentValues, "Id=?", new String[]{Integer.toString(id)});
        return rows > 0;
    }

    /**
     *
     * @param tableName Table to be queried
     * @param id ID of entry being deleted
     * @return true if successful, false if unsuccessful
     */
    public boolean deleteRow(String tableName, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(tableName, "id=?", new String[]{Integer.toString(id)});
        return rows > 0;
    }
}
