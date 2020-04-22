package ua.lukyanov.usermanaging.ui;

import android.app.AlertDialog;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected void showMessageDialog(String title, String message, String buttonText){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (title != null){
            builder.setTitle(title);
        }
        builder.setMessage(message)
                .setPositiveButton(buttonText, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
