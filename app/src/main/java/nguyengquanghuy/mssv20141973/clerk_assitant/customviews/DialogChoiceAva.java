package nguyengquanghuy.mssv20141973.clerk_assitant.customviews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.adapters.AvaChangeUserAdapter;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class DialogChoiceAva extends Dialog {

    private int startX;
    private int startY;
    private RelativeLayout rlvView;
    private RecyclerView rcvAva;
    private AvaChangeUserAdapter avaChangeUserAdapter = new AvaChangeUserAdapter();

    public DialogChoiceAva(@NonNull Context context) {
        super(context);
        setUpDialog();
    }

    private void setUpDialog() {
        setContentView(R.layout.fragment_dialog_chose_ava);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        rlvView = findViewById(R.id.rlvView);
        setUpRcvAva();
        if(getWindow()!= null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
            getWindow().getDecorView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e( "onClick: ","fdfd" );
                }
            });
            getWindow().setGravity(Gravity.START);
        }
    }

    private void setUpRcvAva() {
        rcvAva = findViewById(R.id.rcvAva);
        rcvAva.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvAva.setAdapter(avaChangeUserAdapter);
    }

    public DialogChoiceAva(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setUpDialog();
    }

    protected DialogChoiceAva(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setOnAvaCliclListener(OnItemClickListener onItemClickListener){
        avaChangeUserAdapter.setOnClickItemListener(onItemClickListener);
    }

    public void setData(List<User> users) {
        avaChangeUserAdapter.setData(users);
    }

    public void setLocation(float startX, float startY){
        rlvView.setTranslationX(startX);
        rlvView.setTranslationY(startY);
    }

    public User getData(int position) {
        return avaChangeUserAdapter.getUser(position);
    }
}
