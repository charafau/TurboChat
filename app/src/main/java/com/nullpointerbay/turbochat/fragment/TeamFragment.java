package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.parsers.EmojiParser;
import com.nullpointerbay.turbochat.spans.CustomLinkSpan;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;
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

        String message = "Good morning! (megusta) (coffee) here is" +
                " some link\n https://www.youtube.com/watch?v=7Ky6ZaodBkU&t=2473s \nshould " +
                "be highlighted and @alex is nice";

        List<String> emojiList = new ArrayList<>();
        emojiList.add("megusta");
        emojiList.add("dummy");
        emojiList.add("coffee");

        final EmojiParser emoji = new EmojiParser(getContext(), emojiList, "emoji");

        SpannableString spannableString = emoji.insertEmoji(message, (int) (-txtMessage.getPaint().ascent()));

        insertLinks(spannableString);
        insertMentions(spannableString);


        txtMessage.setText(spannableString);
        txtMessage.setMovementMethod(LinkMovementMethod.getInstance());


        return view;
    }

    private void insertLinks(SpannableString spannableString) {

        final Pattern pattern = Patterns.WEB_URL;
        final Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            final CustomLinkSpan customLinkSpan = new CustomLinkSpan(getContext(), "http://www.dug.net.pl");
            spannableString.setSpan(customLinkSpan, matcher.start(), matcher.end(), 0);
        }
    }

    private void insertMentions(SpannableString spannableString) {
        Pattern pattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        final Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()) {
            final CustomLinkSpan customLinkSpan = new CustomLinkSpan(getContext(), "http://www.dug.net.pl");
            spannableString.setSpan(customLinkSpan, matcher.start(), matcher.end(), 0);
        }
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