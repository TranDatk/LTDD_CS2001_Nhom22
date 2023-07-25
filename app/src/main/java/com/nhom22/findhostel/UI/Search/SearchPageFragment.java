package com.nhom22.findhostel.UI.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nhom22.findhostel.UI.Map.MapActivity;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.databinding.FragmentSearchPageBinding;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment {
    List<Posts> items = null;

    SearchPageAdapter adapter;

    ListView lvPost;

    ImageButton btnSearch;

    TextView edtKeyWord;

    int counter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchPageFragment newInstance(String param1, String param2) {
        SearchPageFragment fragment = new SearchPageFragment();
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
        FragmentSearchPageBinding binding = FragmentSearchPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        lvPost = view.findViewById(R.id.lvPost);
        String[] itemsSpinner = {"Mới nhất", "Giá thấp đến cao", "Giá cao đến thấp"};
        Spinner snOptions = view.findViewById(R.id.snOptions);
        edtKeyWord = view.findViewById(R.id.edtKeyword);
        btnSearch = view.findViewById(R.id.btnSearch);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, itemsSpinner);
        snOptions.setAdapter(adapterSpinner);
        PostsService postsService = new PostsService();
        try {
            items = postsService.getAllPost();
        } catch (ParseException e) {throw new RuntimeException(e);
        }

        if (!items.isEmpty()) {
            counter = items.size();
//            adapter = new SearchPageAdapter(this, items);
//            lvPost.setAdapter(adapter);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = edtKeyWord.getText().toString().trim();
                if(!keyword.isEmpty()) {
                    List<Posts> backUpItems;
                    backUpItems = items.stream().filter(post -> (post.getAddress().getCities().getName()
                            + post.getAddress().getDistricts().getName()
                            + post.getAddress().getSubDistrics().getName()
                            + post.getAddress().getStreets().getName()
                            + post.getAddress().getHouseNumber()).toLowerCase().contains(keyword.toLowerCase()) ||
                                    post.getType().getName().toLowerCase()
                                            .contains(keyword.toLowerCase()) || post.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                            .collect(Collectors.toList());
                    if (backUpItems.isEmpty()) {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        items = backUpItems;
                    }
                    updateListView(items);
                    edtKeyWord.setText(null);
                }
                else {
                    Toast.makeText(getContext(), "Hãy nhập từ khóa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle dataBundle = new Bundle();

                dataBundle.putInt("id", items.get(i).getId());

                PostDetailFragment postDetailFragment = new PostDetailFragment();
                postDetailFragment.setArguments(dataBundle);

                replaceFragment(postDetailFragment);
            }
        });

        snOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (itemsSpinner[i].equals("Mới nhất")) {
//                    List<Posts> itemss;
//                    PostsService postsService = new PostsService();
//                    try {
//                        itemss = postsService.getAllPost();
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    if (!itemss.isEmpty()) {
//                        Collections.reverse(itemss);
//                        updateListView(itemss);
//                    }
                    if (items.size() != counter) {
                        try {
                            items = postsService.getAllPost();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (!items.isEmpty()) {
                        Collections.reverse(items);
                        updateListView(items);
                    }
                    else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }

                if (itemsSpinner[i].equals("Giá thấp đến cao")) {
//                    List<Posts> itemss;
//                    PostsService postsService = new PostsService();
//                    try {
//                        itemss = postsService.getAllPost();
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    if (!itemss.isEmpty()) {
//                        itemss = itemss.stream().filter(post -> String.valueOf(post.getPrice()) != null)
//                                .sorted(Comparator.comparingDouble(Posts::getPrice))
//                                .collect(Collectors.toList());
//                        updateListView(itemss);
//                    }
                    if (items.size() != counter) {
                        try {
                            items = postsService.getAllPost();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (!items.isEmpty()) {
                        items = items.stream().filter(post -> String.valueOf(post.getPrice()) != null)
                                .sorted(Comparator.comparingDouble(Posts::getPrice))
                                .collect(Collectors.toList());
                        updateListView(items);
                    }
                    else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }

                if (itemsSpinner[i].equals("Giá cao đến thấp")) {
//                    List<Posts> itemss;
//                    PostsService postsService = new PostsService();
//                    try {
//                        itemss = postsService.getAllPost();
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    if (!itemss.isEmpty()) {
//                        itemss = itemss.stream().filter(post -> String.valueOf(post.getPrice()) != null)
//                                .sorted(Comparator.comparingDouble(Posts::getPrice).reversed())
//                                .collect(Collectors.toList());
//                        updateListView(itemss);
//                    }
                    if (items.size() != counter) {
                        try {
                            items = postsService.getAllPost();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (!items.isEmpty()) {
                        items = items.stream().filter(post -> String.valueOf(post.getPrice()) != null)
                                .sorted(Comparator.comparingDouble(Posts::getPrice).reversed())
                                .collect(Collectors.toList());
                        updateListView(items);
                    }
                    else {
                        Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void updateListView(List<Posts> itemss) {
            adapter = new SearchPageAdapter(SearchPageFragment.this, itemss);
            lvPost.setAdapter(adapter);
    }


    private void filterByKeyword(String keyword) {
        items = items.stream().filter(post -> (post.getAddress().getCities().getName()
                        + post.getAddress().getDistricts().getName()
                        + post.getAddress().getSubDistrics().getName()
                        + post.getAddress().getStreets().getName()
                        + post.getAddress().getHouseNumber()).toLowerCase().contains(keyword.toLowerCase()) ||
                        post.getType().getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        post.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton mapSearchButton = view.findViewById(R.id.mapSearch);

        mapSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi button được click
                // Chuyển đến MapActivity khi bấm vào button
                Intent intent = new Intent(requireContext(), MapActivity.class);
                startActivity(intent);

            }
        });
    }
}