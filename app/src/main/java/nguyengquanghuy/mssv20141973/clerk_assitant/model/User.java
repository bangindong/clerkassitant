package nguyengquanghuy.mssv20141973.clerk_assitant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("ava_url")
    @Expose
    private String avaUrl;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("part")
    @Expose
    private String part;
    @SerializedName("sounds")
    @Expose
    private List<String> sounds;

    private int typePeople;

    public User(String id, String name, String avaUrl, String phone, String email, String position, String part, int typePeople, List<String> sounds) {
        this.id = id;
        this.name = name;
        this.avaUrl = avaUrl;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.part = part;
        this.typePeople = typePeople;
        this.sounds = sounds;
    }

    public User() {

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

    public String getAvaUrl() {
        return avaUrl;
    }

    public void setAvaUrl(String avaUrl) {
        this.avaUrl = avaUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getTypePeople() {
        return typePeople;
    }

    public void setTypePeople(int typePeople) {
        this.typePeople = typePeople;
    }

    public List<String> getSounds() {
        return sounds;
    }

    public void setSounds(List<String> sounds) {
        this.sounds = sounds;
    }
}
