package com.valyakinaleksey.amocrm.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valyakinaleksey.amocrm.R;
import com.valyakinaleksey.amocrm.domain.RestClient;
import com.valyakinaleksey.amocrm.models.AuthResponse;
import com.valyakinaleksey.amocrm.models.Response;
import com.valyakinaleksey.amocrm.util.Logger;
import com.valyakinaleksey.amocrm.util.ToastUtils;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loginInit(view);
        return view;
    }

    private void loginInit(View view) {
        view.findViewById(R.id.hardcode_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestClient.AmoCrmApiInterface client = RestClient.getClient(GsonConverterFactory.create());
                Call<Response<AuthResponse>> itemCall = client.apiLogin("xzaleksey@gmail.com", "nighthunger00");
                itemCall.enqueue(new Callback<Response<AuthResponse>>() {
                    @Override
                    public void onResponse(retrofit.Response<Response<AuthResponse>> response, Retrofit retrofit) {
                        Logger.d(response.toString());
                        ToastUtils.showShortMessage("Login successful", getContext());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Logger.d(t.toString());
                        ToastUtils.showShortMessage("Check your internet connection", getContext());
                    }
                });
            }
        });
    }


}
