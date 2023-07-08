package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Save_PostDAO {
    private DatabaseHelper dbHelper;

    public Save_PostDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Save_Post getSavePostById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "posts_id",
                "user_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("save_post", columns, selection, selectionArgs, null, null, null);

        Save_Post savePost = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int savePostId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int userAccountId = cursor.getInt(cursor.getColumnIndex("user_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin UserAccount từ cơ sở dữ liệu dựa trên userAccountId
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            UserAccount userAccount = userAccountDAO.getUserAccountById(userAccountId);

            // Tạo đối tượng Save_Post từ các cột trong Cursor và các đối tượng Posts, UserAccount
            savePost = new Save_Post(savePostId, post, userAccount);
        }

        cursor.close();
        db.close();

        return savePost;
    }

    public Save_Post getSavePostByPostIdAndUserAccountId(int postId, int userAccountId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "posts_id",
                "user_id"
        };

        String selection = "post_id = ? AND user_id = ?";
        String[] selectionArgs = {String.valueOf(postId), String.valueOf(userAccountId)};

        Cursor cursor = db.query("save_post", columns, selection, selectionArgs, null, null, null);

        Save_Post savePost = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int savePostId = cursor.getInt(cursor.getColumnIndex("id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin UserAccount từ cơ sở dữ liệu dựa trên userAccountId
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            UserAccount userAccount = userAccountDAO.getUserAccountById(userAccountId);

            // Tạo đối tượng Save_Post từ các cột trong Cursor và các đối tượng Posts, UserAccount
            savePost = new Save_Post(savePostId, post, userAccount);
        }

        cursor.close();
        db.close();

        return savePost;
    }

    public long addASavePost(int postId, int userAccountId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("posts_id", postId);
        values.put("user_id", userAccountId);

        long id = db.insert("save_post", null, values);
        db.close();

        return id;
    }

    public long deleteASavePost(int savePostId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(savePostId)};

        long rowsAffected = db.delete("save_post", selection, selectionArgs);
        db.close();

        return rowsAffected;
    }

    public List<Save_Post> getListSavePostByUserAccountId(int userId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "posts_id",
                "user_id"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("save_post", columns, selection, selectionArgs, null, null, null);

        List<Save_Post> save_postList = new ArrayList<>();
        while (cursor.moveToNext())  {
            @SuppressLint("Range") int savePostId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));
            @SuppressLint("Range") int userAccountId = cursor.getInt(cursor.getColumnIndex("user_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            // Lấy thông tin UserAccount từ cơ sở dữ liệu dựa trên userAccountId
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            UserAccount userAccount = userAccountDAO.getUserAccountById(userAccountId);

            // Tạo đối tượng Save_Post từ các cột trong Cursor và các đối tượng Posts, UserAccount
            Save_Post savePost = new Save_Post(savePostId, post, userAccount);
            save_postList.add(savePost);
        }

        cursor.close();
        db.close();

        return save_postList;
    }

    public List<Posts> getListPostsByUserAccountId(int userId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "posts_id",
                "user_id"
        };

        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("save_post", columns, selection, selectionArgs, null, null, null);

        List<Posts> listPosts = new ArrayList<>();
        while (cursor.moveToNext())  {
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("posts_id"));

            // Lấy thông tin Posts từ cơ sở dữ liệu dựa trên postId
            PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
            Posts post = postsDAO.getPostById(postId);

            listPosts.add(post);
        }

        cursor.close();
        db.close();

        return listPosts;
    }
}
