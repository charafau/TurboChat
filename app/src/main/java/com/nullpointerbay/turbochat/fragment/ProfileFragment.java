package com.nullpointerbay.turbochat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.base.BaseFragment;
import com.nullpointerbay.turbochat.di.DaggerViewComponent;
import com.nullpointerbay.turbochat.di.TurboChatComponent;
import com.nullpointerbay.turbochat.di.ViewModule;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.viewmodel.ProfileViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ProfileFragment extends BaseFragment {

    public static final String ARG_USER = "arg_user";
    public static final String TAG = ProfileFragment.class.getSimpleName();
    @Inject
    ProfileViewModel profileViewModel;
    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.txt_nick)
    TextView txtNick;
    @BindView(R.id.txt_name)
    TextView txtName;
    private String userNick;

    public static ProfileFragment createInstance(String user) {
        final Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        final ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        userNick = getArguments().getString(ARG_USER);

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
        compositeDisposable.add(profileViewModel.getUserByNick(userNick)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> bindUser(user),
                        throwable -> Timber.e("err %s", throwable.getMessage())
                ));
    }

    private void bindUser(User user) {
        this.txtName.setText(getResources().getString(R.string.profile_name, user.getName()));
        this.txtNick.setText(getResources().getString(R.string.profile_nick, user.getNick()));
        imageLoader.loadImage(getContext(), user.getAvatarUrl(), this.imgAvatar);
    }
}
