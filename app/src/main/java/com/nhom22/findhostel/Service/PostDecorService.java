package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.PostDecorDAO;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.YourApplication;

public class PostDecorService {
    private final static PostDecorDAO POST_DECOR_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        POST_DECOR_DAO = new PostDecorDAO(appContext);
    }

    public long addPostDecor(PostDecor postDecor) {
        if(postDecor != null){
            return POST_DECOR_DAO.addPostDecor(postDecor); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postDecor", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }
}
