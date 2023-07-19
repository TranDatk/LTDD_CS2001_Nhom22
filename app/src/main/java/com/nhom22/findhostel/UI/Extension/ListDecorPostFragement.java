package com.nhom22.findhostel.UI.Extension;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
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
import com.nhom22.findhostel.Service.PostDecorService;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentAccountPageBinding;
import com.nhom22.findhostel.databinding.FragmentListDecorPostBinding;
import com.nhom22.findhostel.databinding.FragmentSecondRegisterFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class ListDecorPostFragement extends Fragment {

    public static DatabaseHelper dataBase;
    ListView lsvItem;
    List<PostDecor> arrItem;
    ItemPostAdapter itemAdapter;

    PostDecorService postDecorService = new PostDecorService();
    private int userId = 0 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        userId = sharedPreferences.getInt("userId", -1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentListDecorPostBinding binding = FragmentListDecorPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());



        if (userId > 0) {
            binding.btnAddPostDeco.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddPostDeco.setVisibility(View.GONE);
        }



        lsvItem = binding.lvItemDecoPost;
        arrItem = postDecorService.getAllPostDecor();
        itemAdapter = new ItemPostAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_post_decor_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);

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