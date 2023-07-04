package com.nhom22.findhostel.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.nhom22.findhostel.Model.PostDecor;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        db.close();

        return id;
    }


    private void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
