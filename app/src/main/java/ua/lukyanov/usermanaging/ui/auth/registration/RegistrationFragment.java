package ua.lukyanov.usermanaging.ui.auth.registration;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import ua.lukyanov.usermanaging.views.PasswordInputView;
import ua.lukyanov.usermanaging.views.TextInputView;

public class RegistrationFragment extends BaseFragment {

    private static final String TAG = "RegistrationFragment";

    private TextInputView textInputName;
    private TextInputView textInputEmail;
    private PasswordInputView textInputPassword;
    private PasswordInputView textInputPasswordRepeat;

    @Inject
    ViewModelProvider.Factory factory;
    private RegistrationViewModel mRegisterViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mRegisterViewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false);
        binding.setViewModel(mRegisterViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        setupTextInputs(view);
        setupRegisterButton(view);
        setupCancelButton(view);

        observeRegistration();
        observeErrors();
        return view;
    }

    private void setupTextInputs(View view) {
        textInputName = view.findViewById(R.id.inputName);
        textInputName.initView(getString(R.string.name), getString(R.string.name_hint), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);

        textInputEmail = view.findViewById(R.id.inputLogin);
        textInputEmail.initView(getString(R.string.email), "", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);

        textInputPassword = view.findViewById(R.id.inputPassword);
        textInputPassword.initView();

        textInputPasswordRepeat = view.findViewById(R.id.inputPasswordRepeat);
        textInputPasswordRepeat.initView();
        textInputPasswordRepeat.setTitle(getString(R.string.repeat_password));
    }

    private void setupRegisterButton(View view) {
        Button button = view.findViewById(R.id.btnRegistration);
        button.setOnClickListener(v -> {
            mRegisterViewModel.makeRegistration(textInputName.getText(), textInputEmail.getText(), textInputPassword.getText(), textInputPasswordRepeat.getText());
        });
    }

    private void setupCancelButton(View view) {
        Button button = view.findViewById(R.id.btnCancel);
        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_cancel));
    }

    private void observeRegistration() {
        mRegisterViewModel.isSuccessRegistration().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess && getView() != null){
                Toast.makeText(getContext(), R.string.registration_success, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigate(R.id.action_cancel);
            }
        });
    }

    private void observeErrors(){
        mRegisterViewModel.getNameErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputName.showError(errorMsg);
        });

        mRegisterViewModel.getLoginErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputEmail.showError(errorMsg);
        });

        mRegisterViewModel.getPasswordErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputPassword.showError(errorMsg);
        });

        mRegisterViewModel.getPasswordRepeatErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputPasswordRepeat.showError(errorMsg);
        });

        mRegisterViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel));
        });

    }


}