package nguyengquanghuy.mssv20141973.clerk_assitant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import nguyengquanghuy.mssv20141973.clerk_assitant.customviews.DialogChoiceAva;
import nguyengquanghuy.mssv20141973.clerk_assitant.interfaces.OnItemClickListener;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.BaseHolder> {

    private List<Messenger> data = new ArrayList<>();
    private List<Integer> changeData = new ArrayList<>();

    @NonNull
    @Override
    public MessAdapter.BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessAdapter.ChildHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_dialog_add_single_voice, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessAdapter.BaseHolder baseHolder, int i) {
        baseHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Messenger> messes) {
        data.clear();
        data.addAll(messes);
        notifyDataSetChanged();
    }

    public void add(Messenger messenger) {
        if (messenger != null) {
            data.add(0, messenger);
            notifyDataSetChanged();
        }
    }

    public List<Messenger> getAll() {
        return data;
    }

    public List<Integer> getPositionChange() {
        return changeData;
    }

    public Messenger getItemData(int position) {
        return data.get(position);
    }

    class ChildHolder extends MessAdapter.BaseHolder implements OnItemClickListener {

        @BindView(R.id.imgAvaMessSingle)
        ImageView imgAvaMessSingle;
        @BindView(R.id.tvTimeSingleMess)
        TextView tvTimeSingleMess;
        @BindView(R.id.edtMess)
        EditText tvMess;
        @BindView(R.id.tvNameMess)
        TextView tvNameMess;

        private DialogChoiceAva dialog;

        ChildHolder(View itemView) {
            super(itemView);
        }

        @Override
        void onBindingData(final int position) {
            Glide.with(itemView.getContext())
                    .load(data.get(position).getUser().getAvaUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgAvaMessSingle);

            dialog = new DialogChoiceAva(itemView.getContext());
            dialog.setOnAvaCliclListener(this);
            dialog.setData(AppConstant.getListUser());
            imgAvaMessSingle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int location[] = new int[2];
                    view.getLocationOnScreen(location);
                    int heightPopup = itemView.getResources().getDimensionPixelOffset(
                            R.dimen.height_view_choose_ava
                    );
                    if (AppConstant.heightScreen - location[1] < heightPopup) {
                        dialog.setLocation(location[0], location[1] - heightPopup);
                    } else {
                        dialog.setLocation(location[0], location[1]);
                    }
                    dialog.show();
                }
            });
            tvTimeSingleMess.setText(data.get(position).getTime());
            tvMess.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence!= data.get(position).getContent()){
                        if(!changeData.contains(position)){
                            changeData.add(position);
                        }
                    }
                    data.get(position).setContent(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            tvMess.setText(data.get(position).getContent());
            tvNameMess.setText(data.get(position).getUser().getName());
        }

        @Override
        public void onClickItem(int position) {
            if(!data.get(getAdapterPosition()).getUser().equals(dialog.getData(position))) {
                data.get(getAdapterPosition()).setUser(dialog.getData(position));
                if(!changeData.contains(position)){
                    changeData.add(position);
                }
            }
            dialog.dismiss();
            notifyItemChanged(getAdapterPosition());
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
