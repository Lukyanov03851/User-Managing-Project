package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import ua.lukyanov.usermanaging.R;

public class BaseInputView extends LinearLayout {

    protected Context context;

    protected TextView tvTitle;
    protected EditText input;
    protected TextView tooltip;
    protected ImageView icon;

    private String tooltipText = null;

    public BaseInputView(Context context) {
        super(context);
        this.context = context;
    }

    public BaseInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BaseInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    protected void initView(String title, String hint){
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_input_layout, this, true);
        tvTitle = findViewById(R.id.tv_input_title);
        input = findViewById(R.id.edt_input);
        tooltip = findViewById(R.id.tvTooltip);
        icon = findViewById(R.id.img_input);

        tvTitle.setText(title);
        input.setHint(hint);
    }

    public void setTooltipText(int tooltipTextRes){
        setTooltipText(context.getString(tooltipTextRes));
    }

    public void setTooltipText(String tooltipText){
        this.tooltipText = tooltipText;
        tooltip.setText(tooltipText);
        tooltip.setVisibility(View.VISIBLE);
    }

    public void hideTooltipText(){
        tooltip.setVisibility(View.GONE);
    }

    public void showError(String msgError){
        if (msgError != null) {
            tooltip.setText(msgError);
            tooltip.setVisibility(View.VISIBLE);
            tooltip.setTextColor(ContextCompat.getColor(context, R.color.inputErrorColor));
            input.setBackgroundResource(R.drawable.input_error_background);
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.inputErrorColor));
            icon.setImageResource(R.drawable.ic_error_icon);
            icon.setVisibility(View.VISIBLE);
        } else {
            hideError();
        }
    }

    public void hideError(){
        input.setBackgroundResource(R.drawable.input_selector_background);
        tvTitle.setTextColor(ContextCompat.getColor(context, R.color.textColor));

        if (tooltipText != null) {
            tooltip.setTextColor(ContextCompat.getColor(context, R.color.textColor));
            tooltip.setText(tooltipText);
        } else {
            tooltip.setVisibility(View.GONE);
        }
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    public void setText(String text) {
        input.setText(text);
    }

    public String getText() {
        return input.getText().toString().trim();
    }

    public void setInputSize(int size){
        this.input.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(size) });
    }

    public void setInputType(int inputType){
        input.setInputType(inputType);
    }

    public EditText getInputView(){
        return input;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        input.setOnFocusChangeListener(l);
    }

}
