package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

public class SubDistrictsDAO {
    private DatabaseHelper dbHelper;

    public SubDistrictsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public SubDistricts getSubDistrictById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "districts_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("sub_districts", columns, selection, selectionArgs, null, null, null);

        SubDistricts subdistrict = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int subdistrictId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int districtId = cursor.getInt(cursor.getColumnIndex("districts_id"));

            // Lấy thông tin District từ cơ sở dữ liệu dựa trên districtId
            DistricsDAO districsDAO = new DistricsDAO(YourApplication.getInstance().getApplicationContext());
            Districts district = districsDAO.getDistrictById(districtId);

            // Tạo đối tượng SubDistricts từ các cột trong Cursor và đối tượng Districts
            subdistrict = new SubDistricts(subdistrictId, name, isActive, district);
        }

        cursor.close();
        db.close();

        return subdistrict;
    }

}
