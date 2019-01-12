package nguyengquanghuy.mssv20141973.clerk_assitant.customviews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;

import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppRawData;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.AvaAddUserAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.MessAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnAddMessListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DialogChooseTypeVoice extends Dialog implements View.OnClickListener, OnItemClickListener {

    private ImageButton btnChoiceGroup;
    private ImageButton btnChoiceSingle;
    private Button btnChoiceNoise;
    private Button btnChoiceSilent;
    private View viewSingleChoice;
    private View viewGroupChoice;
    private SeekBar sbAudio;
    private Button btnOk;
    private Button btnCancel;
    private ImageButton btnPlayPause;
    private RecyclerView rcvMesses;
    private RecyclerView rcvAva;
    private Button btnAddMess;
    private int typeMess;
    private OnAddMessListener onAddMessListener;
    private ImageView imgAvaAddSingleChoice;

    private boolean peopleChange = false;
    private MediaPlayer player = new MediaPlayer();
    private Handler threadHandler = new Handler();
    private boolean playing = false;
    private MessAdapter messAdapter;
    private AvaAddUserAdapter avaAddUserAdapter;
    private Messenger messSingle = new Messenger();
    private EditText edtSingleChoice;
    private TextView tvNameSingleChoice;
    private TextView tvTimeSingleChoice;

    private int timeStart;
    private int timeEnd;
    private Context context;
    private int buttonChosing = -1;

    public DialogChooseTypeVoice(@NonNull Context context) {
        super(context);
        this.context = context;
        setUpDialog();
    }

    public DialogChooseTypeVoice(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setUpDialog();
    }

    protected DialogChooseTypeVoice(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void setUpDialog() {
        setContentView(R.layout.fragment_dialog_chose_type_voice);
        setCanceledOnTouchOutside(false);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(WRAP_CONTENT, MATCH_PARENT);
        }
        setOnClickButtonChoice();
        setupViewSingleChoice();
        setupViewGroupChoice();
        setupButtonStartPause();
        setupAudio();
        setupButtonOk();
        setupButtonCancel();
    }

    private void setupButtonCancel() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.pause();
                dismiss();
            }
        });
    }

    private void setupButtonOk() {
        btnOk = findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMess == AppConstant.TYPE_MESSAGE_SINGLE) {
                    messSingle.setContent(edtSingleChoice.getText().toString());
                    if (messSingle.getUser() != null) {
                        onAddMessListener.onAddSingleItem(messSingle);
                    }
                } else if (typeMess == AppConstant.TYPE_MESSAGE_GROUP) {
                    onAddMessListener.onAddGroupItem(messAdapter.getAll());
                }
                player.pause();
                dismiss();
            }
        });
    }

    private void setupAudio() {
        btnPlayPause.setVisibility(View.GONE);
        sbAudio = findViewById(R.id.sbAudioDialog);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(AppRawData.URL);
            player.prepare(); // might take long! (for buffering, etc)
            btnPlayPause.setVisibility(View.VISIBLE);
            Log.e("setupAudio: ", String.valueOf(timeEnd) + "    " + String.valueOf(timeStart));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

            }
        });
        sbAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (peopleChange) {
                    player.seekTo(timeStart + i);
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
        UpdateSeekBarThread updateSeekBarThread = new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread, 50);
    }

    private void setupButtonStartPause() {
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playing = !playing;
                if (playing) {
                    btnPlayPause.setImageResource(R.drawable.icon_pause);
                    player.start();
                } else {
                    btnPlayPause.setImageResource(R.drawable.icon_play);
                    player.pause();
                }
            }
        });
    }


    private void setupViewSingleChoice() {
        viewSingleChoice = findViewById(R.id.viewChoiceSingle);
        imgAvaAddSingleChoice = findViewById(R.id.imgAvaMessSingle);
        imgAvaAddSingleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onImgAvaMessSing: ", "haha");
                final DialogChoiceAva dialog = new DialogChoiceAva(context);
                dialog.setOnAvaCliclListener(new OnItemClickListener() {
                    @Override
                    public void onClickItem(int position) {
                        messSingle.setUser(AppConstant.getListUser().get(position));
                        Glide.with(context)
                                .load(AppConstant.getListUser().get(position).getAvaUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .into(imgAvaAddSingleChoice);
                        tvNameSingleChoice.setText(AppConstant.getListUser().get(position).getName());
                        dialog.dismiss();
                    }
                });
                dialog.setData(AppConstant.getListUser());
                int location[] = new int[2];
                view.getLocationOnScreen(location);
                int heightPopup = context.getResources().getDimensionPixelOffset(
                        R.dimen.height_view_choose_ava
                );
                if (AppConstant.heightScreen - location[1] < heightPopup) {
                    dialog.setLocation(location[0], location[1] - heightPopup);
                } else {
                    dialog.setLocation(location[0], location[1]);
                }
                dialog.show();
            }
        });
        edtSingleChoice = findViewById(R.id.edtMess);
        tvNameSingleChoice = findViewById(R.id.tvNameMess);
        tvTimeSingleChoice = findViewById(R.id.tvTimeSingleMess);
    }


    private void setupViewGroupChoice() {
        viewGroupChoice = findViewById(R.id.viewChoiceGroup);
        rcvMesses = findViewById(R.id.rcvMesses);
        btnAddMess = findViewById(R.id.btnAddMess);
        rcvAva = findViewById(R.id.rcvAva);
        messAdapter = new MessAdapter();
        avaAddUserAdapter = new AvaAddUserAdapter();
        avaAddUserAdapter.setData(AppConstant.getListUser());
        avaAddUserAdapter.setOnClickItemListener(this);
        rcvAva.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rcvAva.setAdapter(avaAddUserAdapter);
        rcvMesses.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rcvMesses.setAdapter(messAdapter);
    }


    private void setOnClickButtonChoice() {
        btnChoiceGroup = findViewById(R.id.btnChoiceGroup);
        btnChoiceSingle = findViewById(R.id.btnChoiceSingle);
        btnChoiceNoise = findViewById(R.id.btnChoiceNoise);
        btnChoiceSilent = findViewById(R.id.btnChoiceSilent);
        btnChoiceSilent.setOnClickListener(this);
        btnChoiceNoise.setOnClickListener(this);
        btnChoiceSingle.setOnClickListener(this);
        btnChoiceGroup.setOnClickListener(this);
    }

    private void chooseOneType(int id) {
        switch (id) {
            case R.id.btnChoiceGroup:
                chooseGroup();
                typeMess = AppConstant.TYPE_MESSAGE_GROUP;
                break;
            case R.id.btnChoiceNoise:
                chooseNoise();
                typeMess = AppConstant.TYPE_MESSAGE_NOISE;
                break;
            case R.id.btnChoiceSilent:
                chooseSilent();
                typeMess = AppConstant.TYPE_MESSAGE_SILENT;
                break;
            case R.id.btnChoiceSingle:
                chooseSingle();
                typeMess = AppConstant.TYPE_MESSAGE_SINGLE;
                break;
        }
    }

    private void unChooseOneType(int id) {
        switch (id) {
            case R.id.btnChoiceGroup:
                unChooseGroup();
                break;
            case R.id.btnChoiceNoise:
                unChooseNoise();
                break;
            case R.id.btnChoiceSilent:
                unChooseSilent();
                break;
            case R.id.btnChoiceSingle:
                unChooseSingle();
                break;
        }
    }

    private void chooseGroup() {
        viewGroupChoice.setVisibility(View.VISIBLE);
        btnChoiceGroup.setImageResource(R.drawable.icon_choice_group_chose);
    }

    private void unChooseGroup() {
        viewGroupChoice.setVisibility(View.GONE);
        btnChoiceGroup.setImageResource(R.drawable.icon_choice_group);
    }

    private void chooseSingle() {
        viewSingleChoice.setVisibility(View.VISIBLE);
        btnChoiceSingle.setImageResource(R.drawable.icon_choice_single_chose);
    }

    private void unChooseSingle() {
        viewSingleChoice.setVisibility(View.GONE);
        btnChoiceSingle.setImageResource(R.drawable.icon_choice_single);
    }

    private void chooseNoise() {
        btnChoiceNoise.setTextColor(context.getResources().getColor(R.color.yellow));
    }

    private void unChooseNoise() {
        btnChoiceNoise.setTextColor(context.getResources().getColor(R.color.white));
    }


    private void chooseSilent() {
        btnChoiceSilent.setTextColor(context.getResources().getColor(R.color.yellow));
    }

    private void unChooseSilent() {
        btnChoiceSilent.setTextColor(context.getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        if (buttonChosing != -1) {
            unChooseOneType(buttonChosing);
        }
        chooseOneType(view.getId());
        buttonChosing = view.getId();
    }

    public void setTime(int startTimeChoose, int endTimeChoose) {
        timeStart = startTimeChoose;
        timeEnd = endTimeChoose;
        sbAudio.setMax(timeEnd - timeStart);
        player.seekTo(timeStart);
    }

    @OnClick(R.id.btnAddMess)
    void onBtnAddMessClick() {

    }

    @Override
    public void onClickItem(int position) {
        messAdapter.add(new Messenger(AppConstant.getListUser().get(position), "", ""));
    }

    public void setOnAddMessListener(OnAddMessListener onAddMessListener) {
        this.onAddMessListener = onAddMessListener;
    }

    // Thread sử dụng để Update trạng thái cho SeekBar.
    class UpdateSeekBarThread implements Runnable {

        public void run() {
            if (playing) {
                int currentPosition = player.getCurrentPosition() - timeStart;
                if (player.getCurrentPosition() < timeEnd) {
                    sbAudio.setProgress(currentPosition);
                } else {
                    sbAudio.setProgress(0);
                    player.seekTo(timeStart);
                }
                // Ngừng thread 50 mili giây.
            }
            threadHandler.postDelayed(this, 50);
        }
    }
}
