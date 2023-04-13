package kaerushi.weeabooify.uwuify.ui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class VBHelpers {

    public static void setDrawable(LinearLayout linearLayout, Drawable drawable) {
        Glide.with(linearLayout.getContext()).load(drawable).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                linearLayout.setBackground(resource);
            }
        });
    }

    public static void setHeader(Context context, CollapsingToolbarLayout collapsing_toolbar, Toolbar toolbar, int title) {
        collapsing_toolbar.setTitle(context.getResources().getString(title));
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) context).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
