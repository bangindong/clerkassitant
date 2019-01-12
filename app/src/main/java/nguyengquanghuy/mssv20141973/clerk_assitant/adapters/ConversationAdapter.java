package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Meeting;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.BaseHolder> {

    private OnItemClickListener onClickItemListener;
    private List<Meeting> data = new ArrayList<>();

    public void setOnClickItemListener(OnItemClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public ConversationAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConversationAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chid_conversation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Meeting> meetings) {
        this.data.clear();
        this.data.addAll(meetings);
        notifyDataSetChanged();
    }

    public Meeting get(int position) {
        return data.get(position);
    }

    class ChildHolder extends ConversationAdapter.BaseHolder {

        @BindView(R.id.imgChild)
        ImageView imgChild;
        @BindView(R.id.tvNameMeeting)
        TextView tvNameMeeting;
        @BindView(R.id.tvContentMeeting)
        TextView tvContentMeeting;
        @BindView(R.id.tvRoomNameMeeting)
        TextView tvRoomNameMeeting;
        @BindView(R.id.tvTimeMeeting)
        TextView tvTimeMeeting;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getAssistant().getAvaUrl())
                    .into(imgChild);
            tvNameMeeting.setText(data.get(position).getName());
            tvContentMeeting.setText(data.get(position).getContent());
            tvRoomNameMeeting.setText(data.get(position).getAssistant().getName());
            tvTimeMeeting.setText(data.get(position).getDateTime());
        }
    }

    abstract class BaseHolder extends RecyclerView.ViewHolder {
        BaseHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemListener.onClickItem(getAdapterPosition());
                }
            });
        }

        void bind(int position) {
            onBindingData(position);
        }

        abstract void onBindingData(int position);
    }
}
