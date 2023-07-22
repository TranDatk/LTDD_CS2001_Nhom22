package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationDAO {
    private DatabaseHelper dbHelper;


    public NotificationDAO(Context context){dbHelper = new DatabaseHelper(context);}

    public long addNotification(Notification notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_posts", notification.getPosts().getId());
        values.put("id_user", notification.getUserAccount().getId());
        values.put("created_date", notification.getCreated_date().toString());
        values.put("description", notification.getDescription());


        long id = db.insert("notification", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Notification> getAllNotification() throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "id_posts",
                "id_user",
                "description",
                "created_date"
        };

        Cursor cursor = db.query("notification", columns, null, null, null, null, null);

        List<Notification> notifications = new ArrayList<>();

        while (cursor != null && cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int id_posts = cursor.getInt( cursor.getColumnIndex("id_posts"));
            int id_user = cursor.getInt(cursor.getColumnIndex("id_user"));
            String description = cursor.getString( cursor.getColumnIndex("description"));
            String created_date = cursor.getString( cursor.getColumnIndex("created_date"));

            // Create SimpleDateFormat with the correct pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.getDefault());

            // Parse the date strings using the SimpleDateFormat
            Date createdDate;
            try {
                createdDate = dateFormat.parse(created_date);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing exception as needed
                continue; // Skip this iteration and proceed with the next iteration
            }

            PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());

            Posts posts = postsDAO.getPostById(id_posts);
            UserAccount userAccount = userAccountDAO.getUserAccountById(id_user);

            Notification notification = new Notification(id, posts, userAccount, description, createdDate);
            notifications.add(notification);
        }
        cursor.close();
        db.close();

        return notifications;
    }

    public void deleteAllNotification() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("notification", null, null);

        db.close();
    }

    public void resetNotificationAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='notification'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "notification";
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(0);
        }

        cursor.close();
        db.close();

        return id;
    }
}
