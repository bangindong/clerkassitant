package nguyengquanghuy.mssv20141973.clerk_assitant.services;

import java.util.List;

import nguyengquanghuy.mssv20141973.clerk_assitant.model.Meeting;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Messenger;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.Room;
import nguyengquanghuy.mssv20141973.clerk_assitant.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AppService {
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("rooms")
    Call<List<Room>> getAllRooms();

    @GET("meetings")
    Call<List<Meeting>> getAllMeetings();

    @POST("message/messageInMeeting")
    @FormUrlEncoded
    Call<List<Messenger>> getAllMessInMeeting(@Field("idMeeting") String idMeeting);

    @POST("user/account")
    @FormUrlEncoded
    Call<User> getUsers(@Field("name") String name,@Field("pass") String pass);

    @POST("message/changeMess")
    @FormUrlEncoded
    Call<Messenger> changeMess(@Field("idMess") String id,@Field("objectMess") String objectMess);

    @Multipart
    @POST("uploadFile")
    Call<ResponseBody> upload(
            @Part("idUser") RequestBody description,
            @Part MultipartBody.Part file
    );

}
