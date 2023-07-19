package com.nhom22.findhostel.UI.Extension;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExtensionPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtensionPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentExtensionPageBinding binding = FragmentExtensionPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.lnListHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HostelCollectionPageFragment());
            }
        });

        binding.lnListDecor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               replaceFragment(new ListDecorPostFragement());
            }
        });

        // Inflate the layout for this fragment
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