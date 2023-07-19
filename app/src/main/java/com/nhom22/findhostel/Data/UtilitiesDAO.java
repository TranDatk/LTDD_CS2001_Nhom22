package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Utilities;

import java.util.ArrayList;
import java.util.List;

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

    @SuppressLint("range")
    public List<Utilities> getAllUtilities() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active"
        };

        Cursor cursor = db.query("utilities", columns, null, null, null, null, null);

        List<Utilities> utilitiesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            Utilities utilities = new Utilities(id, name, isActive);
            utilitiesList.add(utilities);
        }

        cursor.close();

        return utilitiesList;
    }

    public long addAUtilities(Utilities utilities) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", utilities.getName());
        values.put("is_active", utilities.getIsActive());

        long id = db.insert("utilities", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }

        return id; // Cập nhật ID cho đối tượng Furniture
    }

    public void deleteAllUtilities() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("utilities", null, null);

        db.close();
    }

    public void resetUtilitiesAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='utilities'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "utilities";
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return id;
    }
}
