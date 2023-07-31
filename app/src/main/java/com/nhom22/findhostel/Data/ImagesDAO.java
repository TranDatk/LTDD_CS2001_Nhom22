package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Images;

import java.util.ArrayList;
import java.util.List;

public class ImagesDAO {
    private DatabaseHelper dbHelper;

    public ImagesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Images getImagesById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "image",
                "is_active"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("images", columns, selection, selectionArgs, null, null, null);

        Images images = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

            // Tạo đối tượng image từ các cột trong Cursor
            images = new Images(imageId, name, image,isActive);
        }

        cursor.close();
        db.close();

        return images;
    }

    public List<Images> getAllImagesByName(String name) {
        List<Images> imagesList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "image",
                "is_active"
        };

        String selection = "name = ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.query("images", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String imageName = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));

                // Create Images object from the columns in the Cursor
                Images images = new Images(imageId, imageName, image, isActive);
                imagesList.add(images);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return imagesList;
    }

    public long addAImages(Images image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", image.getName());
        values.put("image", image.getImage());
        values.put("is_active", image.getIsActive());

        long result = db.insert("images", null, values);
        if(result > 0){
            result = getIdOfLastInsertedRow();
        }
        db.close();
        return  result;
    }

    public int updateActive(long imageId, int isActive) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_active", isActive);
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(imageId)};
        int rowsUpdated = db.update("images", values, whereClause, whereArgs);
        db.close();
        return rowsUpdated;
    }

    public void deleteAllImages() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("images", null, null);

        db.close();
    }


    public void resetImagesAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='images'";
        db.execSQL(query);

        db.close();
    }

    public void insertImages(int idImages, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", image);
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(idImages)};
        db.update("images", values, whereClause, whereArgs);
        db.close();
    }

    public int getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "images";
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
