package com.nhom22.findhostel.UI.Extension;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.R;

import java.util.ArrayList;

public class ListDecorPost extends AppCompatActivity {
    FloatingActionButton btnAddDecoPost;
    public static DatabaseHelper dataBase;
    ListView lsvItem;
    ArrayList<PostDecor> arrItem;
    ItemPostAdapter itemAdapter;
    ImageButton btnBackExtension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_decor_post);

        dataBase = new DatabaseHelper(this);

        lsvItem = (ListView) findViewById(R.id.lv_item_deco_post);
        arrItem = new ArrayList<>();
        itemAdapter = new ItemPostAdapter(this, R.layout.item_post_decor_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);

        Cursor cursor = dataBase.GetData("Select * From posts_extension");
        while(cursor.moveToNext()){
            arrItem.add(new PostDecor(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getBlob(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        itemAdapter.notifyDataSetChanged();

        btnBackExtension = (ImageButton) findViewById(R.id.img_btn_back_extension);
        btnBackExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddDecoPost = (FloatingActionButton) findViewById(R.id.btn_add_post_deco);
        btnAddDecoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListDecorPost.this , ExtensionActivity.class);
                startActivity(intent);
            }
        });

    }

}