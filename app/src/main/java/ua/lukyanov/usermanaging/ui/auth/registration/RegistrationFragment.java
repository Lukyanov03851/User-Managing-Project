package ua.lukyanov.usermanaging.ui.auth.registration;

import android.app.AlertDialog;
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

        binding.btnCancel.setOnClickListener(v -> getActivity().onBackPressed());
    }

    private void observeViewModel() {
        mRegisterViewModel.isSuccessRegistration().observe(getViewLifecycleOwner(), isSuccess -> {
            showRegistrationSuccessDialog();
        });

        mRegisterViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg ->
                showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel)));

    }

    private void showRegistrationSuccessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.registration_success);

        builder.setMessage(R.string.registration_success_message)
                .setPositiveButton(R.string.action_continue, (dialog, which) -> {
                    dialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_continue);
                })
                .show();
    }


}