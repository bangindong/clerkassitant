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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyengquanghuy.mssv20141973.clerk_assitant.R;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

public class MeetingMemberAdapter extends RecyclerView.Adapter<MeetingMemberAdapter.BaseHolder> {

    private List<User> data = new ArrayList<>();

    @NonNull
    @Override
    public MeetingMemberAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MeetingMemberAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_meeting_information_member, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingMemberAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<User> members) {
        data.clear();
        data.addAll(members);
        notifyDataSetChanged();
    }

    class ChildHolder extends MeetingMemberAdapter.BaseHolder {

        @BindView(R.id.imgAvaMember)
        ImageView imgAvaMember;
        @BindView(R.id.tvNameMember)
        TextView tvNameMember;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMember);
            tvNameMember.setText(data.get(position).getName());
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
