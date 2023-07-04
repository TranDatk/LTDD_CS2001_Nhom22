package com.nhom22.findhostel.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.nhom22.findhostel.model.PostDecor;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostDecorDAO extends SQLiteOpenHelper{
//    private static final String DATABASE_URL = "jdbc:sqlite:/data/data/com.nhom22.findhostel/databases/findhostel.sqlite";
//    private Connection connection;
//
//    public PostDecorDAO() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection(DATABASE_URL);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            // Xử lý lỗi khi không tìm thấy lớp JDBC
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Xử lý lỗi khi không thể kết nối đến cơ sở dữ liệu
//        }
//    }


//    public boolean addPostDecor(PostDecor postDecor) {
//        PreparedStatement stmt = null;
//
//        try {
//            String query = "INSERT INTO posts_extension (text, image, created_date, user_id, is_active) VALUES (?, ?, ?, ?, ?)";
//            stmt = connection.prepareStatement(query);
//            stmt.setString(1, postDecor.getText());
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(postDecor.getImage());
//            stmt.setBinaryStream(2, inputStream, postDecor.getImage().length);
//            stmt.setString(3, postDecor.getCreatedDate());
//            stmt.setInt(4, postDecor.getUserId());
//            stmt.setInt(5, postDecor.getIsActive());
//
//            int rowsInserted = stmt.executeUpdate();
//            return rowsInserted > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeStatement(stmt);
//        }
//
//        return false;
//    }

    public PostDecorDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase  database =getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void addPostDecor(String text, byte[] image, String created_date, int user_id, int isActive) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO posts_extension VALUES (null, ?, ?, ?, ?, ?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, text);
        sqLiteStatement.bindBlob(2, image);
        sqLiteStatement.bindString(3, created_date);
        sqLiteStatement.bindLong(4, user_id);
        sqLiteStatement.bindLong(5, isActive);
        sqLiteStatement.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public void closeConnection() {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
