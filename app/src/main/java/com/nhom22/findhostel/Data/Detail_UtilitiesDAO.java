package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_UtilitiesDAO {
    private DatabaseHelper dbHelper;

    public Detail_UtilitiesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Detail_Utilities getADetailUtilitiesById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "price",
                "unit",
                "posts_id",
                "utilities_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("detail_utilities", columns, selection, selectionArgs, null, null, null);

        Detail_Utilities detail_utilities = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int detailUtilitiesId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String unit = cursor.getString(cursor.getColumnIndex("unit"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int utilities_id = cursor.getInt(cursor.getColumnIndex("utilities_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin Furniture từ cơ sở dữ liệu dựa trên furnitureId
            UtilitiesDAO utilitiesDAO= new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());
            Utilities utilities = utilitiesDAO.getUtilitiesById(utilities_id);

            // Tạo đối tượng Detail_Furniture từ các cột trong Cursor và các đối tượng Posts, Furniture
            detail_utilities = new Detail_Utilities(detailUtilitiesId, quantity, unit, post,utilities);
        }

        cursor.close();
        db.close();

        return detail_utilities;
    }

    public List<Utilities> getListUtilitiesByPostsId(int postsId) throws ParseException {
        List<Utilities> listUtilities = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "price",
                "unit",
                "posts_id",
                "utilities_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postsId)};

        Cursor cursor = db.query("detail_utilities", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int utilitiesId = cursor.getInt(cursor.getColumnIndex("utilities_id"));

            UtilitiesDAO utilitiesDAO = new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());
            Utilities utilities = utilitiesDAO.getUtilitiesById(utilitiesId);

            listUtilities.add(utilities);
        }

        cursor.close();
        db.close();

        return listUtilities;
    }
}
