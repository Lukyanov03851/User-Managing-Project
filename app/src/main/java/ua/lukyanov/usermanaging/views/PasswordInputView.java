package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;

import ua.lukyanov.usermanaging.R;

public class PasswordInputView extends BaseInputView {

    private static final String TAG = "PasswordInputView";

    private static final int MAX_LENGTH = 17;
    private boolean isPasswordShow = false;

    public PasswordInputView(Context context) {
        super(context);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();

        icon.setClickable(true);
        icon.setImageResource(R.drawable.ic_eye_closed);
        icon.setVisibility(View.VISIBLE);

        icon.setOnClickListener(v -> {
            isPasswordShow = !isPasswordShow;
            int cursor = edInput.getSelectionEnd();
            if(isPasswordShow){
                showPassword();
            }else{
                hidePassword();
            }
            edInput.setSelection(cursor);
        });

        edInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edInput.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(MAX_LENGTH) });
        edInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void showPassword(){
        icon.setImageResource(R.drawable.ic_eye_opened);
        edInput.setTransformationMethod(null);
    }

    public void  hidePassword(){
        icon.setImageResource(R.drawable.ic_eye_closed);
        edInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void hideError(){
        super.hideError();
        if(isPasswordShow){
            showPassword();
        }else{
            hidePassword();
        }
        icon.setVisibility(View.VISIBLE);
    }
}