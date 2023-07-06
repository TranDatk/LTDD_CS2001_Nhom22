package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Images;

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
}
