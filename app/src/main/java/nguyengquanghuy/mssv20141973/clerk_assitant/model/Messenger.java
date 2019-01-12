package nguyengquanghuy.mssv20141973.clerk_assitant.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messenger {
    @SerializedName("date_time")
    @Expose
    private String time;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idMeeting")
    @Expose
    private String idMeeting;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("content")
    @Expose
    private String content;

    public Messenger(User user, String content, String time) {
        this.user = user;
        this.content = content;
        this.time = time;
    }

    public Messenger() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return user.getName() + "\n content:"+ content;
    }
}
