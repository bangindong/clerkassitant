package nguyengquanghuy.mssv20141973.clerk_assitant;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

public class AppConstant {

    public static final int TYPE_MESSAGE_SINGLE = 0;
    public static final int TYPE_MESSAGE_GROUP = 1;
    public static final int TYPE_MESSAGE_SILENT = 2;
    public static final int TYPE_MESSAGE_NOISE = 3;
    public static final int TYPE_ASSITANT = 0;
    public static final int TYPE_NOT_ASSITANT = 1;
    public static int heightScreen;
    public static int widthScreen;
    private static User mainUsser;
    public static final String[] listMainTitle = new String[]{
            "Nhân viên",
            "Cuộc họp",
            "Phòng"
    };
    public static String[] stringRead = new String[]{
            "Trời cao trong xanh sương sớm long lanh mặt nước xanh xanh",
            "Xã hội chủ nghĩa việt nam độc lập tự do hanh phúc",
            "Trời trời cao có muôn ngàn vì sao",
            "Nửa đêm vỗ gối ruột đau như cắt nước mắt đầm đìa",
            "Mỗi ngày đến trường là một ngày vui",
            "Trong thời đại đời sống vật chất ngày càng phát triển, đời sống tinh thần càng ngày càng giảm sút"
    };

    private static List<User> listUser = new ArrayList<>();

    public static List<User> getListUser() {
        return listUser;
    }

    public static void setListUser(List<User> listUser) {
        AppConstant.listUser.clear();
        AppConstant.listUser.addAll(listUser);
    }

    public static User getMainUsser() {
        return mainUsser;
    }

    public static void setMainUsser(User mainUsser) {
        AppConstant.mainUsser = mainUsser;
    }

    private static Bus bus;
    
    public static Bus getBus(){
        if(bus == null){
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }

    public static void getHeightWidthScreen(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightScreen = displayMetrics.heightPixels;
        widthScreen = displayMetrics.widthPixels;
    }
}
