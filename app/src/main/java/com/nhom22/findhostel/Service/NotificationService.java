package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.NotificationDAO;
import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class NotificationService {
    public static final NotificationDAO NOTIFICATION_DAO;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference notificationRef = database.getReference("notification");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        NOTIFICATION_DAO = new NotificationDAO(appContext);
    }

    public long addNotification(Notification notification) throws ParseException {
        Context context = YourApplication.getInstance().getApplicationContext();
        if(notification != null
                && getANotificationByPostsIdAndUserId(notification.getPostsId(),notification.getUserAccountId()) == null){

            long result = NOTIFICATION_DAO.addNotification(notification);
            notification.setId(Integer.parseInt(String.valueOf(result)));

            notificationRef.child(String.valueOf(result)).setValue(notification);

            return result; // -1 Unsuccessful, >0 Successful
        }
        else if(notification == null){

            Toast.makeText(context, "Null notification", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }else{
            Toast.makeText(context, "Notification đã tồn tại", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public List<Notification> getAllNotification() throws ParseException{
        return NOTIFICATION_DAO.getAllNotification();
    }

    public void deleteAllNotification(){
        NOTIFICATION_DAO.deleteAllNotification();
    }

    public void resetNotificationAutoIncrement() {
        NOTIFICATION_DAO.resetNotificationAutoIncrement();
    }

    public Notification getANotificationByPostsIdAndUserId(int postsId, int userId) throws ParseException {
        if(postsId > 0 && userId > 0){
            return NOTIFICATION_DAO.getANotificationByPostsIdAndUserId(postsId,userId);
        }else{
            Log.e("NotificationService","getANotificationByPostsIdAndUserId get null input");
            return null;
        }
    }

    public List<Notification> getNotificationOverThirtyDay() throws ParseException {
        return NOTIFICATION_DAO.getNotificationOverThirtyDay();
    }

    public void deleteNotificationById(int id){
        NOTIFICATION_DAO.deleteNotificationById(id);
    }
}
