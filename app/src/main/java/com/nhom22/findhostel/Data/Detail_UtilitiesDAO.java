package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_UtilitiesDAO {
    private DatabaseHelper dbHelper;

    private UtilitiesDAO utilitiesDAO = new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());
    private PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());

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
            PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin Furniture từ cơ sở dữ liệu dựa trên furnitureId
            UtilitiesDAO utilitiesDAO = new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());
            Utilities utilities = utilitiesDAO.getUtilitiesById(utilities_id);

            // Tạo đối tượng Detail_Furniture từ các cột trong Cursor và các đối tượng Posts, Furniture
            detail_utilities = new Detail_Utilities(detailUtilitiesId, quantity, unit, post, utilities);
        }

        cursor.close();
        db.close();

        return detail_utilities;
    }

    public List<Utilities> getListUtilitiesByPostsId(int postsId) throws ParseException {
        List<Utilities> listUtilities = new ArrayList<>();
        UtilitiesDAO utilitiesDAO = new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());

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

            Utilities utilities = utilitiesDAO.getUtilitiesById(utilitiesId);

            listUtilities.add(utilities);
        }

        cursor.close();
        db.close();

        return listUtilities;
    }

    public List<Utilities> getAllDetailUtilities() {
        List<Utilities> utilitiesList = new ArrayList<>();
        UtilitiesDAO utilitiesDAO = new UtilitiesDAO(YourApplication.getInstance().getApplicationContext());

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "price",
                "unit",
                "posts_id",
                "utilities_id"
        };


        Cursor cursor = db.query("detail_utilities", columns, null, null,
                null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int utilitiesId = cursor.getInt(cursor.getColumnIndex("utilities_id"));

            Utilities utilities = utilitiesDAO.getUtilitiesById(utilitiesId);

            utilitiesList.add(utilities);
        }

        cursor.close();
        db.close();

        return utilitiesList;
    }

    public long addADetailUtilities(Detail_Utilities detail_utilities) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("price", detail_utilities.getPrice());
        values.put("unit", detail_utilities.getUnit());
        values.put("posts_id",detail_utilities.getPosts().getId());
        values.put("utilities_id",detail_utilities.getUtilities().getId());

        long id = db.insert("detail_utilities", null, values);
        if(id > 1){
            id = getIdOfLastInsertedRow();
        }
        db.close();

        return id;
    }

    public void deleteAllDetailUtilities() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("detail_utilities", null, null);

        db.close();
    }

    public void resetDetailUtilitiesAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='detail_utilities'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "detail_utilities";
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(0);
        }

        cursor.close();
        db.close();

        return id;
    }

    @SuppressLint("Range")
    public List<Detail_Utilities> getListDetailUtilitiesByPostId(int postId) throws ParseException {
        List<Detail_Utilities> listDetailUtilities = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "price",
                "unit",
                "posts_id",
                "utilities_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postId)};

        Cursor cursor = db.query("detail_utilities", columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String unit = cursor.getString(cursor.getColumnIndex("unit"));
            int utilitiesId = cursor.getInt(cursor.getColumnIndex("utilities_id"));

            // Tạo đối tượng Utilities
            Utilities utilities = utilitiesDAO.getUtilitiesById(utilitiesId);

            // Tao doi tuong posts
            Posts posts = postsDAO.getPostById(postId);

            // Tạo đối tượng Detail_Utilities và thêm vào danh sách
            Detail_Utilities detailUtilities = new Detail_Utilities(id, price, unit, posts, utilities);
            listDetailUtilities.add(detailUtilities);
        }

        cursor.close();
        return listDetailUtilities;
    }
}
