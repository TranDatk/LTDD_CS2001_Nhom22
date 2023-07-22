package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.NotificationDAO;
import com.nhom22.findhostel.Model.Notification;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class NotificationService {
    public static final NotificationDAO NOTIFICATION_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        NOTIFICATION_DAO = new NotificationDAO(appContext);
    }

    public long addNotification(Notification notification) {
        if(notification != null){
            return NOTIFICATION_DAO.addNotification(notification); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null notification", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Notification> getAllNotification() throws ParseException{
        return NOTIFICATION_DAO.getAllNotification();
    }
}
