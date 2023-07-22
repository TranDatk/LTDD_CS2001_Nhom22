package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

public class DistricsDAO {
    private DatabaseHelper dbHelper;

    public DistricsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private CitiesService citiesService = new CitiesService();

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
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int districtId = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("cities_id"));

            // Lấy thông tin City từ cơ sở dữ liệu dựa trên cityId
            CitiesDAO citiesDAO = new CitiesDAO(YourApplication.getInstance().getApplicationContext());
            Cities city = citiesDAO.getCityById(cityId);

            // Tạo đối tượng Districts từ các cột trong Cursor và đối tượng CitiesFirebase
            district = new Districts(districtId, name, isActive, city);
        }

        cursor.close();
        db.close();

        return district;
    }

    public List<Districts> getAllDistricts() {
        List<Districts> districtList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "cities_id"
        };

        Cursor cursor = null;
        try {
            cursor = db.query("districts", columns, null, null, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");
            int columnIndexCitiesId = cursor.getColumnIndex("cities_id");

            while (cursor.moveToNext()) {
                int districtId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;
                int citiesId = columnIndexCitiesId != -1 ? cursor.getInt(columnIndexCitiesId) : -1;

                Cities city = citiesService.getCityById(citiesId);
                Districts district = new Districts(districtId, name, isActive, city);
                districtList.add(district);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception trace for debugging purposes
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return districtList;
    }

    public List<Districts> getAllDistrictsByCitiesId(int citiesId) {
        List<Districts> districtList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "name",
                "is_active",
                "cities_id"
        };

        String selection = "cities_id = ?";
        String[] selectionArgs = {String.valueOf(citiesId)};

        Cursor cursor = null;
        try {
            cursor = db.query("districts", columns, selection, selectionArgs, null, null, null);

            int columnIndexId = cursor.getColumnIndex("id");
            int columnIndexName = cursor.getColumnIndex("name");
            int columnIndexIsActive = cursor.getColumnIndex("is_active");

            while (cursor.moveToNext()) {
                int districtId = columnIndexId != -1 ? cursor.getInt(columnIndexId) : -1;
                String name = columnIndexName != -1 ? cursor.getString(columnIndexName) : null;
                int isActive = columnIndexIsActive != -1 ? cursor.getInt(columnIndexIsActive) : -1;

                Cities city = citiesService.getCityById(citiesId);
                Districts district = new Districts(districtId, name, isActive, city);
                districtList.add(district);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception trace for debugging purposes
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return districtList;
    }

    public long addDistricts(Districts district) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", district.getName());
        values.put("is_active", district.getIsActive());
        values.put("cities_id", district.getCities().getId());

        long id = db.insert("districts", null, values);
        if(id > 0){
            id = getIdOfLastInsertedRow();
        }
        db.close();

        return id;
    }

    public void deleteAllDistricts() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("districts", null, null);

        db.close();
    }

    public void resetDistrictsAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='districts'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "districts";
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(0);
        }

        cursor.close();
        db.close();

        return id;
    }
}
