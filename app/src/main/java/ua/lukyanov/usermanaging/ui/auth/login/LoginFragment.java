package ua.lukyanov.usermanaging.ui.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.databinding.FragmentLoginBinding;
import ua.lukyanov.usermanaging.ui.BaseFragment;
import ua.lukyanov.usermanaging.views.PasswordInputView;
import ua.lukyanov.usermanaging.views.TextInputView;

public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";

    private TextInputView textInputEmail;
    private PasswordInputView textInputPassword;

    @Inject
    ViewModelProvider.Factory factory;
    private LoginViewModel mLoginViewModel;
    private OnLoginSuccessInteraction successInteraction;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mLoginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setViewModel(mLoginViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        setupTextInputs(view);
        setupRegisterButton(view);
        setupLoginButton(view);

        observeLogin();
        observeErrors();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccessInteraction){
            successInteraction = (OnLoginSuccessInteraction) context;
        } else {
            throw  new IllegalStateException("Activity must implement OnLoginSuccessInteraction interface");
        }
    }

    private void setupTextInputs(View view) {
        textInputEmail = view.findViewById(R.id.inputLogin);
        textInputEmail.initView(getString(R.string.email), "", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);
        textInputPassword = view.findViewById(R.id.inputPassword);
        textInputPassword.initView();
    }

    private void setupRegisterButton(View view) {
        Button button = view.findViewById(R.id.btnRegistration);
        button.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_registration));
    }

    private void setupLoginButton(View view) {
        Button button = view.findViewById(R.id.btnLogin);
        button.setOnClickListener(v -> {
            mLoginViewModel.makeLogin(textInputEmail.getText(), textInputPassword.getText());
        });
    }

    private void observeLogin() {
        mLoginViewModel.isSuccessLogin().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess){
                Log.v(TAG, "Login success");
                successInteraction.onLoginSuccess();
            }
        });
    }

    private void observeErrors(){
        mLoginViewModel.getLoginErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputEmail.showError(errorMsg);
        });

        mLoginViewModel.getPasswordErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            textInputPassword.showError(errorMsg);
        });

        mLoginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel));
        });

    }

    public interface OnLoginSuccessInteraction{
        public void onLoginSuccess();
    }

}
