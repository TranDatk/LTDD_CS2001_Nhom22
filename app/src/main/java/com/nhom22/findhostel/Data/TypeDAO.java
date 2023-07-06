package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Type;

public class TypeDAO {
    private DatabaseHelper dbHelper;

    public TypeDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Type getTypeById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("type", columns, selection, selectionArgs, null, null, null);

        Type type = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng Cities từ các cột trong Cursor
            type = new Type(cityId, name, isActive);
        }

        cursor.close();
        db.close();

        return type;
    }
}
