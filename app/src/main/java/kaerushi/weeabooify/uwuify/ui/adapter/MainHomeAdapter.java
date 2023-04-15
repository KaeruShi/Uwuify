package kaerushi.weeabooify.uwuify.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.ui.activity.Extras;
import kaerushi.weeabooify.uwuify.ui.activity.Info;
import kaerushi.weeabooify.uwuify.ui.activity.NotificationStyle;
import kaerushi.weeabooify.uwuify.ui.activity.QsStyle;
import kaerushi.weeabooify.uwuify.ui.models.MainHomeModel;

public class MainHomeAdapter extends RecyclerView.Adapter<MainHomeAdapter.ViewHolder> {

    Context context;
    ArrayList<MainHomeModel> itemList;
    LinearLayoutManager linearLayoutManager;

    public MainHomeAdapter(Context context, ArrayList<MainHomeModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_option_main_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.main_icon.setImageResource(itemList.get(position).getIcon());
        holder.main_title.setText(itemList.get(position).getTitle());
        holder.main_desc.setText(itemList.get(position).getDesc());

        switch (position) {
            case 0:
                holder.container.setCardBackgroundColor(holder.view.getResources().getColor(R.color.color_a, null));
                break;
            case 1:
                holder.container.setCardBackgroundColor(holder.view.getResources().getColor(R.color.color_b, null));
                break;
            case 2:
                holder.container.setCardBackgroundColor(holder.view.getResources().getColor(R.color.color_c, null));
                break;
            case 3:
                holder.container.setCardBackgroundColor(holder.view.getResources().getColor(R.color.color_d, null));
                break;
            case 4:
                holder.container.setCardBackgroundColor(holder.view.getResources().getColor(R.color.color_b, null));
                break;
        }

        holder.container.setOnClickListener(view -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(context, QsStyle.class);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(context, NotificationStyle.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(context, Extras.class);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(context, Info.class);
                    context.startActivity(intent);
                    break;
            }
//            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show();
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color_a);
        colorCode.add(R.color.color_b);
        colorCode.add(R.color.color_c);
        colorCode.add(R.color.color_d);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return  colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView container;
        TextView main_title, main_desc;
        ImageView main_icon;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.main_child);
            main_icon = itemView.findViewById(R.id.main_icon);
            main_title = itemView.findViewById(R.id.main_title);
            main_desc = itemView.findViewById(R.id.main_desc);
            view = itemView;
        }
    }
}
