package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;

public class Detail_FurnitureDAO {
    private DatabaseHelper dbHelper;

    public Detail_FurnitureDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Detail_Furniture getADetailFurnitureById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "quantity",
                "posts_id",
                "furniture_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("detail_furniture", columns, selection, selectionArgs, null, null, null);

        Detail_Furniture detailFurniture = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int detailFurnitureId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int furnitureId = cursor.getInt(cursor.getColumnIndex("furniture_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin Furniture từ cơ sở dữ liệu dựa trên furnitureId
            FurnitureDAO furnitureDAO= new FurnitureDAO(YourApplication.getInstance().getApplicationContext());
            Furniture furniture = furnitureDAO.getFurnitureById(furnitureId);

            // Tạo đối tượng Detail_Furniture từ các cột trong Cursor và các đối tượng Posts, Furniture
            detailFurniture = new Detail_Furniture(detailFurnitureId, quantity, post, furniture);
        }

        cursor.close();
        db.close();

        return detailFurniture;
    }
}
