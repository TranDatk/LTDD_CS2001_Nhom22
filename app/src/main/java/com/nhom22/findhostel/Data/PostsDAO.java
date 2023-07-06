package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

            // Định dạng của cột "time_from" và "time_to" trong SQLite là "YYYY-MM-DD HH:MM:SS.SSS"
            // Để chuyển đổi thành đối tượng Timestamp, sử dụng SimpleDateFormat
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date timeFromDate = sdf.parse(timeFromStr);
            Date timeToDate = sdf.parse(timeToStr);

            // Tạo đối tượng Timestamp từ các giá trị Date
            Timestamp timeFrom = new Timestamp(timeFromDate.getTime());
            Timestamp timeTo = new Timestamp(timeToDate.getTime());

            @SuppressLint("Range") String postName = cursor.getString(cursor.getColumnIndex("post_name"));
            @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") int activePost = cursor.getInt(cursor.getColumnIndex("active_post"));
            @SuppressLint("Range") int addressId = cursor.getInt(cursor.getColumnIndex("address_id"));
            @SuppressLint("Range") int userAccountId = cursor.getInt(cursor.getColumnIndex("owner_id"));
            @SuppressLint("Range") int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));

            // Lấy thông tin Address từ cơ sở dữ liệu dựa trên addressId
            AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
            Address address = addressDAO.getAddressById(addressId);

            // Lấy thông tin UserAccount từ cơ sở dữ liệu dựa trên userAccountId
            UserAccountDAO userAccountDAO = new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
            UserAccount userAccount = userAccountDAO.getUserAccountById(userAccountId);

            // Lấy thông tin Type từ cơ sở dữ liệu dựa trên typeId
            TypeDAO typeDAO = new TypeDAO(YourApplication.getInstance().getApplicationContext());
            Type type = typeDAO.getTypeById(typeId);

            // Tạo đối tượng Posts từ các cột trong Cursor và các đối tượng Address, UserAccount, Type
            post = new Posts(postId, timeFrom, timeTo, postName, price, description, activePost, address, userAccount, type);
        }

        cursor.close();
        db.close();

        return post;
    }
}
