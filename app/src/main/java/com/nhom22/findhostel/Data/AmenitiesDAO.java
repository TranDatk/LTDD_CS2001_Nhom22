package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Amenities;
import com.nhom22.findhostel.Model.Images;

public class AmenitiesDAO {
    private DatabaseHelper dbHelper;

    public AmenitiesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Amenities getAmenitiesById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("amenities", columns, selection, selectionArgs, null, null, null);

        Amenities amenities = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng amenities từ các cột trong Cursor
            amenities = new Amenities(imageId, name,isActive);
        }

        cursor.close();
        db.close();

        return amenities;
    }
}
