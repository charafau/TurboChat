package com.nullpointerbay.turbochat.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamFragment extends BaseFragment {

    public static final String TAG = TeamFragment.class.getSimpleName();

    @Inject
    TeamViewModel teamViewModel;
    @BindView(R.id.txt_message)
    TextView txtMessage;

    public static TeamFragment createInstance() {
        return new TeamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team, container, false);
        ButterKnife.bind(this, view);

        teamViewModel.test();

        String message = "Good morning! (megusta) (coffee)";

        Pattern pattern = Pattern.compile("\\(megusta\\)");
        Matcher matcher = pattern.matcher(message);
        SpannableString spannableString
                = new SpannableString(message);

        Bitmap octopus = null;
        int size = (int) (-txtMessage.getPaint().ascent());
        while (matcher.find()) {
            if (octopus == null) {
                Bitmap bitmap = BitmapFactory.decodeResource(
                        getResources(), R.drawable.emoji_megusta);
                octopus = Bitmap.createScaledBitmap(
                        bitmap, size, size, true);
                bitmap.recycle();
            }
            ImageSpan span = new ImageSpan(getContext(), octopus, ImageSpan.ALIGN_BASELINE);
            spannableString.setSpan(
                    span, matcher.start(), matcher.end(), 0);
        }

        txtMessage.setText(spannableString);

        return view;
    }

    @Override
    protected void setup(TurboChatComponent component) {
        DaggerViewComponent.builder()
                .turboChatComponent(component)
                .viewModule(new ViewModule())
                .build()
                .inject(this);

    }
}
