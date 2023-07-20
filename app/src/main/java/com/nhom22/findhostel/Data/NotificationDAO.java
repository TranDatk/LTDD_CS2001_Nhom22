package com.nhom22.findhostel.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.Model.Posts;

public class NotificationDAO {
    private DatabaseHelper dbHelper;

    public NotificationDAO(Context context){dbHelper = new DatabaseHelper(context);}

    public long addNotification(Notification notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("posts_id", notification.getPosts().getId());
        values.put("created_date", notification.getCreated_date().toString());
        values.put("description", notification.getDescription());


        long id = db.insert("notification", null, values);
        db.close();
        return id;
    }
}
