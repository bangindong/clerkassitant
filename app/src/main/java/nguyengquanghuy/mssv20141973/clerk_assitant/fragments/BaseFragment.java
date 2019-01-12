package nguyengquanghuy.mssv20141973.clerk_assitant.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.LoginActivity;
import nguyengquanghuy.mssv20141973.clerk_assitant.MainActivity;

public class BaseFragment extends Fragment {

    private View view;
    private boolean isFirstTime = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppConstant.getBus().register(this);
    }

    @Override
    public void onDetach() {
        AppConstant.getBus().unregister(this);
        super.onDetach();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.e("onAttachFragment: ", "fdgf");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        onViewAppear();
    }

    void onViewAppear() {
        Log.e("onViewAppear: ","123" );
    }

    @SuppressLint("RestrictedApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("onCreateView: ", String.valueOf(isFirstTime));
        if (isFirstTime) {
            view = inflater.inflate(getLayoutResource(), container, false);
            ButterKnife.bind(this, view);
            initView(view);
            isFirstTime = false;
            return view;
        } else {
            if (!isKeepFragment()) {
                view = inflater.inflate(getLayoutResource(), container, false);
                ButterKnife.bind(this, view);
                initView(view);
                return view;
            } else {
                return view;
            }
        }
    }

    void initView(View view) {

    }

    int getLayoutResource() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        onViewDisappear();
        super.onDestroyView();
    }

    void onViewDisappear() {

    }

    void setUpButton(View view, int[] buttonIdsVisiable, int[] buttonIdsHide) {
        for (int buttonId : buttonIdsVisiable) {
            view.findViewById(buttonId).setVisibility(View.VISIBLE);
        }
        for (int buttonId : buttonIdsHide) {
            view.findViewById(buttonId).setVisibility(View.GONE);
        }
    }

    public void onBackPressd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    }

    public void refreshView(FragmentActivity activity) {

    }

    public boolean isKeepFragment() {
        return false;
    }

    public void haveProgressBar(boolean haveProgressBar) {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((MainActivity) Objects.requireNonNull(getActivity())).haveProgressbar(haveProgressBar);
        }
    }

    public void changeFragment(BaseFragment fragment, boolean isAddToBackStack) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).changeFragment(fragment, isAddToBackStack);
        } else if (getActivity() instanceof LoginActivity) {
            ((LoginActivity) getActivity()).changeFragment(fragment);
        }
    }

    public void setHasToolBar(boolean hasToolBar) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hasToolBar(hasToolBar);
        }
    }

    public void setTitle(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setTitle(title);
        }
    }
}
