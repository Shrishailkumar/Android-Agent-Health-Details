package model;

public class DeviceInfo {
    private String ramDetails;
    private String board;
    private String batteryPercentage;

    public DeviceInfo(String ramDetails, String batteryPercentage, String board, String fingerPrint, String host, String type, String brad, String manufacture, String id, String model, String serial, String device, String product, String bootLoader, String display, String hardware) {
        this.ramDetails = ramDetails;
        this.batteryPercentage = batteryPercentage;
        this.board = board;
        this.fingerPrint = fingerPrint;
        this.host = host;
        this.type = type;
        this.brad = brad;
        this.manufacture = manufacture;
        this.id = id;
        this.model = model;
        this.serial = serial;
        Device = device;
        this.product = product;
        this.bootLoader = bootLoader;
        Display = display;
        Hardware = hardware;
    }

    public String getRamDetails() {
        return ramDetails;
    }

    public void setRamDetails(String ramDetails) {
        this.ramDetails = ramDetails;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrad() {
        return brad;
    }

    public void setBrad(String brad) {
        this.brad = brad;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBootLoader() {
        return bootLoader;
    }

    public void setBootLoader(String bootLoader) {
        this.bootLoader = bootLoader;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public String getHardware() {
        return Hardware;
    }

    public void setHardware(String hardware) {
        Hardware = hardware;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(String batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    private String fingerPrint;
    private String host;
    private String type;
    private String brad;
    private String manufacture;
    private String id;
    private String model;
    private String serial;
    private String Device;
    private String product;
    private String bootLoader;
    private String Display;
    private String Hardware;

    DeviceInfo() {

    }

}
