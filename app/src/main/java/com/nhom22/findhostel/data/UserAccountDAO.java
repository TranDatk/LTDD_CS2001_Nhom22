package com.nhom22.findhostel.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nhom22.findhostel.model.UserAccount;


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

      db.close();

      return id;
   }

   public int updateUserAccount(UserAccount userAccount) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      ContentValues values = new ContentValues();
      values.put("password", userAccount.getPassword());

      int rowsAffected = db.update("user_accounts", values, "username = ?",
              new String[]{userAccount.getUsername()});

      db.close();

      return rowsAffected;
   }

   public int deleteUserAccount(String username) {
      SQLiteDatabase db = dbHelper.getWritableDatabase();

      int rowsAffected = db.delete("user_accounts", "username = ?", new String[]{username});

      db.close();

      return rowsAffected;
   }
}
