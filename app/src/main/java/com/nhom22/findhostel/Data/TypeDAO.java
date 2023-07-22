package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Type;

import java.util.ArrayList;
import java.util.List;

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
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int typeId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng Type từ các cột trong Cursor
            type = new Type(typeId, name, isActive);
        }

        cursor.close();
        db.close();

        return type;
    }

    public List<Type> getAllType() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        Cursor cursor = db.query("type", columns, null, null, null, null, null);

        List<Type> typeList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int typeId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

                // Create a Type object from the columns in the Cursor
                Type type = new Type(typeId, name, isActive);

                typeList.add(type);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return typeList;
    }
    public long addType(Type type) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", type.getName());
        values.put("is_active", type.getIsActive());

        long id = db.insert("type", null, values);
        db.close();
        return id;
    }

    public void deleteAllType() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("type", null, null);

        db.close();
    }

    public void resetTypeAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='type'";
        db.execSQL(query);

        db.close();
    }
}
