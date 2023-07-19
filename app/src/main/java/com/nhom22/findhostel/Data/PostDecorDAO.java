package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
}
