package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.YourApplication;

public class DistricsDAO {
    private DatabaseHelper dbHelper;

    public DistricsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Districts getDistrictById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "cities_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("districts", columns, selection, selectionArgs, null, null, null);

        Districts district = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int districtId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String isActive = cursor.getString(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("cities_id"));

            // Lấy thông tin City từ cơ sở dữ liệu dựa trên cityId
            CitiesDAO citiesDAO = new CitiesDAO(YourApplication.getInstance().getApplicationContext());
            Cities city = citiesDAO.getCityById(cityId);

            // Tạo đối tượng Districts từ các cột trong Cursor và đối tượng Cities
            district = new Districts(districtId, name, isActive, city);
        }

        cursor.close();
        db.close();

        return district;
    }
}
