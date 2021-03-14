package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.utils.Utils;

public class BaseInputView extends LinearLayout {

    protected TextView tvTitle;
    protected EditText edInput;
    protected TextView tvSubtitle;
    protected ImageView icon;

    protected String inputTitle = "";
    private String inputHint = "";
    private String inputSubtitle = "";
    private int inputLength = 0;
    private int inputType = InputType.TYPE_CLASS_TEXT;

    public BaseInputView(Context context) {
        super(context);
    }

    public BaseInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BaseInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_input_layout, this, true);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BaseInputView, 0, 0);

        if (typedArray.hasValue(R.styleable.BaseInputView_inputTitle)){
            inputTitle = typedArray.getString(R.styleable.BaseInputView_inputTitle);
        }

        if (typedArray.hasValue(R.styleable.BaseInputView_inputSubtitle)){
            inputSubtitle = typedArray.getString(R.styleable.BaseInputView_inputSubtitle);
        }

        if (typedArray.hasValue(R.styleable.BaseInputView_inputHint)){
            inputHint = typedArray.getString(R.styleable.BaseInputView_inputHint);
        }

        if (typedArray.hasValue(R.styleable.BaseInputView_inputLength)){
            inputLength = typedArray.getInt(R.styleable.BaseInputView_inputLength, 0);
        }

        if (typedArray.hasValue(R.styleable.BaseInputView_android_inputType)){
            inputType = typedArray.getInt(R.styleable.BaseInputView_android_inputType, InputType.TYPE_CLASS_TEXT);
        }

        initView();
    }

    protected void initView(){

        tvTitle = findViewById(R.id.tv_input_title);
        edInput = findViewById(R.id.edt_input);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        icon = findViewById(R.id.img_input);

        tvTitle.setText(inputTitle);
        edInput.setHint(inputHint);

        if (inputLength > 0){
            setMaxLength(inputLength);
        }

        setInputType(inputType);
    }

    public void setSubtitleText(int subtitleText){
        setSubtitleText(getContext().getString(subtitleText));
    }

    public void setSubtitleText(String subtitleText){
        this.inputSubtitle = subtitleText;
        tvSubtitle.setText(subtitleText);
        tvSubtitle.setVisibility(View.VISIBLE);
    }

    public void hideSubtitleText(){
        tvSubtitle.setVisibility(View.GONE);
    }

    public void showError(String msgError){
        if (msgError != null) {
            tvSubtitle.setText(msgError);
            tvSubtitle.setVisibility(View.VISIBLE);
            tvSubtitle.setTextColor(ContextCompat.getColor(getContext(), R.color.inputErrorColor));
            edInput.setBackgroundResource(R.drawable.input_error_background);
            tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.inputErrorColor));
            icon.setImageResource(R.drawable.ic_error_icon);
            icon.setVisibility(View.VISIBLE);
        } else {
            hideError();
        }
    }

    public void hideError(){
        edInput.setBackgroundResource(R.drawable.input_selector_background);
        tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor));

        if (inputSubtitle != null) {
            tvSubtitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textColor));
            tvSubtitle.setText(inputSubtitle);
        } else {
            tvSubtitle.setVisibility(View.GONE);
        }
        icon.setVisibility(View.GONE);
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    public void setText(String text) {
        edInput.setText(text);
    }

    public String getText() {
        return edInput.getText().toString().trim();
    }

    public void setInputSize(int size){
        this.edInput.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(size) });
    }

    public void setInputType(int inputType){
        edInput.setInputType(inputType);
    }

    public EditText getInputView(){
        return edInput;
    }

    public void setMaxLength(int maxLength){
        addInputFilter(new InputFilter.LengthFilter(maxLength));
    }

    public void addTextWatcher(TextWatcher textWatcher){
        edInput.addTextChangedListener(textWatcher);
    }

    public void addKeyListener(KeyListener listener){
        edInput.setKeyListener(listener);
    }

    private void addInputFilter(InputFilter filter){
        InputFilter[] fArray = edInput.getFilters();
        InputFilter[] newArray = new InputFilter[fArray.length+1];

        System.arraycopy(fArray, 0, newArray, 0, fArray.length);

        newArray[fArray.length] = filter;
        edInput.setFilters(newArray);
    }

    public void setFocus(){
        Utils.showKeyboard(edInput);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        edInput.setOnFocusChangeListener(l);
    }

}
