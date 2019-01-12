package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;

public class MeetingConversationGroupAdapter extends RecyclerView.Adapter<MeetingConversationGroupAdapter.BaseHolder> {

    @NonNull
    @Override
    public MeetingConversationGroupAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MeetingConversationGroupAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_meeting_conversation_single, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingConversationGroupAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ChildHolder extends MeetingConversationGroupAdapter.BaseHolder {

        @BindView(R.id.imgAvaMessSingle)
        ImageView imgAvaMessSingle;
        @BindView(R.id.tvTimeSingleMess)
        TextView tvTimeSingleMess;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(R.drawable.img_test)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMessSingle);
        }
    }

    abstract class BaseHolder extends RecyclerView.ViewHolder {
        BaseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(int position) {
            onBindingData(position);
        }

        abstract void onBindingData(int position);
    }
}
