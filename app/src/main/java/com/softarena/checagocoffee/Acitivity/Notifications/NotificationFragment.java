package com.softarena.checagocoffee.Acitivity.Notifications;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {

   RecyclerView recycler_notificationsList;
    private List<NotificationModel> notificationModelList;
    private NotificationAdapter notificationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recycler_notificationsList = view.findViewById(R.id.recycler_notificationsList);
        notificationModelList = new ArrayList<>();
        recycler_notificationsList.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationAdapter=new NotificationAdapter(notificationModelList,getContext());
        recycler_notificationsList.setAdapter(notificationAdapter);
        getAllNotifications(getActivity());
        return view;
    }
    private void getAllNotifications(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        NotificationrequestModel notificationrequestModel = new NotificationrequestModel();
        notificationrequestModel.setLimit("100");
        notificationrequestModel.setPage("1");
        notificationrequestModel.setUser_id(user_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<NotificationresponseModel> call = apiService
                .getAllnotifications(accessToken, notificationrequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<NotificationresponseModel>() {
            @Override
            public void onResponse(Call<NotificationresponseModel> call,
                                   Response<NotificationresponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            notificationModelList.clear();
                            if (response.body().getGetnotificationResult()!=null){
                                notificationModelList.addAll(response.body().getGetnotificationResult());
                                notificationAdapter.notifyDataSetChanged();



                            }


                        }
                    }

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMsg = jObjError.getString("message");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationresponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
}