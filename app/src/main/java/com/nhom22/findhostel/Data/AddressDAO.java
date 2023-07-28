package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;

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
        if (cursor != null && cursor.moveToFirst()) {
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

            // Tạo đối tượng AddressFirebase từ các cột trong Cursor và các đối tượng City, District, SubDistrict, Street
            address = new Address(id, houseNumber, isActive, city, district, subdistrict, street);
        }

        cursor.close();
        db.close();

        return address;
    }

    public List<Address> getAllAddress() {
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

        Cursor cursor = db.query("address", columns, null, null, null, null, null);

        List<Address> addressList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
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

                DistricsDAO districtsDAO = new DistricsDAO(YourApplication.getInstance().getApplicationContext());
                Districts district = districtsDAO.getDistrictById(districtId);

                SubDistrictsDAO subDistrictsDAO = new SubDistrictsDAO(YourApplication.getInstance().getApplicationContext());
                SubDistricts subdistrict = subDistrictsDAO.getSubDistrictById(subdistrictId);

                StreetsDAO streetsDAO = new StreetsDAO(YourApplication.getInstance().getApplicationContext());
                Streets street = streetsDAO.getStreetsById(streetId);

                // Tạo đối tượng AddressFirebase từ các cột trong Cursor và các đối tượng City, District, SubDistrict, Street
                Address address = new Address(id, houseNumber, isActive, city, district, subdistrict, street);
                addressList.add(address);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return addressList;
    }

    public long addAddress(Address address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("house_number", address.getHouseNumber());
        values.put("is_active", address.getIsActive());
        values.put("cities_id", address.getCities().getId());
        values.put("districts_id", address.getDistricts().getId());
        values.put("sub_districts_id", address.getSubDistrics().getId());
        values.put("streets_id", address.getStreets().getId());

        long result = db.insert("address", null, values);
        if(result > 0){
            result = getIdOfLastInsertedRow();
        }
        db.close();
        return result;
    }

    public int updateAddress(Address address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("house_number", address.getHouseNumber());
        values.put("is_active", address.getIsActive());
        values.put("cities_id", address.getCities().getId());
        values.put("districts_id", address.getDistricts().getId());
        values.put("sub_districts_id", address.getSubDistrics().getId());
        values.put("streets_id", address.getStreets().getId());

        int rowsAffected = db.update("address", values, "id=?", new String[]{String.valueOf(address.getId())});
        db.close();
        return rowsAffected;
    }


    public Address getAddressByNameStreetAndHouseNumber(String streetName, String houseNumber) {
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

        String selection = "house_number = ? AND streets_id IN (SELECT id FROM streets WHERE name = ?)";
        String[] selectionArgs = {houseNumber, streetName};

        Cursor cursor = db.query("address", columns, selection, selectionArgs, null, null, null);

        Address address = null;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String fetchedHouseNumber = cursor.getString(cursor.getColumnIndex("house_number"));
            @SuppressLint("Range") int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            @SuppressLint("Range") int cityId = cursor.getInt(cursor.getColumnIndex("cities_id"));
            @SuppressLint("Range") int districtId = cursor.getInt(cursor.getColumnIndex("districts_id"));
            @SuppressLint("Range") int subdistrictId = cursor.getInt(cursor.getColumnIndex("sub_districts_id"));
            @SuppressLint("Range") int streetId = cursor.getInt(cursor.getColumnIndex("streets_id"));

            // Retrieve City, District, SubDistrict, and Street information from the database based on the corresponding IDs
            CitiesDAO citiesDAO = new CitiesDAO(YourApplication.getInstance().getApplicationContext());
            Cities city = citiesDAO.getCityById(cityId);

            DistricsDAO districtsDAO = new DistricsDAO(YourApplication.getInstance().getApplicationContext());
            Districts district = districtsDAO.getDistrictById(districtId);

            SubDistrictsDAO subDistrictsDAO = new SubDistrictsDAO(YourApplication.getInstance().getApplicationContext());
            SubDistricts subdistrict = subDistrictsDAO.getSubDistrictById(subdistrictId);

            StreetsDAO streetsDAO = new StreetsDAO(YourApplication.getInstance().getApplicationContext());
            Streets street = streetsDAO.getStreetsById(streetId);

            // Create an Address object from the columns in the Cursor and the City, District, SubDistrict, and Street objects
            address = new Address(id, fetchedHouseNumber, isActive, city, district, subdistrict, street);
        }

        cursor.close();
        db.close();

        return address;
    }

    public void deleteAllAddress() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("address", null, null);

        db.close();
    }

    public void resetAddressAutoIncrement() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "DELETE FROM sqlite_sequence WHERE name='address'";
        db.execSQL(query);

        db.close();
    }

    public long getIdOfLastInsertedRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT last_insert_rowid() FROM " + "address";
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


