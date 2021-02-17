package ua.lukyanov.usermanaging.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

import ua.lukyanov.usermanaging.views.BaseInputView;

public class BindingsAdapters {

    @BindingAdapter({"app:inputError"})
    public static void inputError(BaseInputView view, String errorMsg) {
        view.showError(errorMsg);
    }

    @BindingAdapter({"app:goneUnless"})
    public static void goneUnless(View view, boolean isVisible) {
        if (isVisible){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
