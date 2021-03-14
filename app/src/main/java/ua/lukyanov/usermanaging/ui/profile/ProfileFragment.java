package ua.lukyanov.usermanaging.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.ui.adapters.UserPropertiesAdapter;
import ua.lukyanov.usermanaging.databinding.FragmentProfileBinding;
import ua.lukyanov.usermanaging.ui.BaseFragment;

public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelProvider.Factory factory;
    private ProfileViewModel mProfileViewModel;

    FragmentProfileBinding binding;

    private final UserPropertiesAdapter propertiesAdapter = new UserPropertiesAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mProfileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setViewModel(mProfileViewModel);
        binding.setLifecycleOwner(this);

        setupRecyclerView();
        observeViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.obtainUserProperties();
    }

    private void setupRecyclerView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvUserProperties.getContext(), RecyclerView.VERTICAL);
        binding.rvUserProperties.addItemDecoration(dividerItemDecoration);
        binding.rvUserProperties.setAdapter(propertiesAdapter);
    }

    private void observeViewModel() {
        mProfileViewModel.getUserProperties().observe(getViewLifecycleOwner(), properties ->
                propertiesAdapter.setProperties(properties)
        );

        mProfileViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg ->
                showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel))
        );
    }

}
