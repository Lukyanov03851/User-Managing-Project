package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import ua.lukyanov.usermanaging.R;

public class PasswordInputView extends BaseInputView {

    private static final String TAG = "PhoneInputView";
    private static final String REGEX_NON_DIGIT = "\\D";
    private static final String EMPTY_STRING = "";

    private final int MAX_LENGTH = 17;
    protected String countryCode;
    protected Context context;
    private String tooltipText = null;
    private boolean isPasswordShow = false;

    public PasswordInputView(Context context) {
        super(context);
        this.context = context;
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void initView() {
        initView(context.getString(R.string.password), "");

        icon.setClickable(true);
        icon.setImageResource(R.drawable.ic_eye_closed);
        icon.setVisibility(View.VISIBLE);

        icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isPasswordShow = !isPasswordShow;
                    int cursor = input.getSelectionEnd();
                    if(isPasswordShow){
                        showPassword();
                    }else{
                        hidePassword();
                    }
                    input.setSelection(cursor);
                }
        });

        input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(MAX_LENGTH) });
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void showPassword(){
        icon.setImageResource(R.drawable.ic_eye_opened);
        input.setTransformationMethod(null);
    }

    public void  hidePassword(){
        icon.setImageResource(R.drawable.ic_eye_closed);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void hideError(){
        super.hideError();
        if(isPasswordShow){
            showPassword();
        }else{
            hidePassword();
        }
    }


}