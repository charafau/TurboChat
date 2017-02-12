package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import com.nullpointerbay.turbochat.parsers.LinkParser;
import com.nullpointerbay.turbochat.parsers.MentionParser;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

        String message = "Good morning! (megusta) (coffee) here is" +
                " some link\n https://www.youtube.com/watch?v=7Ky6ZaodBkU&t=2473s \nshould " +
                "be highlighted and @alex is nice";

        List<String> emojiList = new ArrayList<>();
        emojiList.add("megusta");
        emojiList.add("dummy");
        emojiList.add("coffee");
        final SpannableString spannableString = new SpannableString(message);

        final EmojiParser emojiParser = new EmojiParser(getContext(), emojiList, "emoji", (int) (-txtMessage.getPaint().ascent()));
        final LinkParser linkParser = new LinkParser(getContext());
        final MentionParser mentionParser = new MentionParser(getContext());
        emojiParser.insert(spannableString);
        linkParser.insert(spannableString);
        mentionParser.insert(spannableString);

        teamViewModel.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teams -> {
                            Log.d(TAG, "got teams " + teams.toString());
                        },
                        throwable -> Log.e(TAG, "" + throwable.getMessage())
                );


        txtMessage.setText(spannableString);
        txtMessage.setMovementMethod(LinkMovementMethod.getInstance());


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