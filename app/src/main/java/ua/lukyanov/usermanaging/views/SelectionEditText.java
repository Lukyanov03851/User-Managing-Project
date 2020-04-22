package ua.lukyanov.usermanaging.views;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class SelectionEditText extends AppCompatEditText {

    public int minCursorPosition = 0;

    public SelectionEditText(Context context) {
        super(context);
    }

    public SelectionEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd){
            if (selStart < minCursorPosition && getText().length() > minCursorPosition){
                setSelection(minCursorPosition);
            }
        }
    }

    public void setMinPosition(int minPosition){
        minCursorPosition = minPosition;
    }
}
