package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.PostDecor;

import java.util.ArrayList;
import java.util.List;

public class PostDecorDAO{
    private DatabaseHelper dbHelper;

    public PostDecorDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    public long addPostDecor(PostDecor postDecor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("text", postDecor.getText());
        values.put("image", postDecor.getImage());
        values.put("created_date", postDecor.getCreatedDate());
        values.put("user_id", postDecor.getUserId());
        values.put("is_active", postDecor.getIsActive());

        long id = db.insert("posts_extension", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<PostDecor> getAllPostDecor(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "text",
                "image",
                "created_date",
                "user_id",
                "is_active",
        };


        String orderBy = "created_date DESC"; // Sắp xếp theo created_date giảm dần
        String limit = "20"; // Hiển thị tối đa 20 bản ghi


        Cursor cursor = db.query("posts_extension", columns, null, null, null, null, orderBy, limit);

        List<PostDecor> postDecorList = new ArrayList<>();

        while (cursor != null && cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String text = cursor.getString( cursor.getColumnIndex("text"));
            byte[] image = cursor.getBlob( cursor.getColumnIndex("image"));
            String created_date = cursor.getString( cursor.getColumnIndex("created_date"));
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            int is_active = cursor.getInt(cursor.getColumnIndex("is_active"));


            // Tạo đối tượng Posts và thêm vào danh sách
            PostDecor postDecor = new PostDecor(id, text, image, created_date, user_id, is_active);
            postDecorList.add(postDecor);
        }

        cursor.close();

        return postDecorList;
    }

    public void deleteAllPostDecor() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("posts_extension", null, null);

        db.close();
    }

    public void resetPostDecorAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='posts_extension'";
        db.execSQL(query);

        db.close();
    }

    public void insertImagePostDecor(int idPostDecor, byte[] image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", image);
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(idPostDecor)};
        db.update("posts_extension", values, whereClause, whereArgs);
        db.close();
    }

    public int getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "posts_extension";
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
