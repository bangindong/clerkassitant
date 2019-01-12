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

public class AvaAddUserAdapter extends RecyclerView.Adapter<AvaAddUserAdapter.BaseHolder> {

    private OnItemClickListener onClickItemListener;
    private List<User> data = new ArrayList<>();

    public void setOnClickItemListener(OnItemClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public AvaAddUserAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AvaAddUserAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_ava_add_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AvaAddUserAdapter.BaseHolder baseHolder, int i) {
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

    class ChildHolder extends AvaAddUserAdapter.BaseHolder {

        @BindView(R.id.imgAvaChoice)
        ImageView imgAvaChoice;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaChoice);
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
