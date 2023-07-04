package com.nhom22.findhostel.UI.Save;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhom22.findhostel.R;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;
import com.nhom22.findhostel.databinding.FragmentSavePageBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavePageFragment newInstance(String param1, String param2) {
        SavePageFragment fragment = new SavePageFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSavePageBinding binding = FragmentSavePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] items = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
        SavedPostAdapter adapter = new SavedPostAdapter(this, items);
        ListView lvItems = view.findViewById(R.id.lvSavedPost);
        lvItems.setAdapter(adapter);
        return view;
    }
}