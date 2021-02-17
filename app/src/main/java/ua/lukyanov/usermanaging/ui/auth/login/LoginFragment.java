package ua.lukyanov.usermanaging.ui.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.data.prefs.AppPreferenceHelper;
import ua.lukyanov.usermanaging.databinding.FragmentLoginBinding;
import ua.lukyanov.usermanaging.ui.BaseFragment;

public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";

    @Inject
    ViewModelProvider.Factory factory;
    private LoginViewModel mLoginViewModel;
    private OnLoginSuccessInteraction successInteraction;

    FragmentLoginBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mLoginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setViewModel(mLoginViewModel);
        binding.setLifecycleOwner(this);

        setupInput();
        setupButtons();
        observeViewModel();

        return binding.getRoot();
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

    private void setupInput(){
        if (AppPreferenceHelper.containsLogin(getContext())){
            binding.inputLogin.setText(AppPreferenceHelper.getLogin(getContext()));
            binding.inputPassword.setFocus();
        }
    }

    private void setupButtons() {
        binding.btnRegistration.setOnClickListener(v ->
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registration));
        binding.btnLogin.setOnClickListener(v -> {
            mLoginViewModel.makeLogin(binding.inputLogin.getText(), binding.inputPassword.getText());
        });
    }

    private void observeViewModel() {

        mLoginViewModel.isSuccessLogin().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess){
                Log.v(TAG, "Login success");
                successInteraction.onLoginSuccess();
            }
        });

        mLoginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            showMessageDialog(getString(R.string.error), errorMsg, getString(R.string.cancel));
        });
    }

    public interface OnLoginSuccessInteraction{
        public void onLoginSuccess();
    }

}
