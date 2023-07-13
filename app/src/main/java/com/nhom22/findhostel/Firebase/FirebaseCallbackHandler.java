package com.nhom22.findhostel.Firebase;

import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Model.Detail_Image;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Streets;
import com.nhom22.findhostel.Model.SubDistricts;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.Model.UserAccount;

import java.text.ParseException;
import java.util.List;

public class FirebaseCallbackHandler implements CitiesFirebase.CitiesCallback, DistrictsFirebase.DistrictsCallback
        , SubDistrictsFirebase.SubDistrictsCallback, StreetsFirebase.StreetsCallback,
        AddressFirebase.AddressCallback, UserAccountFirebase.UserAccountCallback,
        TypeFirebase.TypeCallback, PostsFirebase.PostsCallback, ImagesFirebase.ImagesCallback,
        Detail_ImageFirebase.DetailImageCallback {

    private MainActivity mainActivity;

    public FirebaseCallbackHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // Implement methods from CitiesFirebase.CitiesCallback
    @Override
    public void onCityLoaded(List<Cities> cities) {
        mainActivity.onCityLoaded(cities);
    }

    @Override
    public void onAddressLoaded(List<Address> addressesList) {
        mainActivity.onAddressLoaded(addressesList);
    }

    @Override
    public void onTypeLoaded(List<Type> typeList) {
        mainActivity.onTypeLoaded(typeList);
    }

    @Override
    public void onPostsLoaded(List<Posts> postsList) {
        mainActivity.onPostsLoaded(postsList);
    }

    @Override
    public void onImagesLoaded(List<Images> images) {
        mainActivity.onImagesLoaded(images);
    }

    @Override
    public void onDetailImageLoaded(List<Detail_Image> detail_imageList) throws ParseException {
        mainActivity.onDetailImageLoaded(detail_imageList);
    }

    @Override
    public void onError(String errorMessage) {
        mainActivity.onError(errorMessage);
    }

    // Implement methods from DistrictsFirebase.DistrictsCallback
    @Override
    public void onDistrictsLoaded(List<Districts> districts) {
        mainActivity.onDistrictsLoaded(districts);
    }

    @Override
    public void onSubDistrictsLoaded(List<SubDistricts> subDistrictsList) {
        mainActivity.onSubDistrictsLoaded(subDistrictsList);
    }

    @Override
    public void onStreetsLoaded(List<Streets> streetsList) {
        mainActivity.onStreetsLoaded(streetsList);
    }

    @Override
    public void onUserAccountLoaded(List<UserAccount> userAccountList) {
        mainActivity.onUserAccountLoaded(userAccountList);
    }
}
