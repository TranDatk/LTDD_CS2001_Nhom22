package com.nhom22.findhostel.UI.Search;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.ybq.android.spinkit.style.FoldingCube;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Map.MapActivity;
import com.nhom22.findhostel.databinding.FragmentSearchPageBinding;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class SearchPageFragment extends Fragment {
    List<Posts> items = null;

    SearchPageAdapter adapter;

    ListView lvPost;

    ImageButton btnSearch;

    TextView edtKeyWord;

    int counter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SearchPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSearchPageBinding binding = FragmentSearchPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Tìm ProgressBar và ListView trong layout
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setIndeterminateDrawable(new FoldingCube());

        lvPost = view.findViewById(R.id.lvPost);
        // Ẩn ListView khi đang load dữ liệu
        lvPost.setVisibility(View.GONE);
        new LoadDataAsyncTask(progressBar, lvPost).execute();

        String[] itemsSpinner = {"Mới nhất", "Giá thấp đến cao", "Giá cao đến thấp"};
        Spinner snOptions = view.findViewById(R.id.snOptions);
        edtKeyWord = view.findViewById(R.id.edtKeyword);
        btnSearch = view.findViewById(R.id.btnSearch);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, itemsSpinner);
        snOptions.setAdapter(adapterSpinner);
        PostsService postsService = new PostsService();
        try {
            items = postsService.getListPostsActive();
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
                            items = postsService.getListPostsActive();
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
                            items = postsService.getListPostsActive();
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
                            items = postsService.getListPostsActive();
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

    public class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressBar progressBar;
        private ListView listView;

        public LoadDataAsyncTask(ProgressBar progressBar, ListView listView) {
            this.progressBar = progressBar;
            this.listView = listView;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Thực hiện các tác vụ nền ở đây (ví dụ: load dữ liệu)
            // Ví dụ:
            try {
                Thread.sleep(2000); // Giả lập thời gian load dữ liệu (3 giây)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Sau khi tác vụ nền hoàn thành, ẩn ProgressBar và hiển thị ListView
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}