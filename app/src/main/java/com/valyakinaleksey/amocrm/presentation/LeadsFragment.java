package com.valyakinaleksey.amocrm.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valyakinaleksey.amocrm.R;
import com.valyakinaleksey.amocrm.domain.LeadsAdapter;
import com.valyakinaleksey.amocrm.domain.ServiceGenerator;
import com.valyakinaleksey.amocrm.models.api.APIError;
import com.valyakinaleksey.amocrm.models.api.Lead;
import com.valyakinaleksey.amocrm.models.api.LeadsResponse;
import com.valyakinaleksey.amocrm.models.api.Response;
import com.valyakinaleksey.amocrm.util.ErrorUtils;
import com.valyakinaleksey.amocrm.util.Logger;
import com.valyakinaleksey.amocrm.util.Session;
import com.valyakinaleksey.amocrm.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class LeadsFragment extends Fragment {
    private LeadsAdapter leadsAdapter;
    private List<Lead> leadList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        leadsAdapter = new LeadsAdapter(leadList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leads, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_leads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(leadsAdapter);
        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.clearSession(getContext());
                ((Navigator) getActivity()).navigateToFragment(new LoginFragment());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ServiceGenerator.AmoCrmApiInterface service = ServiceGenerator.createService(ServiceGenerator.AmoCrmApiInterface.class);
        if (!TextUtils.isEmpty(Session.SESSION_ID)) {
            Call<Response<LeadsResponse>> responseCall = service.getLeads();
            responseCall.enqueue(new Callback<Response<LeadsResponse>>() {
                @Override
                public void onResponse(retrofit.Response<Response<LeadsResponse>> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        List<Lead> leads = response.body().response.leads;
                        leadList.clear();
                        leadList.addAll(leads);
                        leadsAdapter.notifyDataSetChanged();
                    } else {
                        APIError apiError = ErrorUtils.parseError(response);
                        if (!TextUtils.isEmpty(apiError.error)) {
                            ToastUtils.showShortMessage(apiError.error, getContext());
                            Logger.d(apiError.error);
                            Logger.d(apiError.error_code);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Logger.d(t.toString());
                    ToastUtils.showShortMessage("Check your internet connection", getContext());
                }
            });

        }
    }
}
