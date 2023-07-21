package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostsDAO {
    private DatabaseHelper dbHelper;

    public PostsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Posts getPostById(int id) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "time_from",
                "time_to",
                "post_name",
                "price",
                "description",
                "active_post",
                "address_id",
                "owner_id",
                "type_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("posts", columns, selection, selectionArgs, null, null, null);

        Posts post = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int postId = cursor.getInt(cursor.getColumnIndex("id"));

            @SuppressLint("Range") String timeFromStr = cursor.getString(cursor.getColumnIndex("time_from"));
            @SuppressLint("Range") String timeToStr = cursor.getString(cursor.getColumnIndex("time_to"));

            // Định dạng của cột "time_from" và "time_to" trong SQLite là "YYYY-MM-DD HH:MM:SS"
            // Để chuyển đổi thành đối tượng Timestamp, sử dụng SimpleDateFormat
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
            Date timeFromDate = sdf.parse(timeFromStr);
            Date timeToDate = sdf.parse(timeToStr);


            @SuppressLint("Range") String postName = cursor.getString(cursor.getColumnIndex("post_name"));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int activePost = cursor.getInt(cursor.getColumnIndex("active_post"));
            @SuppressLint("Range") int addressId = cursor.getInt(cursor.getColumnIndex("address_id"));
            @SuppressLint("Range") int userAccountId = cursor.getInt(cursor.getColumnIndex("owner_id"));
            @SuppressLint("Range") int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));

            // Lấy thông tin AddressFirebase từ cơ sở dữ liệu dựa trên addressId
            AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
            Address address = addressDAO.getAddressById(addressId);

            // Lấy thông tin UserAccount từ cơ sở dữ liệu dựa trên userAccountId
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            UserAccount userAccount = userAccountDAO.getUserAccountById(userAccountId);

            // Lấy thông tin Type từ cơ sở dữ liệu dựa trên typeId
            TypeDAO typeDAO = new TypeDAO(YourApplication.getInstance().getApplicationContext());
            Type type = typeDAO.getTypeById(typeId);

            // Tạo đối tượng Posts từ các cột trong Cursor và các đối tượng AddressFirebase, UserAccount, Type
            post = new Posts(postId, timeFromDate, timeToDate, postName, price, description, activePost, address, userAccount, type);
        }

        cursor.close();
        db.close();

        return post;
    }

    public long addAPost(Posts post) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("time_from", post.getTimeFrom().toString());
        values.put("time_to", post.getTimeTo().toString());
        values.put("post_name", post.getPostName());
        values.put("price", post.getPrice());
        values.put("description", post.getDescription());
        values.put("active_post", post.getActivePost());
        values.put("address_id", post.getAddress().getId());
        values.put("owner_id", post.getUserAccount().getId());
        values.put("type_id", post.getType().getId());

        long id = db.insert("posts", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }
        db.close();
        return id;
    }

    public void deleteAllPosts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("posts", null, null);

        db.close();
    }

    public void resetPostsAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='posts'";
        db.execSQL(query);

        db.close();
    }

    public int getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "posts";
        Cursor cursor = db.rawQuery(query, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return id;
    }

    @SuppressLint("Range")
    public List<Posts> getAllPosts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "time_from",
                "time_to",
                "post_name",
                "price",
                "description",
                "active_post",
                "address_id",
                "owner_id",
                "type_id"
        };

        Cursor cursor = db.query("posts", columns, null, null, null, null, null);

        List<Posts> postList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String timeFromStr = cursor.getString(cursor.getColumnIndex("time_from"));
            String timeToStr = cursor.getString(cursor.getColumnIndex("time_to"));
            String postName = cursor.getString(cursor.getColumnIndex("post_name"));
            float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int activePost = cursor.getInt(cursor.getColumnIndex("active_post"));
            int addressId = cursor.getInt(cursor.getColumnIndex("address_id"));
            int ownerId = cursor.getInt(cursor.getColumnIndex("owner_id"));
            int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));

            // Create SimpleDateFormat with the correct pattern
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));

            // Parse the date strings using the SimpleDateFormat
            Date timeFrom;
            Date timeTo;
            try {
                timeFrom = dateFormat.parse(timeFromStr);
                timeTo = dateFormat.parse(timeToStr);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parsing exception as needed
                continue; // Skip this iteration and proceed with the next iteration
            }

            // Tạo các đối tượng liên quan
            AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            TypeDAO typeDAO = new TypeDAO(YourApplication.getInstance().getApplicationContext());

            Address address = addressDAO.getAddressById(addressId);
            UserAccount userAccount = userAccountDAO.getUserAccountById(ownerId);
            Type type = typeDAO.getTypeById(typeId);

            // Tạo đối tượng Posts và thêm vào danh sách
            Posts post = new Posts(id, timeFrom, timeTo, postName, price, description, activePost, address, userAccount, type);
            postList.add(post);
        }

        cursor.close();

        return postList;
    }

    @SuppressLint("Range")
    public List<Posts> getPostsBySubDistrics(int subDistrics) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "time_from",
                "time_to",
                "post_name",
                "price",
                "description",
                "active_post",
                "address_id",
                "owner_id",
                "type_id"
        };

        Cursor cursor = db.query("posts", columns, null, null, null, null, null);

        List<Posts> postList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String timeFromStr = cursor.getString(cursor.getColumnIndex("time_from"));
            String timeToStr = cursor.getString(cursor.getColumnIndex("time_to"));
            String postName = cursor.getString(cursor.getColumnIndex("post_name"));
            float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int activePost = cursor.getInt(cursor.getColumnIndex("active_post"));
            int addressId = cursor.getInt(cursor.getColumnIndex("address_id"));
            int ownerId = cursor.getInt(cursor.getColumnIndex("owner_id"));
            int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));

            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
            Date timeFrom = formatter.parse(timeFromStr);
            Date timeTo = formatter.parse(timeToStr);

            // Tạo các đối tượng liên quan
            AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            TypeDAO typeDAO = new TypeDAO(YourApplication.getInstance().getApplicationContext());

            Address address = addressDAO.getAddressById(addressId);
            UserAccount userAccount = userAccountDAO.getUserAccountById(ownerId);
            Type type = typeDAO.getTypeById(typeId);

            // Tạo đối tượng Posts và thêm vào danh sách
            Posts post = new Posts(id, timeFrom, timeTo, postName, price, description, activePost, address, userAccount, type);
            if (address.getSubDistrics().getId() == subDistrics) {
                postList.add(post);
            }
        }

        cursor.close();

        return postList;
    }

    @SuppressLint("Range")
    public List<Posts> getPostsByOwnerId(int ownerId) throws ParseException {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "time_from",
                "time_to",
                "post_name",
                "price",
                "description",
                "active_post",
                "address_id",
                "owner_id",
                "type_id"
        };

        Cursor cursor = db.query("posts", columns, null, null, null, null, null);

        List<Posts> postList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String timeFromStr = cursor.getString(cursor.getColumnIndex("time_from"));
            String timeToStr = cursor.getString(cursor.getColumnIndex("time_to"));
            String postName = cursor.getString(cursor.getColumnIndex("post_name"));
            float price = cursor.getFloat(cursor.getColumnIndex("price"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int activePost = cursor.getInt(cursor.getColumnIndex("active_post"));
            int addressId = cursor.getInt(cursor.getColumnIndex("address_id"));
            int ownerIdFromDb = cursor.getInt(cursor.getColumnIndex("owner_id"));
            int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));

            // Chuyển đổi chuỗi thời gian thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("en"));
            Date timeFrom = dateFormat.parse(timeFromStr);
            Date timeTo = dateFormat.parse(timeToStr);

            // Tạo các đối tượng liên quan
            AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            TypeDAO typeDAO = new TypeDAO(YourApplication.getInstance().getApplicationContext());

            Address address = addressDAO.getAddressById(addressId);
            UserAccount userAccount = userAccountDAO.getUserAccountById(ownerIdFromDb);
            Type type = typeDAO.getTypeById(typeId);

            // Tạo đối tượng Posts và thêm vào danh sách
            Posts post = new Posts(id, timeFrom, timeTo, postName, price, description, activePost, address, userAccount, type);
            if (ownerIdFromDb == ownerId) {
                postList.add(post);
            }
        }

        cursor.close();

        return postList;
    }
}
