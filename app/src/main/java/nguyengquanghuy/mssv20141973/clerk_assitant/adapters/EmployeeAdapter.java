package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.BaseHolder> {

    private OnItemClickListener onClickItemListener;
    private List<User> employees = new ArrayList<>();

    public void setOnClickItemListener(OnItemClickListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chid_employee, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setData(List<User> employees) {
        this.employees.clear();
        this.employees.addAll(employees);
        Log.e("setData: ",String.valueOf(employees.size()) );
        notifyDataSetChanged();
    }

    public User getItem(int position) {
        return employees.get(position);
    }

    class ChildHolder extends BaseHolder {

        @BindView(R.id.imgEmployee)
        ImageView imgEmployee;
        @BindView(R.id.tvNameEmployee)
        TextView tvNameEmployee;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(employees.get(position).getAvaUrl())
                    .into(imgEmployee);
            tvNameEmployee.setText(employees.get(position).getName());
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
