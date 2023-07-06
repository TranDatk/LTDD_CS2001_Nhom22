package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Furniture;

public class FurnitureDAO {
    private DatabaseHelper dbHelper;

    public FurnitureDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Furniture getFurnitureById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("furniture", columns, selection, selectionArgs, null, null, null);

        Furniture furniture = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int furnitureId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng furniture từ các cột trong Cursor
            furniture = new Furniture(furnitureId, name,isActive);
        }

        cursor.close();
        db.close();

        return furniture;
    }
}
