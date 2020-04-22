package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import ua.lukyanov.usermanaging.R;

public class TextInputView extends BaseInputView {

    public TextInputView(Context context) {
        super(context);
        this.context = context;
    }

    public TextInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void initView(String title, String hint, Integer type, boolean multiline){
        super.initView(title, hint);

        if (type == null){
            type = InputType.TYPE_CLASS_TEXT;
        }

        if (multiline){
            input.setSingleLine(false);
            input.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            input.setMinHeight(50);

            ViewGroup.LayoutParams params = input.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            input.setLayoutParams(params);

            requestLayout();
        }else{
            setInputType(type);
        }
    }

    public void setMaxLength(int maxLength){
        addInputFilter(new InputFilter.LengthFilter(maxLength));
    }

    public void addTextWatcher(TextWatcher textWatcher){
        input.addTextChangedListener(textWatcher);
    }

    public void addKeyListener(KeyListener listener){
        input.setKeyListener(listener);
    }

    private void addInputFilter(InputFilter filter){
        InputFilter[] fArray = input.getFilters();
        InputFilter[] newArray = new InputFilter[fArray.length+1];

        System.arraycopy(fArray, 0, newArray, 0, fArray.length);

        newArray[fArray.length] = filter;
        input.setFilters(newArray);
    }

    @Override
    public void hideError(){
        super.hideError();
        icon.setVisibility(View.GONE);
    }
}
