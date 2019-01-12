package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.LoginActivity;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppService;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.imgAvar)
    ImageView imgAvar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMail)
    TextView tvMail;
    @BindView(R.id.tvOffice)
    TextView tvOffice;
    @BindView(R.id.tvPosition)
    TextView tvPosition;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.viewLogOut)
    View viewLogOut;
    @BindView(R.id.fileRecord1)
    ImageButton fileRecord1;
    @BindView(R.id.fileRecord2)
    ImageButton fileRecord2;
    @BindView(R.id.fileRecord3)
    ImageButton fileRecord3;
    @BindView(R.id.fileRecord4)
    ImageButton fileRecord4;
    @BindView(R.id.fileRecord5)
    ImageButton fileRecord5;
    @BindView(R.id.fileRecord6)
    ImageButton fileRecord6;
    private ImageButton[] tagButtonFileRecords = new ImageButton[6];
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private User user = new User();

    public static BaseFragment newInstance(User user) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.user = user;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    void initView(View view) {
        checkCanLogOut();
        addListFileRecord();
        setUpListFileRecord();
        upDateInformation();
    }

    private void setUpListFileRecord() {
        for (int i = 0; i < user.getSounds().size(); i++) {
            final int finalI = i;
            tagButtonFileRecords[i].setVisibility(View.VISIBLE);
            tagButtonFileRecords[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(user.getSounds().get(finalI));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Toast.makeText(getContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        // make something
                    }
                }
            });
        }
    }

    private void checkCanLogOut() {
        if (!user.getId().equals(AppConstant.getMainUsser().getId())) {
            viewLogOut.setVisibility(View.GONE);
        } else {
            viewLogOut.setVisibility(View.VISIBLE);
        }
    }

    private void addListFileRecord() {
        tagButtonFileRecords[0] = fileRecord1;
        tagButtonFileRecords[1] = fileRecord2;
        tagButtonFileRecords[2] = fileRecord3;
        tagButtonFileRecords[3] = fileRecord4;
        tagButtonFileRecords[4] = fileRecord5;
        tagButtonFileRecords[5] = fileRecord6;
    }


    private void upDateInformation() {
        if (getContext() != null) {
            Glide.with(getContext())
                    .load(user.getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvar);
        }
        tvName.setText(user.getName());
        tvMail.setText(user.getEmail());
        tvOffice.setText(user.getPart());
        tvPosition.setText(user.getPosition());
        tvPhoneNumber.setText(user.getPhone());
    }

    @OnClick(R.id.lnLogOut)
    public void logOutClick() {
        if (getActivity() != null) {
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    @Override
    void onViewDisappear() {
        mediaPlayer.pause();
        mediaPlayer.reset();
    }
}
