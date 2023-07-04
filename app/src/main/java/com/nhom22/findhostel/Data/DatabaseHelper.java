package com.nhom22.findhostel.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "findhostel.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user_account (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "phone TEXT," +
                "digital_money REAL," +
                "email TEXT," +
                "role_user INTEGER," +
                "avatar BLOB," +
                "is_active INTEGER," +
                "address_id INTEGER," +
                "FOREIGN KEY (address_id) REFERENCES address(id)" +
                ")");

        // Create the address table
        db.execSQL("CREATE TABLE address (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "house_number TEXT," +
                "is_active INTEGER," +
                "cities_id INTEGER," +
                "districts_id INTEGER," +
                "sub_districts_id INTEGER," +
                "streets_id INTEGER," +
                "FOREIGN KEY (cities_id) REFERENCES cities(id)," +
                "FOREIGN KEY (districts_id) REFERENCES districts(id)," +
                "FOREIGN KEY (sub_districts_id) REFERENCES sub_districts(id)," +
                "FOREIGN KEY (streets_id) REFERENCES streets(id)" +
                ")");

        // Create the sub_districts table
        db.execSQL("CREATE TABLE sub_districts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER," +
                "districs_id INTEGER," +
                "FOREIGN KEY (districs_id) REFERENCES districs(id)" +
                ")");

        // Create the streets table
        db.execSQL("CREATE TABLE streets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER," +
                "sub_districs_id INTEGER," +
                "FOREIGN KEY (sub_districs_id) REFERENCES sub_districs(id)" +
                ")");

        // Create the cities table
        db.execSQL("CREATE TABLE districts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER," +
                "cities_id INTEGER," +
                "FOREIGN KEY (cities_id) REFERENCES cities(id)" +
                ")");

        // Create the districs table
        db.execSQL("CREATE TABLE cities (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER" +
                ")");

        // Create the streets table
        db.execSQL("CREATE TABLE type (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER" +
                ")");

        // Create the posts table
        db.execSQL("CREATE TABLE posts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "active_post INTEGER," +
                "time_from DATETIME," +
                "time_to DATETIME," +
                "post_name TEXT," +
                "price REAL," +
                "description TEXT," +
                "address_id INTEGER," +
                "owner_id INTEGER," +
                "type_id INTEGER," +
                "FOREIGN KEY (address_id) REFERENCES address(id)," +
                "FOREIGN KEY (owner_id) REFERENCES user_account(id)," +
                "FOREIGN KEY (type_id) REFERENCES type(id)" +
                ")");

        // Create the save_post table
        db.execSQL("CREATE TABLE save_post (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "post_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES user_account(id)," +
                "FOREIGN KEY (post_id) REFERENCES posts(id)" +
                ")");

        // Create the service table
        db.execSQL("CREATE TABLE service (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER" +
                ")");

        // Create the detail_service table
        db.execSQL("CREATE TABLE detail_service (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "price REAL," +
                "unit TEXT," +
                "posts_id INTEGER," +
                "service_id INTEGER," +
                "FOREIGN KEY (posts_id) REFERENCES posts(id)," +
                "FOREIGN KEY (service_id) REFERENCES service(id)" +
                ")");

        // Create the amenities table
        db.execSQL("CREATE TABLE amenities (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "is_active INTEGER" +
                ")");

        // Create the detail_amenities table
        db.execSQL("CREATE TABLE detail_amenities (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quantity INTEGER," +
                "posts_id INTEGER," +
                "amenities_id INTEGER," +
                "FOREIGN KEY (posts_id) REFERENCES posts(id)," +
                "FOREIGN KEY (amenities_id) REFERENCES amenities(id)" +
                ")");

        // Create the detail_image table
        db.execSQL("CREATE TABLE detail_image (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "images_id INTEGER," +
                "posts_id INTEGER," +
                "FOREIGN KEY (posts_id) REFERENCES posts(id)," +
                "FOREIGN KEY (images_id) REFERENCES images(id)" +
                ")");

        // Create the images table
        db.execSQL("CREATE TABLE images (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "image BLOB," +
                "is_active INTEGER" +
                ")");

        // Create the posts_extension table
        db.execSQL("CREATE TABLE posts_extension (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "text TEXT," +
                "image BLOB," +
                "created_date Text," +
                "user_id INTEGER," +
                "is_active INTEGER" +
                ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
