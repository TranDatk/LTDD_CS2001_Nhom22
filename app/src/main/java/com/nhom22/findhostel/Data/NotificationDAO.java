package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationDAO {
    private DatabaseHelper dbHelper;


    public NotificationDAO(Context context){dbHelper = new DatabaseHelper(context);}

    public long addNotification(Notification notification) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_posts", notification.getPostsId());
        values.put("id_user", notification.getUserAccountId());
        values.put("created_date", notification.getCreated_date().toString());


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
                "created_date"
        };

        Cursor cursor = db.query("notification", columns, null, null, null, null, null);

        List<Notification> notifications = new ArrayList<>();

        while (cursor != null && cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int id_posts = cursor.getInt( cursor.getColumnIndex("id_posts"));
            int id_user = cursor.getInt(cursor.getColumnIndex("id_user"));
            String created_date = cursor.getString( cursor.getColumnIndex("created_date"));

            // Create SimpleDateFormat with the correct pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));

            // Parse the date strings using the SimpleDateFormat
            Date createdDate;
            try {
                createdDate = dateFormat.parse(created_date);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing exception as needed
                continue; // Skip this iteration and proceed with the next iteration
            }


            Notification notification = new Notification(id, id_posts, id_user, createdDate);
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

    @SuppressLint("Range")
    public Notification getANotificationByPostsIdAndUserId(int postsId, int userId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "id_posts",
                "id_user",
                "created_date"
        };

        String selection = "id_posts = ? AND id_user = ?";
        String[] selectionArgs = {String.valueOf(postsId), String.valueOf(userId)};

        Cursor cursor = db.query("notification", columns, selection, selectionArgs, null, null, null);

        Notification notification = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String created_date = cursor.getString( cursor.getColumnIndex("created_date"));

            // Create SimpleDateFormat with the correct pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));

            // Parse the date strings using the SimpleDateFormat
            Date createdDate = null;
            try {
                createdDate = dateFormat.parse(created_date);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing exception as needed
            }

            notification = new Notification(id, postsId, userId, createdDate);
        }

        cursor.close();
        return notification;
    }

    @SuppressLint("Range")
    public List<Notification> getNotificationOverThirtyDay() throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "id_posts",
                "id_user",
                "created_date"
        };

        // Lấy ngày hiện tại và tính ngày trước 30 ngày
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date thirtyDaysAgo = calendar.getTime();

        String selection = "created_date < ?";
        String[] selectionArgs = {formatDate(thirtyDaysAgo)};

        Cursor cursor = db.query("notification", columns, selection, selectionArgs, null, null, null);

        List<Notification> notificationList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int postsId = cursor.getInt(cursor.getColumnIndex("id_posts"));
            int userId = cursor.getInt(cursor.getColumnIndex("id_user"));
            Date createdDate = parseDate(cursor.getString(cursor.getColumnIndex("created_date")));

            Notification notification = new Notification(id, postsId, userId, createdDate);
            notificationList.add(notification);
        }

        cursor.close();
        return notificationList;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
        return dateFormat.parse(dateString);
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
        return dateFormat.format(date);
    }
}
