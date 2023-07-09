package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Cities;

import java.util.ArrayList;
import java.util.List;

public class CitiesDAO {
    private DatabaseHelper dbHelper;

    public CitiesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Cities getCityById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("cities", columns, selection, selectionArgs, null, null, null);

        Cities city = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng CitiesFirebase từ các cột trong Cursor
            city = new Cities(cityId, name, isActive);
        }

        cursor.close();
        db.close();

        return city;
    }


    public List<Cities> getAllCities() {
        List<Cities> cityList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        Cursor cursor = null;
        try {
            cursor = db.query("cities", columns, null, null, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");

            while (cursor.moveToNext()) {
                int cityId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;

                // Create a CitiesFirebase object from the columns in the Cursor
                Cities city = new Cities(cityId, name, isActive);
                cityList.add(city);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception trace for debugging purposes
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return cityList;
    }

    public long addCities(Cities city) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", city.getName());
        values.put("is_active", city.getIsActive());

        long id = db.insert("cities", null, values);
        db.close();
        return id;
    }
}
