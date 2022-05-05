package com.farido.websockettestapp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farido.websockettestapp.R;
import com.farido.websockettestapp.databinding.FragmentCompanyBinding;
import com.farido.websockettestapp.view.adapter.CompanyListAdapter;
import com.farido.websockettestapp.viewmodel.CompanyViewModel;

public class CompanyFragment extends Fragment {

    private FragmentCompanyBinding fragmentCompanyBinding;
    private CompanyViewModel companyViewModel;
    private CompanyListAdapter companyListAdapter = new CompanyListAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCompanyBinding = fragmentCompanyBinding.inflate(inflater, container, false);
        return fragmentCompanyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        companyViewModel = new ViewModelProvider(this).get(CompanyViewModel.class);
        companyViewModel.startWebsocketService();

        companyViewModel.companyList.observe(getViewLifecycleOwner(), jsonArray -> {
            if (jsonArray != null) {
                fragmentCompanyBinding.itemRecyclerView.setVisibility(View.VISIBLE);
                fragmentCompanyBinding.itemProgressBar.setVisibility(View.INVISIBLE);
                companyListAdapter.updateList(jsonArray);
            }
        });

        companyViewModel.status.observe(getViewLifecycleOwner(), status -> {
            if (status != null) {
                if (status) {
                    fragmentCompanyBinding.statusTextView.setText("Connected");
                    fragmentCompanyBinding.statusConstraintLayout.setBackgroundResource(R.color.green);
                } else {
                    fragmentCompanyBinding.statusTextView.setText("Disconnected");
                    fragmentCompanyBinding.statusConstraintLayout.setBackgroundResource(R.color.red);
                }
                fragmentCompanyBinding.statusConstraintLayout.setVisibility(View.VISIBLE);
            } else {
                fragmentCompanyBinding.statusConstraintLayout.setVisibility(View.INVISIBLE);
            }
        });

        if (fragmentCompanyBinding.itemRecyclerView != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                    fragmentCompanyBinding.itemRecyclerView.getContext(),
                    linearLayoutManager.getOrientation()
            );
            fragmentCompanyBinding.itemRecyclerView.addItemDecoration(dividerItemDecoration);
            fragmentCompanyBinding.itemRecyclerView.setLayoutManager(linearLayoutManager);
            fragmentCompanyBinding.itemRecyclerView.setAdapter(companyListAdapter);
        }
    }
}