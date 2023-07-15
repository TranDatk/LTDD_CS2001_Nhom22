package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class StreetsDAO {
    private DatabaseHelper dbHelper;

    private SubDistrictsService subDistrictsService = new SubDistrictsService();

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

    public List<Streets> getAllStreets() {
        List<Streets> streetsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "sub_districts_id"
        };

        Cursor cursor = null;
        try {
            cursor = db.query("streets", columns, null, null, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");
            int columnIndexSubDistrictsId = cursor.getColumnIndex("sub_districts_id");

            while (cursor.moveToNext()) {
                int streetId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;
                int subDistrictsId = columnIndexSubDistrictsId != -1 ? cursor.getInt(columnIndexSubDistrictsId) : -1;

                // Get the corresponding SubDistricts object from the database
                SubDistricts subDistricts = subDistrictsService.getSubDistrictById(subDistrictsId);

                // Create a Streets object from the columns in the Cursor and the SubDistricts object
                Streets streets = new Streets(streetId, name, isActive, subDistricts);
                streetsList.add(streets);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception trace for debugging purposes
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return streetsList;
    }

    public List<Streets> getAllStreetsBySubDistrictId(int subDistrictId) {
        List<Streets> streetsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "sub_districts_id"
        };

        String selection = "sub_districts_id = ?";
        String[] selectionArgs = {String.valueOf(subDistrictId)};

        Cursor cursor = null;
        try {
            cursor = db.query("streets", columns, selection, selectionArgs, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");

            while (cursor.moveToNext()) {
                int streetId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;

                // Get the corresponding SubDistricts object from the database
                SubDistricts subDistricts = subDistrictsService.getSubDistrictById(subDistrictId);

                // Create a Streets object from the columns in the Cursor and the SubDistricts object
                Streets streets = new Streets(streetId, name, isActive, subDistricts);
                streetsList.add(streets);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception trace for debugging purposes
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return streetsList;
    }

    public long addStreets(Streets streets) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", streets.getName());
        values.put("is_active", streets.getIsActive());
        values.put("sub_districts_id", streets.getSubDistrics().getId());

        long id = db.insert("streets", null, values);
        db.close();

        return id;
    }

    public void deleteAllStreets() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("streets", null, null);

        db.close();
    }

    public void resetStreetsAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='streets'";
        db.execSQL(query);

        db.close();
    }
}
