package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.Save_PostDAO;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;

public class Save_PostService {
    private final static Save_PostDAO SAVE_POST_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        SAVE_POST_DAO = new Save_PostDAO(appContext);
    }

    public Save_Post getSavePostById(int savePostId) throws ParseException {
        if(savePostId >= 0){
            return SAVE_POST_DAO.getSavePostById(savePostId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public Save_Post getSavePostByPostIdAndUserAccountId(int postId, int userAccountId) throws ParseException {
        if(postId >= 0 && userAccountId >=0){
            return SAVE_POST_DAO.getSavePostByPostIdAndUserAccountId(postId,userAccountId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId or userAccountId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public long addASavePost(int postId, int userAccountId) {
        if(postId >= 0 && userAccountId >=0){
            return SAVE_POST_DAO.addASavePost(postId,userAccountId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId or userAccountId", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public long deleteASavePost(int savePostId) {
        if(savePostId >= 0){
            return SAVE_POST_DAO.deleteASavePost(savePostId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null savePostId", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }
}
