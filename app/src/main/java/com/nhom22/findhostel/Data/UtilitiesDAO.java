package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Utilities;

public class UtilitiesDAO {
    private DatabaseHelper dbHelper;

    public UtilitiesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Utilities getUtilitiesById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("utilities", columns, selection, selectionArgs, null, null, null);

        Utilities utilities = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int utilitiesId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng furniture từ các cột trong Cursor
            utilities = new Utilities(utilitiesId, name, isActive);
        }

        cursor.close();
        db.close();

        return utilities;
    }
}
