package com.nhom22.findhostel.Firebase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.Model.Utilities;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebasePromises {
    public static CompletableFuture<List<Cities>> getCities() {
        CompletableFuture<List<Cities>> future = new CompletableFuture<>();

        CitiesFirebase citiesFirebase = new CitiesFirebase();
        citiesFirebase.getCities(new CitiesFirebase.CitiesCallback() {
            @Override
            public void onCityLoaded(List<Cities> cities) {
                future.complete(cities);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<UserAccount>> getUserAccount() {
        CompletableFuture<List<UserAccount>> future = new CompletableFuture<>();

        UserAccountFirebase userAccountFirebase = new UserAccountFirebase();
        userAccountFirebase.getUserAccount(new UserAccountFirebase.UserAccountCallback() {
            @Override
            public void onUserAccountLoaded(List<UserAccount> userAccountList) {
                future.complete(userAccountList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Districts>> getDistricts() {
        CompletableFuture<List<Districts>> future = new CompletableFuture<>();

        DistrictsFirebase districtsFirebase = new DistrictsFirebase();
        districtsFirebase.getDistricts(new DistrictsFirebase.DistrictsCallback() {
            @Override
            public void onDistrictsLoaded(List<Districts> districts) {
                future.complete(districts);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<SubDistricts>> getSubDistricts() {
        CompletableFuture<List<SubDistricts>> future = new CompletableFuture<>();

        SubDistrictsFirebase subDistrictsFirebase = new SubDistrictsFirebase();
        subDistrictsFirebase.getSubDistricts(new SubDistrictsFirebase.SubDistrictsCallback() {
            @Override
            public void onSubDistrictsLoaded(List<SubDistricts> subDistrictsList) {
                future.complete(subDistrictsList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Streets>> getStreets() {
        CompletableFuture<List<Streets>> future = new CompletableFuture<>();

        StreetsFirebase streetsFirebase = new StreetsFirebase();
        streetsFirebase.getStreets(new StreetsFirebase.StreetsCallback() {
            @Override
            public void onStreetsLoaded(List<Streets> streetsList) {
                future.complete(streetsList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Address>> getAddress() {
        CompletableFuture<List<Address>> future = new CompletableFuture<>();

        AddressFirebase addressFirebase = new AddressFirebase();
        addressFirebase.getAddress(new AddressFirebase.AddressCallback() {
            @Override
            public void onAddressLoaded(List<Address> addressList) {
                future.complete(addressList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Type>> getType() {
        CompletableFuture<List<Type>> future = new CompletableFuture<>();

        TypeFirebase typeFirebase = new TypeFirebase();
        typeFirebase.getType(new TypeFirebase.TypeCallback() {
            @Override
            public void onTypeLoaded(List<Type> typeList) {
                future.complete(typeList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Posts>> getPosts() {
        CompletableFuture<List<Posts>> future = new CompletableFuture<>();

        PostsFirebase postsFirebase = new PostsFirebase();
        postsFirebase.getPosts(new PostsFirebase.PostsCallback() {
            @Override
            public void onPostsLoaded(List<Posts> postsList) {
                future.complete(postsList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Images>> getImages() {
        CompletableFuture<List<Images>> future = new CompletableFuture<>();

        ImagesFirebase imagesFirebase = new ImagesFirebase();
        imagesFirebase.getImages(new ImagesFirebase.ImagesCallback() {
            @Override
            public void onImagesLoaded(List<Images> imagesList) {
                future.complete(imagesList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Detail_Image>> getDetailImage() {
        CompletableFuture<List<Detail_Image>> future = new CompletableFuture<>();

        Detail_ImageFirebase detail_imageFirebase = new Detail_ImageFirebase();
        detail_imageFirebase.getDetailImage(new Detail_ImageFirebase.DetailImageCallback() {
            @Override
            public void onDetailImageLoaded(List<Detail_Image> detailImageList) {
                future.complete(detailImageList);
            }

            @Override
            public void onError(String errorMessage) {
                future.completeExceptionally(new Exception(errorMessage));
            }
        });

        return future;
    }

    public static CompletableFuture<List<Furniture>> getFurniture() {
        CompletableFuture<List<Furniture>> furniture = new CompletableFuture<>();

        FurnitureFirebase furnitureFirebase = new FurnitureFirebase();
        furnitureFirebase.getFurniture(new FurnitureFirebase.FurnitureCallback() {
            @Override
            public void onFurnitureLoaded(List<Furniture> furnitureList) {
                furniture.complete(furnitureList);
            }

            @Override
            public void onError(String errorMessage) {
                furniture.completeExceptionally(new Exception(errorMessage));
            }
        });

        return furniture;
    }

    public static CompletableFuture<List<Detail_Furniture>> getDetailFurniture() {
        CompletableFuture<List<Detail_Furniture>> detail_furniture = new CompletableFuture<>();

        Detail_FurnitureFirebase detail_furnitureFirebase = new Detail_FurnitureFirebase();
        detail_furnitureFirebase.getDetailFurniture(new Detail_FurnitureFirebase.DetailFurnitureCallback() {
            @Override
            public void onDetailFurnitureLoaded(List<Detail_Furniture> detail_furnitureList) {
                detail_furniture.complete(detail_furnitureList);
            }

            @Override
            public void onError(String errorMessage) {
                detail_furniture.completeExceptionally(new Exception(errorMessage));
            }
        });

        return detail_furniture;
    }

    public static CompletableFuture<List<Utilities>> getUtilities() {
        CompletableFuture<List<Utilities>> utilities = new CompletableFuture<>();

        UtilitiesFirebase utilitiesFirebase = new UtilitiesFirebase();
        utilitiesFirebase.getUtilities(new UtilitiesFirebase.UtilitiesCallback() {
            @Override
            public void onUtilitiesLoaded(List<Utilities> utilitiesList) {
                utilities.complete(utilitiesList);
            }

            @Override
            public void onError(String errorMessage) {
                utilities.completeExceptionally(new Exception(errorMessage));
            }
        });

        return utilities;
    }
}
