package ua.lukyanov.usermanaging.ui.auth.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.databinding.FragmentRegistrationBinding;
import ua.lukyanov.usermanaging.ui.BaseFragment;

public class RegistrationFragment extends BaseFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private RegistrationViewModel mRegisterViewModel;

    FragmentRegistrationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mRegisterViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        binding.setViewModel(mRegisterViewModel);
        binding.setLifecycleOwner(this);

        setupButtons();
        observeViewModel();

        return binding.getRoot();
    }

    private void setupButtons(){
        binding.btnRegistration.setOnClickListener(v ->
                mRegisterViewModel.makeRegistration(
                        binding.inputName.getText(),
                        binding.inputLogin.getText(),
                        binding.inputPassword.getText(),
                        binding.inputPasswordRepeat.getText()));

        binding.btnCancel.setOnClickListener(v ->
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_cancel));
    }

    private void observeViewModel() {
        mRegisterViewModel.isSuccessRegistration().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess && getView() != null) {
                Toast.makeText(getContext(), R.string.registration_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_cancel);
            }
        });

        mRegisterViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg ->
                showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel)));

    }


}