package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppRawData;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.SpacesItemDecoration;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.EmployeeAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeFragment extends BaseFragment implements OnItemClickListener {

    @BindView(R.id.rcvEmployee)
    RecyclerView rcvEmployee;

    private EmployeeAdapter adapter = new EmployeeAdapter();

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        EmployeeFragment fragment = new EmployeeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_employee;
    }

    @Override
    void initView(View view) {
        getData();
        setUpRecycleView();
    }

    private void getData() {
        AppClient.getService().getAllUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        try {
                            if (response.body() != null) {
                                adapter.setData(response.body());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
    }

    private void setUpRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rcvEmployee.setLayoutManager(gridLayoutManager);
        rcvEmployee.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.padding_giant)));
        adapter.setOnClickItemListener(this);
        rcvEmployee.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        changeFragment(ProfileFragment.newInstance(adapter.getItem(position)), true);
    }
}
