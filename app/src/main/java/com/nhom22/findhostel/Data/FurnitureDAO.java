package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Furniture;

import java.util.ArrayList;
import java.util.List;

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

    public long addAFurniture(Furniture furniture) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", furniture.getName());
        values.put("is_active", furniture.getIsActive());

        long id = db.insert("furniture", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }

        return id; // Cập nhật ID cho đối tượng Furniture
    }

    @SuppressLint("range")
    public List<Furniture> getAllFurniture() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        Cursor cursor = db.query("furniture", columns, null, null, null, null, null);

        List<Furniture> furnitureList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            Furniture furniture = new Furniture(id, name, isActive);
            furnitureList.add(furniture);
        }

        cursor.close();

        return furnitureList;
    }

    public void deleteAllFurniture() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("furniture", null, null);

        db.close();
    }

    public void resetFurnitureAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='furniture'";
        db.execSQL(query);

        db.close();
    }

    public int getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "furniture";
        Cursor cursor = db.rawQuery(query, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return id;
    }
}
