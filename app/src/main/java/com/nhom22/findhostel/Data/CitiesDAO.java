package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Cities;

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
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String isActive = cursor.getString(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng Cities từ các cột trong Cursor
            city = new Cities(cityId, name, isActive);
        }

        cursor.close();
        db.close();

        return city;
    }
}
