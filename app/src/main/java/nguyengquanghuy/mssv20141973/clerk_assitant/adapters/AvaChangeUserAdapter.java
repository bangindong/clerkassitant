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
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

public class AvaChangeUserAdapter extends RecyclerView.Adapter<AvaChangeUserAdapter.BaseHolder> {

    private OnItemClickListener onClickItemListener;
    private List<User> data = new ArrayList<>();

    public void setOnClickItemListener(OnItemClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public AvaChangeUserAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AvaChangeUserAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_ava_change_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AvaChangeUserAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<User> users) {
        this.data.clear();
        this.data.addAll(users);
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return data.get(position);
    }

    class ChildHolder extends AvaChangeUserAdapter.BaseHolder {

        @BindView(R.id.imgAvaChoice)
        ImageView imgAvaChoice;
        @BindView(R.id.tvName)
        TextView tvName;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaChoice);
            tvName.setText(data.get(position).getName());
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
