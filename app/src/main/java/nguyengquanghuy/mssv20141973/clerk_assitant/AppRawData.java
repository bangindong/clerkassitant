package nguyengquanghuy.mssv20141973.clerk_assitant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.model.Meeting;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Room;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;

public class AppRawData {
    public static final String URL = "https://ia802909.us.archive.org/21/items/LocDinhKy/LocDinhKy01.mp3";
    private static String[] listRecord = new String[]{
            URL, URL, URL, URL, URL, URL
    };
//    public static User assitant = new User(
//            "0", "huy", "https://znews-photo.zadn.vn/w860/Uploaded/ngtmns/2016_12_12/4.jpg",
//            "0969072244", "qhuy.229@gmail.com", "thư kí", "phòng kinh doanh",
//            AppConstant.TYPE_ASSITANT, new ArrayList<>(Arrays.asList(listRecord)));
//    public static User user = new User(
//            "1", "hạnh", "http://techrum.vn/chevereto/images/2017/06/04/3RnNx.jpg",
//            "0969053004", "qbaaa.229@gmail.com", "giám đốc", "phòng kế toán",
//            AppConstant.TYPE_NOT_ASSITANT, new ArrayList<String>());
//    public static User user1 = new User(
//            "2", "thuận", "https://dantricdn.com/thumb_w/640/2018/embe1-1515732302477.jpg",
//            "0969054004", "qbaaa.2vd9@gmail.com", "nhân viên", "phòng hành chính",
//            AppConstant.TYPE_NOT_ASSITANT, new ArrayList<String>());
//    public static User user2 = new User(
//            "3", "mạnh", "http://sohanews.sohacdn.com/thumb_w/660/2017/photo1486969199024-1486969199175-0-32-308-529-crop-1486969281069.jpg",
//            "0969004304", "qba222@gmail.com", "phó phòng", "phòng chém gió",
//            AppConstant.TYPE_NOT_ASSITANT, new ArrayList<String>());
//    public static User user3 = new User(
//            "4", "linh", "http://thuthuatphanmem.vn/uploads/2018/04/10/hinh-anh-buon-co-don-98_052047322.jpg",
//            "0969003424", "qbafdsf29@gmail.com", "trưởng phòng", "phòng ăn chơi",
//            AppConstant.TYPE_NOT_ASSITANT, new ArrayList<String>());
//    public static User user4 = new User(
//            "5", "hiền", "https://image.thanhnien.vn/665/uploaded/hoangnam/2017_05_30/bangdi11_oyoa.jpg",
//            "0969456004", "qbaaưe9@gmail.com", "nhân viên", "phòng ngủ",
//            AppConstant.TYPE_NOT_ASSITANT, new ArrayList<String>());
//    public static Room room1 = new Room("0","A1-103","http://nhadep-nblog.com/upload/2016/10/11/thiet-ke-phong-ngu-dep-hien-dai-04.jpg"
//            , "Tổ 12, Phường Khương Trung, Khương Liệt, Hà Nội" );
//    public static Room room2 = new Room("1","A2-103","http://nhadep-nblog.com/upload/2016/10/11/thiet-ke-phong-ngu-dep-hien-dai-01.jpg"
//            , "Tổ 14, Phường Khương Đình, Khương Liệt, Hà Nội");
//    public static Room room3 = new Room("2","A3-104","https://binhansi.com.vn/wp-content/uploads/2018/10/am-thanh-hoi-thao-tai-nam-dinh.jpg"
//            , "Tổ 13, Phường Bách Khoa, Hai Bà Trưng, Hà Nội");
//    public static Room room4 = new Room("3","B1-103","https://noithatmienbac.vn/Images/ban-phong-hop%20(1).jpg"
//            , "Tổ 11, Phường Bách Khoa, Hai Bà Trưng, Hà Nội");
//    public static Room room5 = new Room("4","B2-103","http://phonghoitruong.com.vn/wp-content/uploads/2016/08/Phong-hop-nho.jpg"
//            , "Tổ 10, Phường Bách Khoa, Hai Bà Trưng, Hà Nội");
//    public static Room room6 = new Room("5","B1-104","https://thietkevanphongmienbac.vn/Uploaded_products/img_any/thiet-ke-noi-that-phong-hop(3).jpg"
//            , "Tổ 11, Phường Bách Khoa, Hai Bà Trưng, Hà Nội");
//    public static Messenger mess1 = new Messenger(user,"Cuộc họp này không nên diễn ra","2018/12/13 11:12:00");
//    public static Messenger mess2 = new Messenger(user,"Chúng ta nên cẩn thận","2018/12/13 11:13:00");
//    public static Messenger mess3 = new Messenger(user,"Theo tôi nghĩ thì lại khác","2018/12/13 11:14:00");
//    public static Messenger mess4 = new Messenger(user,"Trưa nay chúng ta nên ăn cơm","2018/12/13 11:15:00");
//    public static Messenger mess5 = new Messenger(user,"Đừng, đi ăn phở đi","2018/12/13 11:16:00");
//    public static Messenger mess6 = new Messenger(user,"Ăn bún cơ","2018/12/13 11:17:00");
//    public static Messenger mess7 = new Messenger(user,"Vậy thì ăn bún","2018/12/13 11:18:00");
//
//    public static Meeting meeting1 = new Meeting("1","Cuộc họp đảng bộ",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room1, "2018/12/13 11:12:00", user, assitant, URL, Arrays.asList(mess1, mess2, mess3, mess4, mess5, mess6, mess7));
//    public static Meeting meeting2 = new Meeting("1","Cuộc họp lân thứ nhất",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room2, "2018/12/13 11:12:00", user1, assitant, URL, Arrays.asList(mess1, mess2, mess4, mess5));
//    public static Meeting meeting3 = new Meeting("1","Cuộc họp tổng kết năm",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room3, "2018/12/13 11:12:00", user2, assitant, URL, Arrays.asList(mess1, mess3, mess2, mess7));
//    public static Meeting meeting4 = new Meeting("1","Cuộc họp tổng kết quý",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room4, "2018/12/13 11:12:00", user3, assitant, URL, Arrays.asList(mess1, mess2, mess5, mess7));
//    public static Meeting meeting5 = new Meeting("1","Cuộc họp phân chia nhân sự",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room5, "2018/12/13 11:12:00", user4, assitant, URL, Arrays.asList(mess1, mess3, mess6, mess4));
//    public static Meeting meeting6 = new Meeting("1","Cuộc họp đường lối công ty",
//            "Dưới sự chỉ đạo các cấp cán bộ",Arrays.asList(user1,user2,user3,user4,user,assitant),
//            room6, "2018/12/13 11:12:00", user, assitant, URL, Arrays.asList(mess1, mess5, mess5, mess7));
//    public static List<User> listData = Arrays.asList(assitant,user,user1,user2,user3,user4);
}
