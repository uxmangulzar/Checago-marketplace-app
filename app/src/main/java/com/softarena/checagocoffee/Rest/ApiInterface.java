package com.softarena.checagocoffee.Rest;


import com.softarena.checagocoffee.Acitivity.Signin.Model.SignUpRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Acitivity.Category.CategoryRequestModel;
import com.softarena.checagocoffee.Acitivity.Category.GetAllCategoryResponseModel;
import com.softarena.checagocoffee.Acitivity.Subcategories.GetAllSubCategoryResponseModel;
import com.softarena.checagocoffee.Acitivity.Home.HomeMainResponsedata;
import com.softarena.checagocoffee.Acitivity.Home.HomeRequestModel;
import com.softarena.checagocoffee.Acitivity.Notifications.NotificationrequestModel;
import com.softarena.checagocoffee.Acitivity.Notifications.NotificationresponseModel;
import com.softarena.checagocoffee.Acitivity.Orders.OrderRequestModel;
import com.softarena.checagocoffee.Acitivity.Products.ProductRequestModel;
import com.softarena.checagocoffee.Acitivity.Products.ProductsResponseModel;
import com.softarena.checagocoffee.Acitivity.Shops.ShopRequestModel;
import com.softarena.checagocoffee.Acitivity.Shops.ShopResponseModel;
import com.softarena.checagocoffee.Acitivity.Subcategories.SubCategoryRequestModel;
import com.softarena.checagocoffee.Acitivity.Profile.UpdateUserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.UsermainResponseModel;
import com.softarena.checagocoffee.Acitivity.card.AddcardRequestModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @POST("Functions/user_login.php")
    Call<UserResponseModel> loginUser(@Body UserRequestModel modelSigninSending);
    @POST("Functions/TRACK_ORDER.php")
    Call<UserResponseModel> trackOrder(@Header("Authorization") String token, @Body UserRequestModel userRequestModel);

    @POST("Functions/create_new_user.php")
    Call<UserResponseModel> signUpUser(@Body SignUpRequestModel modelSignupSending);

    @POST("Functions/GET_ALL_SUBCATEGORIES.php")
    Call<GetAllSubCategoryResponseModel> getAllSubCategories(@Header("Authorization") String token, @Body SubCategoryRequestModel subCategoryRequestModel);

    @POST("Functions/GET_USER_BY_USERID.php")
    Call<UsermainResponseModel> getUserByuserId(@Header("Authorization") String token, @Body UserRequestModel userRequestModel);

    @POST("Functions/ADD_NEW_ORDER.php")
    Call<UserResponseModel> addNewOrder(@Header("Authorization") String token, @Body OrderRequestModel orderRequestModel);

    @POST("Functions/ADD_ORDER_RATING.php")
    Call<UserResponseModel> addOrderRating(@Header("Authorization") String token, @Body OrderRequestModel orderRequestModel);

    @POST("Functions/DELETE_CARD_BY_CARDID.php")
    Call<UserResponseModel> deleteCard(@Header("Authorization") String token, @Body AddcardRequestModel addcardRequestModel);

    @POST("Functions/ADD_CARD.php")
    Call<UserResponseModel> addCard(@Header("Authorization") String token, @Body AddcardRequestModel addcardRequestModel);

    @POST("Functions/UPDATE_CUSTOMER_SHIPMENT_ADDRESS.php")
    Call<UserResponseModel> updateCustomerShipment(@Header("Authorization") String token, @Body UserRequestModel userRequestModel);

    @POST("Functions/forgot_password.php")
    Call<UserResponseModel> forgotPassSendEmail(@Body UserRequestModel userRequestModel);

     @POST("Functions/UPDATE_USER_PASSWORD.php")
     Call<UserResponseModel> updatePass( @Body UserRequestModel userRequestModel);

     @POST("Functions/GET_SHIPMENT_RATES.php")
     Call<UserResponseModel> getShipingrates(@Header("Authorization") String token, @Body UserRequestModel userRequestModel);

    @POST("Functions/UPDATE_CUSTOMER_PROFILE_BY_USERID.php")
    Call<UserResponseModel> updateCustomerById(@Header("Authorization") String token, @Body UpdateUserRequestModel updateUserRequestModel);

    @POST("Functions/GET_SHOPS_BY_CATID.php")
    Call<ShopResponseModel> getAllshopbycat(@Header("Authorization") String token, @Body ShopRequestModel shopRequestModel);

    @POST("Functions/GET_CARDS_BY_USERID.php")
    Call<ShopResponseModel> getcardsByuserId(@Header("Authorization") String token, @Body ShopRequestModel shopRequestModel);

    @POST("Functions/GET_ALL_CUSTOMER_ORDERS.php")
    Call<ShopResponseModel> getAllcustomerOrders(@Header("Authorization") String token, @Body OrderRequestModel orderRequestModel);

    @POST("Functions/GET_ORDER_ALL_PRODUCTS.php")
    Call<ProductsResponseModel> getOrderproducts(@Header("Authorization") String token, @Body OrderRequestModel orderRequestModel);

    @POST("Functions/GET_ALL_NOTIFICATIONS.php")
    Call<NotificationresponseModel> getAllnotifications(@Header("Authorization") String token, @Body NotificationrequestModel notificationrequestModel);

    @POST("Functions/GET_SHOP_ALL_REVIEWS.php")
    Call<ShopResponseModel> getAllshopReviews(@Header("Authorization") String token, @Body ShopRequestModel shopRequestModel);

    @POST("Functions/GET_SHOP_BY_SHOPID.php")
    Call<ShopResponseModel> getShopbyShopId(@Header("Authorization") String token, @Body ShopRequestModel shopRequestModel);

    @POST("Functions/GET_SHOP_PRODUCTS_FOR_USER.php")
    Call<ProductsResponseModel> getShopProducts(@Header("Authorization") String token, @Body ProductRequestModel categoryRequestModel);

    @POST("Functions/GET_SUGGESTED_PRODUCTS.php")
        Call<ProductsResponseModel> getSuggestedProducts(@Header("Authorization") String token, @Body ProductRequestModel categoryRequestModel);
 @POST("Functions/GET_ALL_PRODUCTS_BY_SUBID.php")
        Call<ProductsResponseModel> getProductsBysubcat(@Header("Authorization") String token, @Body ProductRequestModel categoryRequestModel);

    @POST("Functions/GET_PRODUCT_BY_PRODUCTID.php")
    Call<ProductsResponseModel> getProductByProductId(@Header("Authorization") String token, @Body ProductRequestModel categoryRequestModel);

    @Multipart
    @POST("Functions/UPLOAD_FILES.php")
    Call<UserResponseModel> uploadFile(@Part List<MultipartBody.Part> file,
                                       @Part("type") RequestBody type);

    @POST("Functions/GET_HOME_SCREEN_DATA.php")
    Call<HomeMainResponsedata> getHomeData(@Header("Authorization") String token, @Body HomeRequestModel categoryRequestModel);

    @POST("Functions/GET_ALL_CATEGORIES.php")
    Call<GetAllCategoryResponseModel> getAllCategories(@Header("Authorization") String token, @Body CategoryRequestModel categoryRequestModel);





}
