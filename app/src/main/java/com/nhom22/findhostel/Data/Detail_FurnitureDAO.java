package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Furniture> getListFurnitureByPostsId(int postsId) throws ParseException {
        List<Furniture> listFurniture = new ArrayList<>();
        FurnitureDAO furnitureDAO= new FurnitureDAO(YourApplication.getInstance().getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "quantity",
                "posts_id",
                "furniture_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postsId)};

        Cursor cursor = db.query("detail_furniture", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int furnitureId = cursor.getInt(cursor.getColumnIndex("furniture_id"));

            Furniture furniture = furnitureDAO.getFurnitureById(furnitureId);

            listFurniture.add(furniture);
        }

        cursor.close();
        db.close();

        return listFurniture;
    }

    public long addADetailFurniture(Detail_Furniture detailFurniture) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", detailFurniture.getQuantity());
        values.put("posts_id",detailFurniture.getPosts().getId());
        values.put("furniture_id",detailFurniture.getFurniture().getId());

        long id = db.insert("detail_furniture", null, values);
        if(id > 1){
            id = getIdOfLastInsertedRow();
        }
        db.close();

        return id;
    }

    public void deleteAllDetailFurniture() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("detail_furniture", null, null);

        db.close();
    }

    public void resetDetailFurnitureAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='detail_furniture'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "detail_furniture";
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(0);
        }

        cursor.close();
        db.close();

        return id;
    }

    public List<Detail_Furniture> getAllDetailFurniture() throws ParseException {
        List<Detail_Furniture> detail_furnitureList = new ArrayList<>();
        FurnitureDAO furnitureDAO= new FurnitureDAO(YourApplication.getInstance().getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "quantity",
                "posts_id",
                "furniture_id"
        };




        Cursor cursor = db.query("detail_furniture", columns, null, null, null, null, null);

         while (cursor.moveToNext()) {
            @SuppressLint("Range") int detailFurnitureId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int furnitureId = cursor.getInt(cursor.getColumnIndex("furniture_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            Furniture furniture = furnitureDAO.getFurnitureById(furnitureId);

            // Tạo đối tượng Detail_Furniture từ các cột trong Cursor và các đối tượng Posts, Furniture
            Detail_Furniture detailFurniture = new Detail_Furniture(detailFurnitureId, quantity, post, furniture);
            detail_furnitureList.add(detailFurniture);
        }

        cursor.close();
        db.close();

        return detail_furnitureList;
    }

    public List<Detail_Furniture> getListDetailFurnitureByPostId(int postsId) throws ParseException {
        List<Detail_Furniture> detail_furnitureList = new ArrayList<>();
        FurnitureDAO furnitureDAO = new FurnitureDAO(YourApplication.getInstance().getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "quantity",
                "posts_id",
                "furniture_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postsId)};

        Cursor cursor = db.query("detail_furniture", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int detailFurnitureId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int furnitureId = cursor.getInt(cursor.getColumnIndex("furniture_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            Furniture furniture = furnitureDAO.getFurnitureById(furnitureId);

            // Tạo đối tượng Detail_Furniture từ các cột trong Cursor và các đối tượng Posts, Furniture
            Detail_Furniture detailFurniture = new Detail_Furniture(detailFurnitureId, quantity, post, furniture);
            detail_furnitureList.add(detailFurniture);
        }

        cursor.close();
        db.close();

        return detail_furnitureList;
    }


}
