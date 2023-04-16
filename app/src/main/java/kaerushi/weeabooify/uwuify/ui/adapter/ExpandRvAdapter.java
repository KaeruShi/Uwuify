package kaerushi.weeabooify.uwuify.ui.adapter;

import static kaerushi.weeabooify.uwuify.ui.utils.VBHelpers.setDrawable;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kaerushi.weeabooify.uwuify.R;
import kaerushi.weeabooify.uwuify.Weeabooify;
import kaerushi.weeabooify.uwuify.config.Prefs;
import kaerushi.weeabooify.uwuify.installer.NotifStyleInstaller;
import kaerushi.weeabooify.uwuify.ui.models.ExpandRVModel;
import kaerushi.weeabooify.uwuify.ui.view.LoadingDialog;

public class ExpandRvAdapter extends RecyclerView.Adapter<ExpandRvAdapter.ViewHolder> {
    Context context;
    private ArrayList<ExpandRVModel> itemList;
    ArrayList<String> NOTIFICATION_KEY = new ArrayList<>();
    LoadingDialog loadingDialog;
    private final SparseBooleanArray mExpandedItems;

    public ExpandRvAdapter(Context context, ArrayList<ExpandRVModel> itemList, LoadingDialog loadingDialog) {
        this.context = context;
        this.itemList = itemList;
        this.loadingDialog = loadingDialog;
        mExpandedItems = new SparseBooleanArray();

        // Preference key
        for (int i = 1; i <= itemList.size(); i++)
            NOTIFICATION_KEY.add("UwuifyComponentNF" + i + ".overlay");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expand_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setDrawable(holder.container, ContextCompat.getDrawable(context, itemList.get(position).getBackground()));
        ExpandRVModel rvModel = itemList.get(position);
        holder.bind(rvModel);
        holder.style_name.setText(itemList.get(position).getName());

        if (Prefs.getBoolean(NOTIFICATION_KEY.get(position))) {
            holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
            holder.style_name.setTextColor(context.getResources().getColor(R.color.colorSuccess));
            holder.btn_enable.setVisibility(View.GONE);
            holder.btn_disable.setVisibility(View.VISIBLE);
        } else {
            holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
            holder.style_name.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
            holder.btn_enable.setVisibility(View.VISIBLE);
            holder.btn_disable.setVisibility(View.GONE);
        }
        
        enableOnClick(holder);
    }

    private void enableOnClick(ViewHolder holder) {
        // Set onClick operation for Enable button
        holder.btn_enable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(context.getResources().getString(R.string.loading_dialog_wait));

            @SuppressLint("SetTextI18n") Runnable runnable = () -> {
                NotifStyleInstaller.enableOverlay(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> {
                    new Handler().postDelayed(() -> {
                        // Hide loading dialog
                        loadingDialog.hide();

                        // Change name to " - applied"
                        holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), "") + ' ' + context.getResources().getString(R.string.opt_applied));
                        holder.style_name.setTextColor(context.getResources().getColor(R.color.colorSuccess));

                        // Change button visibility
                        holder.btn_enable.setVisibility(View.GONE);
                        holder.btn_disable.setVisibility(View.VISIBLE);

                        Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_applied), Toast.LENGTH_SHORT).show();
                    }, 1000);
                });
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });

        // Set onClick operation for Disable button
        holder.btn_disable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(context.getResources().getString(R.string.loading_dialog_wait));

            Runnable runnable = () -> {
                NotifStyleInstaller.disable_pack(holder.getBindingAdapterPosition() + 1);

                ((Activity) context).runOnUiThread(() -> {
                    new Handler().postDelayed(() -> {
                        // Hide loading dialog
                        loadingDialog.hide();

                        // Change name back to original
                        holder.style_name.setText(holder.style_name.getText().toString().replace(' ' + context.getResources().getString(R.string.opt_applied), ""));
                        holder.style_name.setTextColor(context.getResources().getColor(R.color.textColorPrimary));

                        // Change button visibility
                        holder.btn_disable.setVisibility(View.GONE);
                        holder.btn_enable.setVisibility(View.VISIBLE);

                        Toast.makeText(Weeabooify.getAppContext(), context.getResources().getString(R.string.toast_disabled), Toast.LENGTH_SHORT).show();
                    }, 1000);
                });
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView style_name;
        LinearLayout expand_view, container;
        ImageView arrow;
        Button btn_enable, btn_disable;
        private boolean isExpanded = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            style_name = itemView.findViewById(R.id.rv_title);
            container = itemView.findViewById(R.id.rv_child);
            expand_view = itemView.findViewById(R.id.expand_view);
            arrow = itemView.findViewById(R.id.rv_arrow);
            btn_enable = itemView.findViewById(R.id.enable_rv);
            btn_disable = itemView.findViewById(R.id.disable_rv);

            itemView.setOnClickListener(view -> {
                boolean isExpanded = mExpandedItems.get(getAdapterPosition());
                // Collapse all other items except for the current item
                for (int i = 0; i < mExpandedItems.size(); i++) {
                    int key = mExpandedItems.keyAt(i);
                    if (key != getAdapterPosition() && mExpandedItems.get(key)) {
                        mExpandedItems.put(key, false);
                        notifyItemChanged(key);
                    }
                }
                // Toggle the state of the current item
                mExpandedItems.put(getAdapterPosition(), !isExpanded);
                ExpandRVModel rvModel = itemList.get(getAbsoluteAdapterPosition());
                animateExpandCollapse();
                notifyItemChanged(getAdapterPosition());
            });
        }

        private void animateExpandCollapse() {
            if (mExpandedItems.get(getAdapterPosition())) {
                arrow.setAlpha(0);
                rotateIcon(180);
            } else {
                arrow.setAlpha(0);
                rotateIcon(0);
            }
        }

        public void bind(ExpandRVModel rvModel) {
            style_name.setText(rvModel.getName());

            if (mExpandedItems.get(getAdapterPosition())) {
                rotateIcon(180);
                expand_view.setVisibility(View.VISIBLE);
            } else {
                rotateIcon(0);
                expand_view.setVisibility(View.GONE);
            }
        }

        private void rotateIcon(float degrees) {
            ObjectAnimator rotate = ObjectAnimator.ofFloat(arrow, View.ROTATION, degrees);
            rotate.setDuration(300).start();
        }
    }
}
