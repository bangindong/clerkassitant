package nguyengquanghuy.mssv20141973.clerk_assitant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meeting {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("members")
    @Expose
    private List<User> members = null;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("leader")
    @Expose
    private User leader;
    @SerializedName("secretary")
    @Expose
    private User assistant;
    @SerializedName("room_name")
    @Expose
    private String roomName;
    @SerializedName("contents")
    @Expose
    private String content;
    @SerializedName("audioUrl")
    @Expose
    private String audioUrl;

    public Meeting(String id, String name, String content, List<User> members, String roomName, String dateTime, User leader, User assistant, String audioUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.members = members;
        this.roomName = roomName;
        this.dateTime = dateTime;
        this.leader = leader;
        this.assistant = assistant;
        this.audioUrl = audioUrl;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Meeting() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}

