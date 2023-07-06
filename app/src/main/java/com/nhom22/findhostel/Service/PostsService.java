package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;

public class PostsService {
    private final static PostsDAO POSTS_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        POSTS_DAO = new PostsDAO(appContext);
    }

    public Posts getPostById(int postsId) throws ParseException {
        if(postsId >= 0){
            return POSTS_DAO.getPostById(postsId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null postsId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
