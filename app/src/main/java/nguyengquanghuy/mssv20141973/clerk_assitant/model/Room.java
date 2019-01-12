package nguyengquanghuy.mssv20141973.clerk_assitant.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("room_url")
    @Expose
    private String avaUrl;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;

    public Room(String id, String name, String avaUrl, String address) {
        this.id = id;
        this.name = name;
        this.avaUrl = avaUrl;
        this.address = address;
    }

    public Room() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
