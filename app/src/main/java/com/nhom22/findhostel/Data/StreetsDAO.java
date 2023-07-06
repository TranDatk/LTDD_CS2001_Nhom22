package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

public class StreetsDAO {
    private DatabaseHelper dbHelper;

    public StreetsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Streets getStreetsById(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "sub_districts_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("streets", columns, selection, selectionArgs, null, null, null);

        Streets streets = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int streetId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int subdistrictId = cursor.getInt(cursor.getColumnIndex("sub_districts_id"));

            // Lấy thông tin SubDistrict từ cơ sở dữ liệu dựa trên subdistrictId
            SubDistrictsDAO subDistrictsDAO = new SubDistrictsDAO(YourApplication.getInstance().getApplicationContext());
            SubDistricts subdistrict = subDistrictsDAO.getSubDistrictById(subdistrictId);

            // Tạo đối tượng Streets từ các cột trong Cursor và đối tượng SubDistricts
            streets = new Streets(streetId, name, isActive, subdistrict);
        }

        cursor.close();
        db.close();

        return streets;
    }


}
