package ua.lukyanov.usermanaging.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.adapters.UserPropertiesAdapter;
import ua.lukyanov.usermanaging.databinding.FragmentProfileBinding;
import ua.lukyanov.usermanaging.ui.BaseFragment;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelProvider.Factory factory;
    private ProfileViewModel mProfileViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mProfileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setViewModel(mProfileViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rvUserProperties);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new UserPropertiesAdapter());
    }

}
