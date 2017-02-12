package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by charafau on 2017/02/12.
 */

public class MessageFragment extends BaseFragment {

    public static final String TAG = MessageFragment.class.getSimpleName();
    public static final String ARG_TEAM = "arg_team";
    @Inject
    MessageViewModel messageViewModel;
    @BindView(R.id.recycler_messages)
    RecyclerView recyclerMessages;
    @BindView(R.id.progress)
    ProgressBar progress;
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
        adapter = new MessageAdapter(new ArrayList<>());
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


        private List<Message> messages;

        public MessageAdapter(List<Message> messages) {
            this.messages = messages;
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false));
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            final Message message = messages.get(position);
            holder.txtMessage.setText(message.getText());
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

        @BindView(R.id.txt_message)
        TextView txtMessage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
