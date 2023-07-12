package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class SubDistrictsDAO {
    private DatabaseHelper dbHelper;

    private DistrictsService districtsService = new DistrictsService();

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

    public List<SubDistricts> getAllSubDistricts() {
        List<SubDistricts> subDistrictsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "districts_id"
        };

        Cursor cursor = null;
        try {
            cursor = db.query("sub_districts", columns, null, null, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");
            int columnIndexDistrictId = cursor.getColumnIndex("districts_id");

            while (cursor.moveToNext()) {
                int subDistrictId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;
                int districtId = columnIndexDistrictId != -1 ? cursor.getInt(columnIndexDistrictId) : -1;

                Districts district = districtsService.getDistrictById(districtId);

                SubDistricts subDistrict = new SubDistricts(subDistrictId, name, isActive, district);
                subDistrictsList.add(subDistrict);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return subDistrictsList;
    }

    public List<SubDistricts> getAllSubDistrictsByDistrictsId(int districtsId) {
        List<SubDistricts> subDistrictsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "districts_id"
        };

        String selection = "districts_id = ?";
        String[] selectionArgs = {String.valueOf(districtsId)};

        Cursor cursor = null;
        try {
            cursor = db.query("sub_districts", columns, selection, selectionArgs, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");

            while (cursor.moveToNext()) {
                int subDistrictId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;

                Districts district = districtsService.getDistrictById(districtsId);

                SubDistricts subDistrict = new SubDistricts(subDistrictId, name, isActive, district);
                subDistrictsList.add(subDistrict);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return subDistrictsList;
    }

    public long addSubDistricts(SubDistricts subDistricts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", subDistricts.getName());
        values.put("is_active", subDistricts.getIsActive());
        values.put("districts_id", subDistricts.getDistricts().getId());

        long id = db.insert("sub_districts", null, values);
        db.close();

        return id;
    }

    public void deleteAllSubDistricts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("sub_districts", null, null);

        db.close();
    }

    public void resetSubDistrictsAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='sub_districts'";
        db.execSQL(query);

        db.close();
    }
}
