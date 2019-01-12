package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppRawData;
import nguyengquanghuy.mssv20141973.clerk_assitant.MainActivity;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import nguyengquanghuy.mssv20141973.clerk_assitant.services.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.edtPassWord)
    EditText edtPassWord;
    @BindView(R.id.btnForgotPassWord)
    Button btnForgotPassWord;

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    void initView(View view) {

    }

    @OnClick(R.id.btnLogin)
    public void onBtnLoginClick() {
        AppClient.getService().getUsers(edtName.getText().toString(), edtPassWord.getText().toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        try {
                            if (response.body() != null) {
                                Log.e("onResponse: ","fdgdgd" );
                                User user = response.body();
                                AppConstant.setMainUsser(user);
                                if(user.getSounds().size()<6){
                                    Log.e( "onResponse: ","fd" );
                                    changeFragment(RecordFragment.newInstance(user), false);
                                } else {
                                    Log.e("onResponse: ","fdgg" );
                                    changeToMainActivity();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    private void changeToMainActivity() {
        if (getActivity() != null) {
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
}
