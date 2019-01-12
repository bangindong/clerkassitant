package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppRawData;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.ConversationAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Meeting;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationFragment extends BaseFragment implements OnItemClickListener {

    @BindView(R.id.rcvConversation)
    RecyclerView rcvConversation;

    private ConversationAdapter adapter = new ConversationAdapter();
    private List<Meeting> meetings = new ArrayList<>();

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_conversation;
    }

    @Override
    void initView(View view) {
        getData();
        setUpRecycleView();
    }

    private void getData() {
        AppClient.getService().getAllMeetings()
                .enqueue(new Callback<List<Meeting>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Meeting>> call, @NonNull Response<List<Meeting>> response) {
                        try {
                            if (response.body() != null) {
                                adapter.setData(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Meeting>> call, Throwable t) {

                    }
                });
    }

    private void setUpRecycleView() {
        rcvConversation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter.setOnClickItemListener(this);
        rcvConversation.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        changeFragment(MeetingFragment.newInstance(adapter.get(position)), true);
    }
}
