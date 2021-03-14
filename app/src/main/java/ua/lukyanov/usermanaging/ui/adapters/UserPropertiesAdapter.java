package ua.lukyanov.usermanaging.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ua.lukyanov.usermanaging.R;
import ua.lukyanov.usermanaging.model.UserProperty;

public class UserPropertiesAdapter extends RecyclerView.Adapter<UserPropertiesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvPropertyTitle;
        private final TextView tvPropertyValue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPropertyTitle = itemView.findViewById(R.id.tvTitle);
            tvPropertyValue = itemView.findViewById(R.id.tvValue);
        }

        void bind(UserProperty property) {
            tvPropertyTitle.setText(property.getTitle());
            if (property.getValue() != null) {
                tvPropertyValue.setText(property.getValue());
            }
        }
    }

    private final ArrayList<UserProperty> properties = new ArrayList<>();

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

    public void setProperties(List<UserProperty> properties) {
        this.properties.clear();
        this.properties.addAll(properties);
        notifyDataSetChanged();
    }


}
