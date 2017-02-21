package com.nullpointerbay.turbochat.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
    @BindView(R.id.img_emoji)
    ImageButton imgEmoji;
    @BindView(R.id.frame_emojis)
    FrameLayout frameEmojis;
    @BindView(R.id.edit_comment)
    EditText editComment;
    private Team team;
    private MessageAdapter adapter;
    private EmojiFragment emojiFragment;
    private List<String> pressedEmojis = new ArrayList<>();

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
        initMessagesRecyclerView();
        inflateEmojiFragment();

        return view;
    }

    private void initMessagesRecyclerView() {
        recyclerMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMessages.setHasFixedSize(true);
        adapter = new MessageAdapter(new ArrayList<>(), imageLoader);
        recyclerMessages.setAdapter(adapter);
    }

    private void inflateEmojiFragment() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        emojiFragment = EmojiFragment.createInstance(team);
        ft.replace(R.id.frame_emojis, emojiFragment, EmojiFragment.TAG);
        ft.commit();
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
                                    Timber.d("adding messages");
                                    progress.setVisibility(View.GONE);
                                    adapter.addAll(messages);
                                    recyclerMessages.scrollToPosition(adapter.getItemCount() - 1);
                                },
                                throwable -> Timber.e("" + throwable.getMessage())
                        )
        );

        compositeDisposable.add(
                messageViewModel.localMessageStream()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                message -> {
                                    adapter.addMessage(message);
                                    adapter.notifyItemInserted(adapter.getItemCount() - 1);
                                    recyclerMessages.scrollToPosition(adapter.getItemCount() - 1);
                                },
                                throwable -> {
                                }
                        )
        );

        compositeDisposable.add(messageViewModel.apiSendMessageStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        message -> updateMessage(message),
                        throwable -> Timber.e(throwable)
                ));

        if (emojiFragment != null) {
            compositeDisposable.add(emojiFragment.getEmojiOnClick()
                    .subscribe(emojiText -> insertEmoji(emojiText))
            );
        }

        final int height = frameEmojis.getHeight();
        imgEmoji.setOnClickListener(view -> {
            hideKeyboard(view);
            if (frameEmojis.getVisibility() == View.GONE) {
                frameEmojis.animate()
                        .translationY(-height)
                        .alpha(0.0f)
                        .setDuration(100)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                frameEmojis.setVisibility(View.VISIBLE);
                                recyclerMessages.smoothScrollToPosition(recyclerMessages.getAdapter().getItemCount());
                            }
                        });
            }

        });

        editComment.setOnFocusChangeListener((view, hasFocus) -> onOpenKeyboard());

        editComment.setOnClickListener(view -> onOpenKeyboard());
        editComment.requestFocus();

        editComment.setOnEditorActionListener((textView, actionId, keyEvent) -> {

            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                messageViewModel.sendMessage(textView.getText().toString(), pressedEmojis,
                        Schedulers.io());
                handled = true;
                editComment.setText("");
            }
            return handled;
        });
    }

    private void updateMessage(Message message) {
        Timber.d("update message %s", message);
        final List<Message> messages = adapter.messages;
        for (int i = 0; i < messages.size(); i++) {
            final Message m = messages.get(i);
            if (m.getUser().equals(message.getUser()) && m.getText().equals(message.getText())) {
                adapter.changeMessage(i, message);
                adapter.notifyItemChanged(i);
            }
        }
    }

    private void onOpenKeyboard() {
        editComment.post(() -> {
            frameEmojis.setVisibility(View.GONE);
            recyclerMessages.smoothScrollToPosition(recyclerMessages.getAdapter().getItemCount());
        });
    }

    private void insertEmoji(String emojiText) {
        pressedEmojis.add(emojiText);
        String insertEmoji = String.format(" (%s) ", emojiText);
        final int selectionStart = editComment.getSelectionStart();
        final Editable text = editComment.getText();
        final Editable insertedText = text.insert(selectionStart, insertEmoji);
        editComment.setText(insertedText);
        editComment.setSelection(editComment.length());
    }

    private void hideKeyboard(View view) {
        final View v = getActivity().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
                    (int) (holder.txtMessage.getPaint().getTextSize()));
            emoji.insert(spannableString);
            linkParser.insert(spannableString);
            mentionParser.insert(spannableString);
            holder.txtMessage.setText(spannableString);
            holder.txtMessage.setMovementMethod(LinkMovementMethod.getInstance());
            imageLoader.loadImageWithCircleTransformation(context, message.getUser().getAvatarUrl(), holder.imgAvatar);
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

        public void addMessage(Message message) {
            messages.add(message);
        }

        public void changeMessage(int position, Message message) {
            messages.set(position, message);
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
