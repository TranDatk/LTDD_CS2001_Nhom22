package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

import java.util.ArrayList;
import java.util.List;


public class UserAccountDAO{
   private DatabaseHelper dbHelper;

   public UserAccountDAO(Context context) {
      dbHelper = new DatabaseHelper(context);
   }

   public long addUserAccount(UserAccount userAccount) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      ContentValues values = new ContentValues();
      values.put("username", userAccount.getUsername());
      values.put("email",userAccount.getEmail());
      values.put("phone",userAccount.getPhone());
      values.put("digital_money",userAccount.getDigital_money());
      values.put("role_user",userAccount.getRoleUser());
      values.put("avatar",userAccount.getImage());
      values.put("is_active",userAccount.getIsActive());
      values.put("password", userAccount.getPassword());
      values.put("address_id",userAccount.getAddress().getId());

      long id = db.insert("user_account", null, values);
      if(id > 0){
         id = getIdOfLastInsertedRow();
      }

      db.close();

      return id;
   }

   public int updateUserAccount(UserAccount userAccount) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      ContentValues values = new ContentValues();
      values.put("username", userAccount.getUsername());
      values.put("email", userAccount.getEmail());
      values.put("phone", userAccount.getPhone());
      values.put("digital_money", userAccount.getDigital_money());
      values.put("role_user", userAccount.getRoleUser());
      values.put("avatar", userAccount.getImage());
      values.put("is_active", userAccount.getIsActive());
      values.put("password", userAccount.getPassword());
      values.put("address_id", userAccount.getAddress().getId());

      String whereClause = "id = ?";
      String[] whereArgs = {String.valueOf(userAccount.getId())};

      int rowsAffected = db.update("user_account", values, whereClause, whereArgs);

      db.close();

      return rowsAffected;
   }


   public int deleteUserAccount(String username) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      int rowsAffected = db.delete("user_accounts", "username = ?", new String[]{username});

      db.close();

      return rowsAffected;
   }

   @SuppressLint("Range")
   public UserAccount getUserAccountById(Integer id) {
      SQLiteDatabase db = dbHelper.getReadableDatabase();

      String[] columns = {
              "username",
              "email",
              "phone",
              "digital_money",
              "role_user",
              "avatar",
              "is_active",
              "password",
              "address_id"
      };

      String selection = "id = ?";
      String[] selectionArgs = {String.valueOf(id)};

      Cursor cursor = db.query("user_account", columns, selection, selectionArgs, null, null, null);
      UserAccount user = null;

      if (cursor != null && cursor.moveToFirst()) {
         String username = cursor.getString( cursor.getColumnIndex("username"));
         String email = cursor.getString( cursor.getColumnIndex("email"));
         String phone = cursor.getString( cursor.getColumnIndex("phone"));
         Double digital_money = cursor.getDouble( cursor.getColumnIndex("digital_money"));
         Integer role_user = cursor.getInt( cursor.getColumnIndex("role_user"));
         byte[] avatar = cursor.getBlob( cursor.getColumnIndex("avatar"));
         Integer is_active = cursor.getInt( cursor.getColumnIndex("is_active"));
         String password = cursor.getString(cursor.getColumnIndex("password"));
         int address_id = cursor.getInt(cursor.getColumnIndex("address_id"));

         // YourApplication.getInstance().getApplicationContext() là biến toàn cục lấy context hiện tại
         AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
         Address address = addressDAO.getAddressById(address_id);

         // Tạo đối tượng User
         user = new UserAccount(id,username, email, password, phone,digital_money, role_user, avatar, is_active, address);
         cursor.close();
      }
      db.close();

      return user;
   }

   @SuppressLint("Range")
   public List<UserAccount> getAllUserAccountByDistrictId(Integer districs_id) {
      SQLiteDatabase db = dbHelper.getReadableDatabase();

      String[] columns = {
              "id",
              "username",
              "email",
              "phone",
              "digital_money",
              "role_user",
              "avatar",
              "is_active",
              "password",
              "address_id"
      };


      Cursor cursor = db.query("user_account", columns, null, null, null, null, null);

      List<UserAccount> userAccountListList = new ArrayList<>();

      while (cursor != null && cursor.moveToNext())
      {
         int id = cursor.getInt(cursor.getColumnIndex("id"));
         String username = cursor.getString( cursor.getColumnIndex("username"));
         String email = cursor.getString( cursor.getColumnIndex("email"));
         String phone = cursor.getString( cursor.getColumnIndex("phone"));
         Double digital_money = cursor.getDouble( cursor.getColumnIndex("digital_money"));
         Integer role_user = cursor.getInt( cursor.getColumnIndex("role_user"));
         byte[] avatar = cursor.getBlob( cursor.getColumnIndex("avatar"));
         Integer is_active = cursor.getInt( cursor.getColumnIndex("is_active"));
         String password = cursor.getString(cursor.getColumnIndex("password"));
         int address_id = cursor.getInt(cursor.getColumnIndex("address_id"));

         // YourApplication.getInstance().getApplicationContext() là biến toàn cục lấy context hiện tại
         AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
         Address address = addressDAO.getAddressById(address_id);

         // Tạo đối tượng User
         UserAccount user = new UserAccount(id,username, email, password, phone,digital_money, role_user, avatar, is_active, address);

         if(address.getDistricts().getId() == districs_id){
            userAccountListList.add(user);
         }
      }
      cursor.close();
      db.close();

      return userAccountListList;
   }
   @SuppressLint("Range")
   public List<String> getAllEmailUser() {
      SQLiteDatabase db = dbHelper.getReadableDatabase();

      String[] columns = {
              "email"
      };

      Cursor cursor = db.query("user_account", columns, null, null, null, null, null);

      List<String> userEmailList = new ArrayList<>();

      while (cursor != null && cursor.moveToNext()) {
         String email = cursor.getString(cursor.getColumnIndex("email"));
         userEmailList.add(email);
      }

      cursor.close();
      db.close();

      return userEmailList;
   }

   public void deleteAllUserAccount() {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      db.delete("user_account", null, null);

      db.close();
   }

   public void resetUserAccountAutoIncrement() {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      String query = "DELETE FROM sqlite_sequence WHERE name='user_account'";
      db.execSQL(query);

      db.close();
   }

   public void insertImageUserAccount(int idUserAccount, byte[] image) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put("avatar", image);
      String whereClause = "id = ?";
      String[] whereArgs = {String.valueOf(idUserAccount)};
      db.update("user_account", values, whereClause, whereArgs);
      db.close();
   }

   public int getIdOfLastInsertedRow() {
      SQLiteDatabase db = dbHelper.getReadableDatabase();
      String query = "SELECT last_insert_rowid() FROM " + "user_account";
      Cursor cursor = db.rawQuery(query, null);

      int id = -1;
      if (cursor != null && cursor.moveToFirst()) {
         id = cursor.getInt(0);
      }

      cursor.close();
      db.close();

      return id;
   }

   @SuppressLint("Range")
   public UserAccount checkUserLogin(String email, String password) {
      SQLiteDatabase db = dbHelper.getReadableDatabase();

      Cursor cursor = db.rawQuery("SELECT * FROM user_account WHERE email = ? AND password = ?", new String[]{email, password});

      UserAccount user = null;

      if (cursor != null && cursor.moveToFirst()) {
          int id = cursor.getInt(cursor.getColumnIndex("id"));
         String username = cursor.getString(cursor.getColumnIndex("username"));
         String phone = cursor.getString(cursor.getColumnIndex("phone"));
         Double digital_money = cursor.getDouble( cursor.getColumnIndex("digital_money"));
         Integer role_user = cursor.getInt( cursor.getColumnIndex("role_user"));
         byte[] avatar = cursor.getBlob( cursor.getColumnIndex("avatar"));
         Integer is_active = cursor.getInt( cursor.getColumnIndex("is_active"));

         AddressDAO addressDAO = new AddressDAO(YourApplication.getInstance().getApplicationContext());
         int address_id = cursor.getInt(cursor.getColumnIndex("address_id"));
         Address address = addressDAO.getAddressById(address_id);


         user = new UserAccount(id, username, email, password, phone, digital_money, role_user, avatar, is_active, address);
      }
      if (cursor != null) {
         cursor.close();
      }
      db.close();

      return user;
   }

}
