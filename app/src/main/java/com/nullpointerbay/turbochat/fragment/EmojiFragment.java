package com.nullpointerbay.turbochat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.viewmodel.EmojiViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by charafau on 2017/02/15.
 */

public class EmojiFragment extends BaseFragment {

    public static final String TAG = "EmojiFragment";
    public static final String ARG_TEAM = "arg_team";
    @BindView(R.id.recycler_emojis)
    RecyclerView recyclerEmojis;

    @Inject
    ImageLoader imageLoader;
    @Inject
    EmojiViewModel emojiViewModel;
    private EmojiAdapter adapter;
    private Team team;


    public static EmojiFragment createInstance(Team team) {
        final Bundle args = new Bundle();
        args.putParcelable(ARG_TEAM, team);
        final EmojiFragment emojiFragment = new EmojiFragment();
        emojiFragment.setArguments(args);
        return emojiFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        ButterKnife.bind(this, view);
        team = getArguments().getParcelable(ARG_TEAM);
        List<String> emojiList = new ArrayList<>();
        recyclerEmojis.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false));
        recyclerEmojis.setHasFixedSize(true);
        adapter = new EmojiAdapter(emojiList);
        recyclerEmojis.setAdapter(adapter);


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

    @Override
    protected void bind() {
        compositeDisposable.add(
                emojiViewModel.getEmojis(team)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                emojis -> adapter.addAll(emojis),
                                throwable -> {
                                }
                        )
        );
    }

    public Observable<String> getEmojiOnClick() {
        return adapter.getPositionClick();
    }

    private class EmojiAdapter extends RecyclerView.Adapter<EmojiViewHolder> {
        public static final String EMOJI_PREFIX = "emoji_%s";
        private final PublishSubject<String> onClickSubject = PublishSubject.create();
        private List<String> emojiList;
        private Context context;


        public EmojiAdapter(List<String> emojiList) {
            this.emojiList = emojiList;
        }

        @Override
        public EmojiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            final View view = LayoutInflater.from(context).inflate(R.layout.item_emoji, parent, false);

            return new EmojiViewHolder(view);

        }

        @Override
        public void onBindViewHolder(EmojiViewHolder holder, int position) {
            final String emojiUrl = emojiList.get(position);
            imageLoader.loadImage(context, String.format(EMOJI_PREFIX, emojiUrl), holder.imgEmoji);
            holder.itemView.setOnClickListener(view -> onClickSubject.onNext(emojiUrl));
        }

        public Observable<String> getPositionClick() {
            return onClickSubject;
        }

        @Override
        public int getItemCount() {
            return emojiList.size();
        }

        public void addAll(List<String> emojis) {
            emojiList = emojis;
            notifyDataSetChanged();
        }
    }

    class EmojiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_emoji)
        ImageView imgEmoji;

        public EmojiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
