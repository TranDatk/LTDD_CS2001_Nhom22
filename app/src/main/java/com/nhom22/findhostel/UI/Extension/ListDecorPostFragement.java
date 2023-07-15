package com.nhom22.findhostel.UI.Extension;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentAccountPageBinding;
import com.nhom22.findhostel.databinding.FragmentListDecorPostBinding;
import com.nhom22.findhostel.databinding.FragmentSecondRegisterFragmentBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListDecorPostFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListDecorPostFragement extends Fragment {

    public static DatabaseHelper dataBase;
    ListView lsvItem;
    ArrayList<PostDecor> arrItem;
    ItemPostAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentListDecorPostBinding binding = FragmentListDecorPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());

        lsvItem = binding.lvItemDecoPost;
        arrItem = new ArrayList<>();
        itemAdapter = new ItemPostAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_post_decor_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);

        Cursor cursor = dataBase.GetData("Select * From posts_extension Order By created_date DESC LIMIT 20");
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

       binding.imgBtnBackExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ExtensionPageFragment());
            }
        });

        binding.btnAddPostDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PostDecorPageAddFragement());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}