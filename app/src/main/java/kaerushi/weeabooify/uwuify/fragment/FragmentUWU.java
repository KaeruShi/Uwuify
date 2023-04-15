package kaerushi.weeabooify.uwuify.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kaerushi.weeabooify.uwuify.R;

public class FragmentUWU extends Fragment {
    TextView title, desc;
    Button btnVariant;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uwu, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.variant_title);
        desc = view.findViewById(R.id.variant_detail);
        btnVariant = view.findViewById(R.id.btnVariant);

        title.setText("Weeabooify\nUwU");
        desc.setText("Substratum for A10, Uwuify alternative but substratum version. Include Quick Settings Style.");
        btnVariant.setOnClickListener(view1 -> {
            String url = "https://t.me/weeabooify/233";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }
}
