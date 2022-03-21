package model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AgentHealthInfo {

    @SerializedName("product")
    private String product;

    @SerializedName("networkStatus")
    private String networkStatus;

    @SerializedName("display")
    private String display;

    @SerializedName("paperRoll")
    private String paperRoll;

    @SerializedName("terminalId")
    private int terminalId;

    @SerializedName("merchantName")
    private String merchantName;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("ramDetails")
    private String ramDetails;

    @SerializedName("manufacture")
    private String manufacture;

    @SerializedName("installedApps")
    private List<String> installedApps;

    @SerializedName("batteryStatus")
    private String batteryStatus;

    @SerializedName("serial")
    private String serial;

    @SerializedName("bootloader")
    private String bootloader;

    @SerializedName("host")
    private String host;

    @SerializedName("model")
    private String model;

    @SerializedName("brand")
    private String brand;

    @SerializedName("device")
    private String device;

    @SerializedName("board")
    private String board;

    @SerializedName("hardware")
    private String hardware;

    public String getProduct() {
        return product;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public String getDisplay() {
        return display;
    }

    public String getPaperRoll() {
        return paperRoll;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getRamDetails() {
        return ramDetails;
    }

    public String getManufacture() {
        return manufacture;
    }

    public List<String> getInstalledApps() {
        return installedApps;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public String getSerial() {
        return serial;
    }

    public String getBootloader() {
        return bootloader;
    }

    public String getHost() {
        return host;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getDevice() {
        return device;
    }

    public String getBoard() {
        return board;
    }

    public String getHardware() {
        return hardware;
    }

    public AgentHealthInfo(String product, String merchantName, String networkStatus, String display, String paperRoll, int terminalId, String timeStamp, String ramDetails, String manufacture, List<String> installedApps, String batteryStatus, String serial, String bootloader, String host, String model, String brand, String device, String board, String hardware) {
        this.product = product;
        this.merchantName = merchantName;
        this.networkStatus = networkStatus;
        this.display = display;
        this.paperRoll = paperRoll;
        this.terminalId = terminalId;
        this.timeStamp = timeStamp;
        this.ramDetails = ramDetails;
        this.manufacture = manufacture;
        this.installedApps = installedApps;
        this.batteryStatus = batteryStatus;
        this.serial = serial;
        this.bootloader = bootloader;
        this.host = host;
        this.model = model;
        this.brand = brand;
        this.device = device;
        this.board = board;
        this.hardware = hardware;
    }

    public AgentHealthInfo() {

    }
}