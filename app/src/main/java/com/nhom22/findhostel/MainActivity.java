package com.nhom22.findhostel;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Data.UserAccountDAO;
import com.nhom22.findhostel.Firebase.FirebasePromises;
import com.nhom22.findhostel.Firebase.ImageUserAccountFirebase;
import com.nhom22.findhostel.Firebase.Save_PostFirebase;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.Model.Utilities;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.Detail_UtilitiesService;
import com.nhom22.findhostel.Service.DistrictsService;
import com.nhom22.findhostel.Service.FurnitureService;
import com.nhom22.findhostel.Service.ImagesService;
import com.nhom22.findhostel.Service.NotificationService;
import com.nhom22.findhostel.Service.PostDecorService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.Save_PostService;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.SubDistrictsService;
import com.nhom22.findhostel.Service.TypeService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.Service.UtilitiesService;
import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.UI.Notification.NotificationPageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;
import com.nhom22.findhostel.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PostsDAO postsDAO= new PostsDAO(YourApplication.getInstance().getApplicationContext());
    private UserAccountDAO userAccountDAO= new UserAccountDAO(YourApplication.getInstance().getApplicationContext());
    private NotificationService notificationService = new NotificationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        binding.navigation.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Fragment selectedFragment = null;
                if (newTab.getId() == R.id.navigation_notification) {
                    selectedFragment = new NotificationPageFragment();
                } else if (newTab.getId() == R.id.navigation_exten) {
                    selectedFragment = new ExtensionPageFragment();
                } else if (newTab.getId() == R.id.navigation_search) {
                    selectedFragment = new SearchPageFragment();
                } else if (newTab.getId() == R.id.navigation_info) {
                    selectedFragment = new SavePageFragment();
                } else if (newTab.getId() == R.id.navigation_account) {
                    selectedFragment = new AccountPageFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, selectedFragment)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        Fragment defaultFragment = new SearchPageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, defaultFragment)
                .commit();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        createFirebase();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void createFirebase() {
        Save_PostFirebase save_postFirebase = new Save_PostFirebase();
        String[] tables = new String[]{"save_post"};
        for (String table : tables){
            save_postFirebase.resetFirebaseIds(table);
        }

        FirebasePromises.getCities()
                .thenCompose(cities -> {
                    onCityLoaded(cities);
                    return FirebasePromises.getDistricts();
                })
                .thenCompose(districts -> {
                    onDistrictsLoaded(districts);
                    return FirebasePromises.getSubDistricts();
                })
                .thenCompose(subDistricts -> {
                    onSubDistrictsLoaded(subDistricts);
                    return FirebasePromises.getStreets();
                })
                .thenCompose(streets -> {
                    onStreetsLoaded(streets);
                    return FirebasePromises.getAddress();
                })
                .thenCompose(addresses -> {
                    onAddressLoaded(addresses);
                    return FirebasePromises.getFurniture();
                })
                .thenCompose(furniture-> {
                    onFurnitureLoaded(furniture);
                    return FirebasePromises.getUtilities();
                })
                .thenCompose(utilities -> {
                    onUtilitiesLoaded(utilities);
                    return FirebasePromises.getUserAccount();
                })
                .thenCompose(userAccounts -> {
                    onUserAccountLoaded(userAccounts);
                    return FirebasePromises.getType();
                })
                .thenCompose(types -> {
                    onTypeLoaded(types);
                    return FirebasePromises.getPosts();
                })
                .thenCompose(posts -> {
                    onPostsLoaded(posts);
                    return FirebasePromises.getSavePost();
                })
                .thenCompose(savePosts -> {
                    try {
                        onSavePostLoaded(savePosts);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return FirebasePromises.getPostDecor();
                }).thenCompose(postDecorList -> {
                    onPostDecorLoaded(postDecorList);
                    return FirebasePromises.getDetailFurniture();
                })
                .thenCompose(detail_furnitures -> {
                    try {
                        onDetailFurnitureLoaded(detail_furnitures);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    return FirebasePromises.getDetailUtilities();
                })
                .thenCompose(detail_utilities -> {
                    onDetailUtilitiesLoaded(detail_utilities);
                    return FirebasePromises.getImages();
                })
                .thenCompose(images -> {
                    onImagesLoaded(images);
                    return FirebasePromises.getDetailImage();
                })
                .thenAccept(detailImages -> {
                    try {
                        onDetailImageLoaded(detailImages);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(error -> {
                    onError(error.getMessage());
                    return null;
                });
    }

    public void onDistrictsLoaded(List<Districts> districts) {
        DistrictsService districtsService = new DistrictsService();
        districtsService.deleteAllDistricts();
        districtsService.resetDistrictsAutoIncrement();
        for (Districts district : districts) {
            districtsService.addDistricts(district);
        }

    }

    public void onCityLoaded(List<Cities> cities) {
        CitiesService citiesService = new CitiesService();
        citiesService.deleteAllCities();
        citiesService.resetCitiesAutoIncrement();
        for (Cities city : cities) {
            citiesService.addCities(city);
            // Thực hiện các xử lý khác với citiesService
        }
    }

    public void onSubDistrictsLoaded(List<SubDistricts> subDistrictsList) {
        SubDistrictsService subDistrictsService = new SubDistrictsService();
        subDistrictsService.deleteAllSubDistricts();
        subDistrictsService.resetSubDistrictsAutoIncrement();
        for (SubDistricts subDistricts : subDistrictsList) {
            subDistrictsService.addSubDistricts(subDistricts);
            if (subDistricts.getId() == 10) {
                return;
            }
            // Thực hiện các xử lý khác với citiesService
        }
    }

    public void onStreetsLoaded(List<Streets> streetsList) {
        StreetsService streetsService = new StreetsService();
        streetsService.deleteAllStreets();
        streetsService.resetStreetsAutoIncrement();
        for (Streets streets : streetsList) {
            streetsService.addStreets(streets);
        }
    }

    public void onAddressLoaded(List<Address> addressList) {
        AddressService addressService = new AddressService();
        addressService.deleteAllAddress();
        addressService.resetAddressAutoIncrement();
        for (Address address : addressList) {
            addressService.addAddress(address);
        }
    }

    public void onUserAccountLoaded(List<UserAccount> userAccountList) {
        UserAccountService userAccountService = new UserAccountService();
        userAccountService.deleteAllUserAccount();
        userAccountService.resetUserAccountAutoIncrement();

        for (UserAccount userAccount : userAccountList) {
            userAccountService.addUserAccount(userAccount);
            ImageUserAccountFirebase.getImageUserAccount(String.valueOf(userAccount.getId()) + ".png",
                    new ImageUserAccountFirebase.ImageDownloadListener() {
                        @Override
                        public void onImageDownloaded(byte[] imageData) {
                            userAccountService.insertImageUserAccount(userAccount.getId(), imageData);
                        }

                        @Override
                        public void onImageDownloadFailed(String errorMessage) {
                            // Xử lý khi có lỗi tải xuống ảnh
                            Log.e("ErrorDowloadImage", "Failed to download image: " + errorMessage);
                        }
                    });
        }
    }

    public void onTypeLoaded(List<Type> typeList) {
        TypeService typeService = new TypeService();
        typeService.deleteAllType();
        typeService.resetTypeAutoIncrement();
        for (Type type : typeList) {
            typeService.addType(type);
        }
    }

    public void onPostsLoaded(List<Posts> postsList) {
        PostsService postsService = new PostsService();
        postsService.deleteAllPosts();
        postsService.resetPostsAutoIncrement();
        for (Posts posts : postsList) {
            postsService.addAPost(posts);
        }
    }

    public void onImagesLoaded(List<Images> imagesList) {
        ImagesService imagesService = new ImagesService();
        imagesService.deleteAllImages();
        imagesService.resetImagesAutoIncrement();

        for (Images images : imagesList) {
            imagesService.addAImages(images);

            final Images finalImages = images;

            ImageUserAccountFirebase.getImages(String.valueOf(images.getId()) + ".png",
                    new ImageUserAccountFirebase.ImageDownloadListener() {
                        @Override
                        public void onImageDownloaded(byte[] imageData) {
                            imagesService.insertImages(finalImages.getId(), imageData);
                        }

                        @Override
                        public void onImageDownloadFailed(String errorMessage) {
                            // Xử lý khi có lỗi tải xuống ảnh
                            Log.e("ErrorDowloadImage", "Failed to download image: " + errorMessage);
                        }
                    });
        }
    }

    public void onDetailImageLoaded(List<Detail_Image> detail_imageList) throws ParseException {
        Detail_ImageService detail_imageService = new Detail_ImageService();
        detail_imageService.deleteAllDetailImage();
        detail_imageService.resetDetailImageAutoIncrement();
        for (Detail_Image detail_image : detail_imageList) {
            detail_imageService.addADetailImage(detail_image.getImages().getId(), detail_image.getPosts().getId());
        }
    }


    public void onFurnitureLoaded(List<Furniture> furnitureList) {
        FurnitureService furnitureService = new FurnitureService();
        furnitureService.deleteAllFurniture();
        furnitureService.resetFurnitureAutoIncrement();
        for (Furniture furniture : furnitureList) {
            furnitureService.addAFurniture(furniture);
        }
    }

    public void onDetailFurnitureLoaded(List<Detail_Furniture> detail_furnitureList) throws ParseException {
        Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();
        detail_furnitureService.deleteAllDetailFurniture();
        detail_furnitureService.resetDetailFurnitureAutoIncrement();
        for (Detail_Furniture detail_furniture : detail_furnitureList) {
            detail_furnitureService.addADetailFurniture(detail_furniture);
        }
    }

    public void onUtilitiesLoaded(List<Utilities> utilitiesList) {
        UtilitiesService utilitiesService = new UtilitiesService();
        utilitiesService.deleteAllUtilities();
        utilitiesService.resetUtilitiesAutoIncrement();
        for (Utilities utilities : utilitiesList) {
            utilitiesService.addAUtilities(utilities);
        }
    }

    public void onDetailUtilitiesLoaded(List<Detail_Utilities> detail_utilitiesList) {
        Detail_UtilitiesService detail_utilitiesService = new Detail_UtilitiesService();
        detail_utilitiesService.deleteAllDetailUtilities();
        detail_utilitiesService.resetDetailUtilitiesAutoIncrement();
        for (Detail_Utilities detail_utilities : detail_utilitiesList) {
            detail_utilitiesService.addADetailUtilities(detail_utilities);
        }
    }

    public void onSavePostLoaded(List<Save_Post> save_postList) throws ParseException {
        Save_PostService save_postService = new Save_PostService();
        save_postService.deleteAllSavePost();
        save_postService.resetSavePostAutoIncrement();
        for (Save_Post savePost : save_postList) {
            save_postService.addASavePost(savePost.getPosts().getId(), savePost.getUserAccount().getId());
        }
    }

    public void onError(String errorMessage) {
        Toast.makeText(MainActivity.this, "Thực hiện truy vấn firebase thất bại", Toast.LENGTH_LONG).show();
        Log.d("FirebaseLog", errorMessage);
    }

    private void onPostDecorLoaded(List<PostDecor> postDecorList) {
        PostDecorService postDecorService = new PostDecorService();
        postDecorService.deleteAllPostDecor();
        postDecorService.resetPostDecorAutoIncrement();

        for (PostDecor postDecor : postDecorList) {
            postDecorService.addPostDecor(postDecor);
            ImageUserAccountFirebase.getPostDecorImage(String.valueOf(postDecor.getId()) + ".png",
                    new ImageUserAccountFirebase.ImageDownloadListener() {
                        @Override
                        public void onImageDownloaded(byte[] imageData) {
                            postDecorService.insertImagePostDecor(postDecor.getId(), imageData);
                        }

                        @Override
                        public void onImageDownloadFailed(String errorMessage) {
                            // Xử lý khi có lỗi tải xuống ảnh
                            Log.e("ErrorDowloadImage", "Failed to download image: " + errorMessage);
                        }
                    });
        }
    }
}



