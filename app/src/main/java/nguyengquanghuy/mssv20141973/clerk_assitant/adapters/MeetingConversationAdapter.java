package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.AppConstant;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;

public class MeetingConversationAdapter extends RecyclerView.Adapter<MeetingConversationAdapter.BaseHolder> {

    private List<Messenger> data = new ArrayList<>();

    @NonNull
    @Override
    public MeetingConversationAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case AppConstant.TYPE_MESSAGE_GROUP:
                return new ChildGroupHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.child_meeting_conversation_group, viewGroup, false));
            case AppConstant.TYPE_MESSAGE_NOISE:
                return new ChildNoiseHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.child_meeting_conversation_noise, viewGroup, false));
            case AppConstant.TYPE_MESSAGE_SILENT:
                return new ChildSilentHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.child_meeting_conversation_silent, viewGroup, false));
            case AppConstant.TYPE_MESSAGE_SINGLE:
                return new ChildSingleHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.child_meeting_conversation_single, viewGroup, false));
            default:
                return new ChildSingleHolder(LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.child_meeting_information_member, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingConversationAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return AppConstant.TYPE_MESSAGE_SINGLE;
    }

    public void setData(List<Messenger> messes) {
        data.clear();
        data.addAll(messes);
        notifyDataSetChanged();
    }

    public List<Messenger> getAllData() {
        return data;
    }

    class ChildGroupHolder extends MeetingConversationAdapter.BaseHolder {

        @BindView(R.id.imgAvaMessGroup)
        ImageView imgAvaMessGroup;
        @BindView(R.id.tvTimeGroupMess)
        TextView tvTimeGroupMess;
        @BindView(R.id.rcvMesses)
        RecyclerView rcvMesses;

        private MeetingConversationGroupAdapter adapter = new MeetingConversationGroupAdapter();

        ChildGroupHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            setUpRecycleView();
            Glide.with(itemView.getContext())
                    .load(R.drawable.img_test)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMessGroup);
        }

        private void setUpRecycleView() {
            rcvMesses.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            rcvMesses.setAdapter(adapter);
        }
    }

    class ChildNoiseHolder extends MeetingConversationAdapter.BaseHolder {

        @BindView(R.id.imgAvaMessNoise)
        ImageView imgAvarMessNoise;
        @BindView(R.id.tvTimeNoiseMess)
        TextView tvTimeNoiseMess;

        ChildNoiseHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(R.drawable.img_message_noise)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvarMessNoise);
        }
    }

    class ChildSilentHolder extends MeetingConversationAdapter.BaseHolder {

        @BindView(R.id.imgAvaMessSilent)
        ImageView imgAvaMessSilent;
        @BindView(R.id.tvTimeMessSilent)
        TextView tvTimeMessSilent;

        ChildSilentHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(R.drawable.icon_message_silent)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMessSilent);
        }
    }

    class ChildSingleHolder extends MeetingConversationAdapter.BaseHolder {

        @BindView(R.id.imgAvaMessSingle)
        ImageView imgAvaMessSingle;
        @BindView(R.id.tvTimeSingleMess)
        TextView tvTimeSingleMess;
        @BindView(R.id.edtMess)
        TextView tvMess;

        ChildSingleHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getUser().getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMessSingle);
            tvTimeSingleMess.setText(data.get(position).getTime());
            tvMess.setText(data.get(position).getContent());
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
