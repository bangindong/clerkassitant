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
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Room;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.BaseHolder> {
    private List<Room> data = new ArrayList<>();

    @NonNull
    @Override
    public RoomAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_room, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Room> rooms) {
        this.data.clear();
        this.data.addAll(rooms);
        notifyDataSetChanged();
    }

    class ChildHolder extends RoomAdapter.BaseHolder {

        @BindView(R.id.imgRoom)
        ImageView imgRoom;
        @BindView(R.id.tvRoomName)
        TextView tvRoomName;
        @BindView(R.id.tvRoomAddress)
        TextView tvRoomAddress;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getAvaUrl())
                    .into(imgRoom);
            tvRoomAddress.setText(data.get(position).getAddress());
            tvRoomName.setText(data.get(position).getName());
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
