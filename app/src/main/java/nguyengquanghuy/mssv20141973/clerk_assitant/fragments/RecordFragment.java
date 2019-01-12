package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.FileUtils;
import nguyengquanghuy.mssv20141973.clerk_assitant.MainActivity;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordFragment extends BaseFragment {

    @BindView(R.id.btnRecord)
    Button btnRecord;
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
    @BindView(R.id.txvHello)
    TextView txvHello;
    @BindView(R.id.txvExampleText)
    TextView txvExampleText;
    private MediaRecorder myAudioRecorder;
    private List<String> outputFile ;
    private String urlFile;
    private MediaPlayer mediaPlayer;
    private Rect rect;
    private int buttonAdding = 0;
    private boolean isRecordDone = false;
    private User user;
    private ImageButton[] tagButtonFileRecords = new ImageButton[6];

    public static BaseFragment newInstance(User user) {
        Bundle args = new Bundle();
        RecordFragment fragment = new RecordFragment();
        fragment.user = user;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_record;
    }

    @Override
    void initView(View view) {
        outputFile = new ArrayList<>();
        addListFileRecord();
        setUpListFileRecord();
        setUpView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListFileRecord() {
        for (final ImageButton button : tagButtonFileRecords) {
            final String linkImage = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + button.getContentDescription() + "_" + user.getId() + ".wav";
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(view.getVisibility() == View.VISIBLE) {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(linkImage);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            Toast.makeText(getContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            // make something
                        }
                    }
                }
            });
            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
                public void onLongPress(MotionEvent e) {
                    try {
                        if(mediaPlayer!= null) {
                            mediaPlayer.pause();
                            mediaPlayer.reset();
                        }
                       showDialog(button, linkImage);
                        if(isRecordDone){
                            recordNotDone();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
            });
        }
    }

    private void showDialog(final ImageButton button, final String linkImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chắc muốn xóa file ghi âm này?");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                button.setVisibility(View.GONE);
                outputFile.remove(linkImage);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void setUpRecord() {
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpView() {
        txvHello.setText(getString(R.string.record_tv_hello, AppConstant.getMainUsser().getName()));
        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!isRecordDone) {
                    if (buttonAdding != -1) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                                prepareFileRecord();
                                break;
                            case MotionEvent.ACTION_UP:
                                if (view.isPressed()) {
                                    addFileRecord();
                                    if (outputFile.size() == 6) {
                                        recordDone();
                                    }
                                } else {
                                    closeFileRecord();
                                    ((Button) view).setText("");
                                }
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (!rect.contains(view.getLeft() + (int) motionEvent.getX(), view.getTop()
                                        + (int) motionEvent.getY())) {
                                    ((Button) view).setText("Cancel");
                                    view.setPressed(false);
                                } else {
                                    view.setPressed(true);
                                }
                                break;
                        }
                    }

                } else {
                    for (int i = 0; i < outputFile.size(); i++) {
                        Log.e( "onTouch: ",String.valueOf(i) );
                        uploadFile(outputFile.get(i));
                        if(i == outputFile.size()-1){
                            outputFile.clear();
                        }
                    }
                    if (getActivity() != null) {
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }
                return false;
            }
        });
    }

    private void recordDone() {
        btnRecord.setBackgroundResource(R.drawable.background_button_record_done);
        btnRecord.setText("Done");
        isRecordDone = true;
    }

    private void recordNotDone() {
        btnRecord.setBackgroundResource(R.drawable.background_button_record);
        btnRecord.setText("");
        isRecordDone = false;
    }

    private void closeFileRecord() {
        myAudioRecorder.stop();
        myAudioRecorder.reset();
        tagButtonFileRecords[buttonAdding].setVisibility(View.GONE);
    }

    private void addFileRecord() {
        try {
            myAudioRecorder.stop();
            myAudioRecorder.reset();
            myAudioRecorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("addFileRecord: ",outputFile.toString() );
    }

    private void addListFileRecord() {
        tagButtonFileRecords[0] = fileRecord1;
        tagButtonFileRecords[1] = fileRecord2;
        tagButtonFileRecords[2] = fileRecord3;
        tagButtonFileRecords[3] = fileRecord4;
        tagButtonFileRecords[4] = fileRecord5;
        tagButtonFileRecords[5] = fileRecord6;
    }

    private void prepareFileRecord() {
        for (int i = 0; i < tagButtonFileRecords.length; i++) {
            if (tagButtonFileRecords[i].getVisibility() == View.GONE) {
                txvExampleText.setText(AppConstant.stringRead[i]);
                urlFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        tagButtonFileRecords[i].getContentDescription()+"_"+ user.getId() + ".wav";
                Log.e("prepareFileRecord: ", urlFile);
                tagButtonFileRecords[i].setVisibility(View.VISIBLE);
                buttonAdding = i;
                break;
            }
        }
        setUpRecord();
        myAudioRecorder.setOutputFile(urlFile);
        outputFile.add(urlFile);
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile(String fileUri) {
    try {

        File file = new File(fileUri);

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("audio/*"),
                        file
                );

        AppConstant.getMainUsser().getSounds().add(AppClient.BASE_API_URL+"getFile?nameFile="+file.getName());
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("sampleFile", file.getName(), requestFile);

        String descriptionString = user.getId();
        RequestBody userId =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        AppClient.getService().upload( userId, body )
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            if (response.body() != null) {
                                Log.e("onResponse: ", response.toString() );
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    } catch (Exception e ){
        e.printStackTrace();
    }}

}
