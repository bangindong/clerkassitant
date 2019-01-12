package nguyengquanghuy.mssv20141973.clerk_assitant.interfaces;

import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;

public interface OnAddMessListener {
    void onAddSingleItem(Messenger mess);
    void onAddGroupItem(List<Messenger> messes);
}
