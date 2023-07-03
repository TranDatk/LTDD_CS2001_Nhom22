package com.nhom22.findhostel.ui.Extension;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;
import com.nhom22.findhostel.ui.Account.RegistrationFragment;
import com.nhom22.findhostel.ui.Home.HomePageFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExtensionPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtensionPageFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExtensionPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExtensionPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExtensionPageFragment newInstance(String param1, String param2) {
        ExtensionPageFragment fragment = new ExtensionPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String[] items = {"", "",
            ""};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentExtensionPageBinding binding = FragmentExtensionPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.lnListHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExtensionActivity.class);
                startActivity(intent);
            }
        });

        binding.lnListDecor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CampaignPageFragment());
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