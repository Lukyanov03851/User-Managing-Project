package ua.lukyanov.usermanaging.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.models.UserProperty;

public class UserPropertiesAdapter extends RecyclerView.Adapter<UserPropertiesAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvPropertyTitle;
        private final TextView tvPropertyValue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPropertyTitle = itemView.findViewById(R.id.tvTitle);
            tvPropertyValue = itemView.findViewById(R.id.tvValue);
        }

        void bind(UserProperty property) {
            tvPropertyTitle.setText(property.getTitle());
            tvPropertyValue.setText(property.getValue());
        }
    }

    private final ArrayList<UserProperty> properties = new ArrayList<>(Arrays.asList(
            new UserProperty("Phone", "+38 096 489 62 73"),
            new UserProperty("Email", "test_user_mail@gmail.com"),
            new UserProperty("Password", "*********"),
            new UserProperty("Age", "29")
    ));

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_property, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(properties.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public void setProperties(ArrayList<UserProperty> properties) {
        this.properties.clear();
        this.properties.addAll(properties);
        notifyDataSetChanged();
    }


}
