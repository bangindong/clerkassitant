package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.MessAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.customviews.CustomSeekbar;
import nguyengquanghuy.mssv20141973.clerk_assitant.customviews.DialogChooseTypeVoice;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnAddMessListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAndEditConversationFragment extends BaseFragment implements OnAddMessListener {

    @BindView(R.id.sbAudio)
    CustomSeekbar sbAudio;
    @BindView(R.id.btnPlayPause)
    ImageButton btnPlayPause;
    @BindView(R.id.btnChoose)
    Button btnChoose;
    @BindView(R.id.rcvMesses)
    RecyclerView rcvMesses;

    private boolean peopleChange = false;
    private boolean playing = false;
    private String audioUrl;
    private MediaPlayer player;
    private Handler threadHandler = new Handler();
    private int startTimeChoose;
    private int endTimeChoose;
    private DialogChooseTypeVoice dialog;
    private MessAdapter messAdapter = new MessAdapter();
    private List<Messenger> messes = new ArrayList<>();
    private String timeStartMeeting;

    public static BaseFragment newInstance(String audioUrl, List<Messenger> messes, String dateTime) {
        Bundle args = new Bundle();
        AddAndEditConversationFragment fragment = new AddAndEditConversationFragment();
        fragment.audioUrl = audioUrl;
        fragment.messes = messes;
        fragment.timeStartMeeting = dateTime;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_add_and_edit_conversation;
    }

    @Override
    void initView(View view) {
        setTitle("Chỉnh sửa cuộc họp");
        setupAudio();
        createThreadAudio();
        settingRecycleView();
    }

    private void settingRecycleView() {
        rcvMesses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messAdapter.setData(messes);
        rcvMesses.setAdapter(messAdapter);
    }

    @Override
    void onViewDisappear() {
        if (player != null) {
            player.pause();
            player.reset();
        }
    }

    private void setupDialog() {
        if (getContext() != null) {
            dialog = new DialogChooseTypeVoice(getContext());
            dialog.setOnAddMessListener(this);
        }
    }

    private void createThreadAudio() {
        UpdateSeekBarThread updateSeekBarThread = new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread, 50);
    }

    private void setupAudio() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(audioUrl);
            player.prepare(); // might take long! (for buffering, etc)
            int duration = player.getDuration();
            sbAudio.setMax(duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sbAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (peopleChange) {
                    player.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                peopleChange = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                peopleChange = false;
            }
        });
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.e("onError: ", "dfdfd");
                return false;
            }
        });
    }

    @OnClick(R.id.btnPlayPause)
    void onClickButtonPlayPause() {
        playing = !playing;
        Log.e("ButtonPlayPause: ", String.valueOf(playing));
        if (playing) {
            btnPlayPause.setImageResource(R.drawable.icon_pause);
            player.start();
        } else {
            btnPlayPause.setImageResource(R.drawable.icon_play);
            player.pause();
        }
    }

    @OnClick(R.id.btnChoose)
    void onClickButtonChoose() {
        btnChoose.setSelected(!btnChoose.isSelected());
        if (btnChoose.isSelected()) {
            btnChoose.setText(getString(R.string.add_and_edit_stop_choose));
            startTimeChoose = player.getCurrentPosition();
            sbAudio.setStartChooseProgress(player.getCurrentPosition());
        } else {
            btnChoose.setText(getString(R.string.add_and_edit_choose));
            sbAudio.setStopChooseProgress();
            endTimeChoose = player.getCurrentPosition();
            if (startTimeChoose > endTimeChoose) {
                Toast.makeText(getContext(), "Thời gian bắt đầu của bạn phải sớm hơn thời gian kết thúc", Toast.LENGTH_LONG).show();
            } else {
                setupDialog();
                dialog.setTime(startTimeChoose, endTimeChoose);
                //TODO dialog add mess
//                dialog.show();
            }
            if (playing) {
                onClickButtonPlayPause();
            }
        }
    }

    @Override
    public void onAddSingleItem(Messenger mess) {
        Log.e("onAddSingleItem: ", mess.toString());
        messAdapter.add(mess);
    }

    @Override
    public void onAddGroupItem(List<Messenger> messes) {
        for (Messenger mess :
                messes) {
            messAdapter.add(mess);
        }
    }

    @OnClick(R.id.btnEdited)
    void onBtnEditedClick() {
        if (messAdapter.getPositionChange().size() != 0) {
            for (int i = 0; i < messAdapter.getPositionChange().size(); i++) {
                final int finalI = i;
                AppClient.getService().changeMess(messAdapter.getItemData(i).getId(), new Gson().toJson(messAdapter.getItemData(i)))
                        .enqueue(new Callback<Messenger>() {
                            @Override
                            public void onResponse(@NonNull Call<Messenger> call, @NonNull Response<Messenger> response) {
                                if (finalI == messAdapter.getPositionChange().size() - 1) {
                                    onBackPressd();
                                }
                                try {
                                    if (response.body() != null) {
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<Messenger> call, Throwable t) {

                            }
                        });
            }
        }
    }

    // Thread sử dụng để Update trạng thái cho SeekBar.
    class UpdateSeekBarThread implements Runnable {

        public void run() {
            if (playing) {
                int currentPosition = player.getCurrentPosition();

                sbAudio.setProgress(currentPosition);
                sbAudio.setCurrentProgress(currentPosition);
            }
            // Ngừng thread 50 mili giây.
            threadHandler.postDelayed(this, 50);
        }
    }
}
