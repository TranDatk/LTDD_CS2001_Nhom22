package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

public class AddressDAO {
    private DatabaseHelper dbHelper;

    public AddressDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Address getAddressById(int addressId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "house_number",
                "is_active",
                "cities_id",
                "districts_id",
                "sub_districts_id",
                "streets_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(addressId)};

        Cursor cursor = db.query("address", columns, selection, selectionArgs, null, null, null);

        Address address = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String houseNumber = cursor.getString(cursor.getColumnIndex("house_number"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("cities_id"));
            @SuppressLint("Range") int districtId = cursor.getInt(cursor.getColumnIndex("districts_id"));
            @SuppressLint("Range") int subdistrictId = cursor.getInt(cursor.getColumnIndex("sub_districts_id"));
            @SuppressLint("Range") int streetId = cursor.getInt(cursor.getColumnIndex("streets_id"));

            // Lấy thông tin City, District, SubDistrict, Street từ cơ sở dữ liệu dựa trên các id tương ứng
            CitiesDAO citiesDAO = new CitiesDAO(YourApplication.getInstance().getApplicationContext());
            Cities city = citiesDAO.getCityById(cityId);

            DistricsDAO districsDAO = new DistricsDAO(YourApplication.getInstance().getApplicationContext());
            Districts district = districsDAO.getDistrictById(districtId);

            SubDistrictsDAO subDistrictsDAO = new SubDistrictsDAO(YourApplication.getInstance().getApplicationContext());
            SubDistricts subdistrict = subDistrictsDAO.getSubDistrictById(subdistrictId);

            StreetsDAO streetsDAO = new StreetsDAO(YourApplication.getInstance().getApplicationContext());
            Streets street = streetsDAO.getStreetsById(streetId);

            // Tạo đối tượng Address từ các cột trong Cursor và các đối tượng City, District, SubDistrict, Street
            address = new Address(id, houseNumber, isActive, city, district, subdistrict, street);
        }

        cursor.close();
        db.close();

        return address;
    }

}
