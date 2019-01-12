package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.SpacesItemDecoration;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.MeetingConversationAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.MeetingMemberAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Meeting;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingFragment extends BaseFragment {

    @BindView(R.id.rcvMeetingMember)
    RecyclerView rcvMeetingMember;
    @BindView(R.id.imgBoss)
    ImageView imgBoss;
    @BindView(R.id.imgClerk)
    ImageView imgClerk;
    @BindView(R.id.tvNameBoss)
    TextView tvNameBoss;
    @BindView(R.id.tvNameAssitant)
    TextView tvNameAssitant;
    @BindView(R.id.tvMember)
    TextView tvMember;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvNameMeeting)
    TextView tvNameMeeting;
    @BindView(R.id.tvContentMeeting)
    TextView tvContentMeeting;
    @BindView(R.id.hideInfoMeeting)
    ImageButton hideInfoMeeting;
    @BindView(R.id.infoMeeting)
    View infoMeeting;

    @BindView(R.id.rcvConversationMess)
    RecyclerView rcvConversationMess;

    private MeetingMemberAdapter memberAdapter = new MeetingMemberAdapter();
    private MeetingConversationAdapter conversationAdapter = new MeetingConversationAdapter();
    private Meeting meeting = new Meeting();

    public static BaseFragment newInstance(Meeting meeting) {
        Bundle args = new Bundle();
        MeetingFragment fragment = new MeetingFragment();
        fragment.meeting = meeting;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_meeting;
    }

    @Override
    void initView(View view) {
        getData();
        addInformationMeeting();
        addConversationMess();
    }

    private void getData() {
        AppClient.getService().getAllMessInMeeting(meeting.getId())
                .enqueue(new Callback<List<Messenger>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Messenger>> call, @NonNull Response<List<Messenger>> response) {
                        try {
                            if (response.body() != null) {
                                conversationAdapter.setData(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Messenger>> call, Throwable t) {

                    }
                });
    }

    private void addConversationMess() {
        setUpMessRecycleView();
    }

    private void setUpMessRecycleView() {
        rcvConversationMess.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvConversationMess.setAdapter(conversationAdapter);
    }

    private void addInformationMeeting() {
        if (getContext() != null) {
            tvNameMeeting.setText(meeting.getName());
            tvContentMeeting.setText(meeting.getContent());
            Glide.with(getContext())
                    .load(meeting.getLeader().getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgBoss);
            tvNameBoss.setText(meeting.getLeader().getName());
            Glide.with(getContext())
                    .load(meeting.getAssistant().getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgClerk);
            tvNameAssitant.setText(meeting.getAssistant().getName());
            tvMember.setText(getResources().getString(R.string.meeting_member,
                    String.valueOf(meeting.getMembers().size())));
            tvTime.setText(meeting.getDateTime());
        }
        setUpMemberRecyclerView();
    }

    private void setUpMemberRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rcvMeetingMember.setLayoutManager(gridLayoutManager);
        rcvMeetingMember.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_gigantic)
        , getResources().getDimensionPixelOffset(R.dimen.padding_gigantic)
        , getResources().getDimensionPixelOffset(R.dimen.padding_normal)
        , getResources().getDimensionPixelOffset(R.dimen.padding_normal)));
        memberAdapter.setData(meeting.getMembers());
        rcvMeetingMember.setAdapter(memberAdapter);
    }

    @OnClick(R.id.btnEditMesses)
    void onButtonEditMessesClick() {
        if(AppConstant.getMainUsser().getTypePeople() == AppConstant.TYPE_ASSITANT) {
            changeFragment(AddAndEditConversationFragment.newInstance(meeting.getAudioUrl(), conversationAdapter.getAllData(), meeting.getDateTime()), true);
        }
    }

    @OnClick(R.id.hideInfoMeeting)
    void onHideInfoMeetingClick(){
        if(infoMeeting.getVisibility() == View.VISIBLE){
            infoMeeting.setVisibility(View.GONE);
            hideInfoMeeting.setBackgroundResource(R.drawable.icon_show_meeting_information);
        } else if( infoMeeting.getVisibility() == View.GONE){
            infoMeeting.setVisibility(View.VISIBLE);
            hideInfoMeeting.setBackgroundResource(R.drawable.icon_hide_meeting_infomation);
        }
    }
}
