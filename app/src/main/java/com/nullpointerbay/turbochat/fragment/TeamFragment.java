package com.nullpointerbay.turbochat.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.activity.MessageActivity;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.utils.UrlResolver;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class TeamFragment extends BaseFragment {

    public static final String TAG = TeamFragment.class.getSimpleName();

    @Inject
    TeamViewModel teamViewModel;
    @BindView(R.id.recycler_teams)
    RecyclerView recyclerTeams;
    @BindView(R.id.progress)
    ProgressBar progress;
    private TeamAdapter adapter;


    public static TeamFragment createInstance() {
        return new TeamFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team, container, false);
        ButterKnife.bind(this, view);
        recyclerTeams.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerTeams.setHasFixedSize(true);
        adapter = new TeamAdapter(new ArrayList<>());
        recyclerTeams.setAdapter(adapter);

        final UrlResolver urlResolver = new UrlResolver();
        urlResolver.getLinkTitle("https://twitter.com/ThePracticalDev/status/820703750574718976")
                .subscribeOn(Schedulers.io())
                .subscribe(
                        message -> Timber.d("link: %s", message),
                        throwable -> Timber.e(throwable)
                );
        adapter.getPositionClick()
                .subscribe(team -> MessageActivity.start(getContext(), team));

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
        compositeDisposable.add(teamViewModel.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teams -> {
                            adapter.addAll(teams);
                            progress.setVisibility(View.GONE);
                        },
                        throwable -> Toast.makeText(getContext(), R.string.team_error_fetch, Toast.LENGTH_LONG))
        );
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder> {

        private final PublishSubject<Team> onClickSubject = PublishSubject.create();
        private List<Team> items;
        private Context context;

        public TeamAdapter(List<Team> items) {
            this.items = items;
        }

        @Override
        public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            return new TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false));
        }

        @Override
        public void onBindViewHolder(TeamViewHolder holder, int position) {
            final Team team = items.get(position);
            holder.txtTeamName.setText(team.getName());
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier(team.getPhotoUrl(), "drawable", context.getPackageName());

            holder.imgGroup.setImageResource(resourceId);
            holder.itemView.setOnClickListener(view -> onClickSubject.onNext(team));

        }

        public Observable<Team> getPositionClick() {
            return onClickSubject;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addAll(List<Team> teams) {
            items = teams;
            Timber.d("settings list teams " + teams.size());
            notifyDataSetChanged();
        }
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_team_name)
        TextView txtTeamName;
        @BindView(R.id.img_group)
        ImageView imgGroup;

        public TeamViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}