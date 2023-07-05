package com.nhom22.findhostel.Data;

import android.content.Context;
import com.nhom22.findhostel.Data.DatabaseHelper;

public class AddressDAO {
    private DatabaseHelper dbHelper;

    public AddressDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

   /* public Address getAddressById(int addressId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "house_number",
                "is_active",
                "city_id",
                "district_id",
                "subdistrict_id",
                "street_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(addressId)};

        Cursor cursor = db.query("address", columns, selection, selectionArgs, null, null, null);

        Address address = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String houseNumber = cursor.getString(cursor.getColumnIndex("house_number"));
            int isActive = cursor.getInt(cursor.getColumnIndex("is_active"));
            int cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
            int districtId = cursor.getInt(cursor.getColumnIndex("district_id"));
            int subdistrictId = cursor.getInt(cursor.getColumnIndex("subdistrict_id"));
            int streetId = cursor.getInt(cursor.getColumnIndex("street_id"));

            // Lấy thông tin City, District, SubDistrict, Street từ cơ sở dữ liệu dựa trên các id tương ứng
            Cities city = getCityById(cityId);
            Districts district = getDistrictById(districtId);
            SubDistrics subdistrict = getSubDistrictById(subdistrictId);
            Streets street = getStreetById(streetId);

            // Tạo đối tượng Address từ các cột trong Cursor và các đối tượng City, District, SubDistrict, Street
            address = new Address(id, houseNumber, isActive, city, district, subdistrict, street);
        }

        cursor.close();
        db.close();

        return address;
    }*/

}
