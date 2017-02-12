package com.nullpointerbay.turbochat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.parsers.EmojiParser;
import com.nullpointerbay.turbochat.parsers.LinkParser;
import com.nullpointerbay.turbochat.parsers.MentionParser;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MessageFragment extends BaseFragment {

    public static final String TAG = MessageFragment.class.getSimpleName();
    public static final String ARG_TEAM = "arg_team";
    @Inject
    MessageViewModel messageViewModel;
    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.progress)
    ProgressBar progress;
    @Inject
    ImageLoader imageLoader;
    private Team team;
    private MessageAdapter adapter;

    public static MessageFragment createInstance(Team team) {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_TEAM, team);
        final MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);


        team = getArguments().getParcelable(ARG_TEAM);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMessages.setHasFixedSize(true);
        adapter = new MessageAdapter(new ArrayList<>(), imageLoader);
        recyclerMessages.setAdapter(adapter);


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
                messageViewModel.getMessages()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                messages -> {
                                    progress.setVisibility(View.GONE);
                                    adapter.addAll(messages);
                                },
                                throwable -> Timber.e("" + throwable.getMessage())
                        )
        );
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

        private final ImageLoader imageLoader;
        private List<Message> messages;
        private Context context;
        private LinkParser linkParser;
        private MentionParser mentionParser;

        public MessageAdapter(List<Message> messages, ImageLoader imageLoader) {
            this.messages = messages;

            this.imageLoader = imageLoader;
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            linkParser = new LinkParser(context);
            mentionParser = new MentionParser(context);
            return new MessageViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_message, parent, false));
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            final Message message = messages.get(position);
            final SpannableString spannableString = new SpannableString(message.getText());
            final EmojiParser emoji = new EmojiParser(context, message.getEmoticons(), "emoji",
                    (int) (-holder.txtMessage.getPaint().ascent()));
            emoji.insert(spannableString);
            linkParser.insert(spannableString);
            mentionParser.insert(spannableString);
            holder.txtMessage.setText(spannableString);
            holder.txtMessage.setMovementMethod(LinkMovementMethod.getInstance());
            imageLoader.loadImage(context, message.getUser().getAvatarUrl(), holder.imgAvatar);
            holder.txtUser.setText(message.getUser().getName());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public void addAll(List<Message> messages) {
            this.messages = messages;
            notifyDataSetChanged();
        }
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar)
        ImageView imgAvatar;
        @BindView(R.id.txt_user)
        TextView txtUser;
        @BindView(R.id.txt_message)
        TextView txtMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
