package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Detail_ImageDAO {
    private DatabaseHelper dbHelper;

    public Detail_ImageDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Detail_Image getDetailImageById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "images_id",
                "posts_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("detail_image", columns, selection, selectionArgs, null, null, null);

        Detail_Image detail_image = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int detailImageId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int imagesId = cursor.getInt(cursor.getColumnIndex("images_id"));
            @SuppressLint("Range") int postsId = cursor.getInt(cursor.getColumnIndex("posts_id"));

            ImagesDAO imagesDAO= new ImagesDAO(YourApplication.getInstance().getApplicationContext());
            Images images = imagesDAO.getImagesById(imagesId);


            PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts posts = postsDAO.getPostById(postsId);

            detail_image = new Detail_Image(detailImageId, images, posts);
        }

        cursor.close();
        db.close();

        return detail_image;
    }

    public Detail_Image getDetailImageByImagesIdAndPostsId(int imagesId, int postsId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "images_id",
                "posts_id"
        };

        String selection = "images_id = ? AND posts_id = ?";
        String[] selectionArgs = {String.valueOf(imagesId), String.valueOf(postsId)};

        Cursor cursor = db.query("detail_image", columns, selection, selectionArgs, null, null, null);

        Detail_Image detail_image = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int detailImageId = cursor.getInt(cursor.getColumnIndex("id"));

            ImagesDAO imagesDAO= new ImagesDAO(YourApplication.getInstance().getApplicationContext());
            Images images = imagesDAO.getImagesById(imagesId);


            PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts posts = postsDAO.getPostById(postsId);

            detail_image = new Detail_Image(detailImageId, images, posts);
        }

        cursor.close();
        db.close();

        return detail_image;
    }

    public long addADetailImage(int imagesId, int postsId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("images_id", imagesId);
        values.put("posts_id", postsId);

        long rowsAffected = db.insert("detail_image", null, values);
        if(rowsAffected > 0){
            rowsAffected = getIdOfLastInsertedRow();
        }

        db.close();

        return rowsAffected;
    }

    public long deleteADetailImage(int detailImageId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(detailImageId)};

        long rowsAffected = db.delete("detail_image", selection, selectionArgs);
        db.close();

        return rowsAffected;
    }

    public List<Detail_Image> getListDetailImageByPostsId(int postsId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "images_id",
                "posts_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postsId)};

        Cursor cursor = db.query("detail_image", columns, selection, selectionArgs, null, null, null);

        List<Detail_Image> listDetailImage = new ArrayList<>();
        while (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int detailImageId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int imagesId = cursor.getInt(cursor.getColumnIndex("images_id"));

            ImagesDAO imagesDAO = new ImagesDAO(YourApplication.getInstance().getApplicationContext());
            Images images = imagesDAO.getImagesById(imagesId);

            PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts posts = postsDAO.getPostById(postsId);

            Detail_Image detail_image = new Detail_Image(detailImageId, images, posts);
            listDetailImage.add(detail_image);
        }

        cursor.close();
        db.close();

        return listDetailImage;
    }

    public List<Images> getListImageByPostsId(int postsId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "images_id",
                "posts_id"
        };

        String selection = "posts_id = ?";
        String[] selectionArgs = {String.valueOf(postsId)};

        Cursor cursor = db.query("detail_image", columns, selection, selectionArgs, null, null, null);

        List<Images> listImage = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            @SuppressLint("Range") int imagesId = cursor.getInt(cursor.getColumnIndex("images_id"));
            ImagesDAO imagesDAO = new ImagesDAO(YourApplication.getInstance().getApplicationContext());
            Images images = imagesDAO.getImagesById(imagesId);

            listImage.add(images);
        }

        cursor.close();
        db.close();

        return listImage;
    }

    public void deleteAllDetailImage() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("detail_image", null, null);

        db.close();
    }

    public void resetDetailImageAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='detail_image'";
        db.execSQL(query);

        db.close();
    }

    public int getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "detail_image";
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

