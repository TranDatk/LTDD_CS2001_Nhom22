package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.util.List;

public class PostsService {
    private final static PostsDAO POSTS_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference postsRef = database.getReference("posts");

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

    public long addAPost(Posts post) {
        if(post != null){
            long result = POSTS_DAO.addAPost(post);

            UserAccount userAccountFirebase = post.getUserAccount();
            userAccountFirebase.setImage(null);

            Posts postsFirebase = new Posts(Integer.parseInt(String.valueOf(result)), post.getTimeFrom(),post.getTimeTo(),
                    post.getPostName(), post.getPrice(),
                    post.getDescription(), post.getActivePost(),
                   post.getAddress(),userAccountFirebase , post.getType());

            postsRef.child(String.valueOf(postsFirebase.getId())).setValue(postsFirebase);

            return result; // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null posts", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllPosts() {
        POSTS_DAO.deleteAllPosts();
    }

    public void resetPostsAutoIncrement() {
        POSTS_DAO.resetPostsAutoIncrement();
    }

    public List<Posts> getAllPost() throws ParseException {
        return POSTS_DAO.getAllPosts();
    }

    public List<Posts> getPostsBySubDistrics(int subDistrics) throws ParseException {
        if(subDistrics >= 0){
            return POSTS_DAO.getPostsBySubDistrics(subDistrics);
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null posts", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<Posts> getPostsByOwnerId(int ownerId) throws ParseException {
        if(ownerId >= 0){
            return POSTS_DAO.getPostsByOwnerId(ownerId);
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null posts", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<Posts> getPostsByDistricsAndNotUserId(Districts districs, UserAccount userAccount) throws ParseException{
        if(districs != null){
            return POSTS_DAO.getPostsByDistricsAndNotUserId(districs, userAccount);
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null posts", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<Posts> getListPostsActive() throws ParseException {
        return POSTS_DAO.getListPostsActive();
    }
}
