package nguyengquanghuy.mssv20141973.clerk_assitant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.BaseFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.MainFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.fragments.ProfileFragment;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static Bus bus;
    @BindView(R.id.progressbar)
    RelativeLayout progressbar;
    @BindView(R.id.appBar)
    RelativeLayout appBar;
    @BindView(R.id.ava)
    ImageView ava;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = new Bus(ThreadEnforcer.MAIN);
        bus.register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setAva();
        getAllMember();
        changeFragment(MainFragment.newInstance(), false);
    }

    private void getAllMember() {
        AppClient.getService().getAllUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        try {
                            if (response.body() != null) {
                                AppConstant.setListUser(response.body());
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

    public void changeFragment(BaseFragment fragment, boolean isAddToBackStack) {
        getSupportFragmentManager().beginTransaction().addToBackStack("").replace(R.id.container, fragment, null).commit();
    }

    @Override
    protected void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }

    public void haveProgressbar(boolean haveProgressBar) {
        if (haveProgressBar) {
            progressbar.setVisibility(View.VISIBLE);
        } else {
            progressbar.setVisibility(View.GONE);
        }
    }

    public void setAva(){
        Glide.with(this.getApplicationContext())
                .load(AppConstant.getMainUsser().getAvaUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(ava);
        ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(ProfileFragment.newInstance(AppConstant.getMainUsser()), true);
            }
        });
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void hasToolBar(boolean hasToolBar){
        appBar.setVisibility(hasToolBar? View.VISIBLE: View.GONE);
    }
}

