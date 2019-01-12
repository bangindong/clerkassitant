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
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.RoomAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Room;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomFragment extends BaseFragment {

    @BindView(R.id.rcvRoom)
    RecyclerView rcvRoom;

    private RoomAdapter adapter = new RoomAdapter();

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_room;
    }

    @Override
    void initView(View view) {
        getData();
        setUpRecycleView();
    }

    private void getData() {
        AppClient.getService().getAllRooms()
                .enqueue(new Callback<List<Room>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Room>> call, @NonNull Response<List<Room>> response) {
                        try {
                            if (response.body() != null) {
                                adapter.setData(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Room>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void setUpRecycleView() {
        rcvRoom.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvRoom.setAdapter(adapter);
    }
}
