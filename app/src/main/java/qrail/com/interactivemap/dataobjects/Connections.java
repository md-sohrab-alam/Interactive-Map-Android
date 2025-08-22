package qrail.com.interactivemap.dataobjects;

public class Connections {

    private String id;
    private String connection;
    private String distance;
    private String connectionColor;
    private String towards;

    public Connections(String id, String connection, String distance,
                       String connectionColor) {

        this.id = id;
        this.connection = connection;
        this.distance = distance;
        this.setConnectionColor(connectionColor);
    }

    public Connections(String id, String connection, String distance,
                       String connectionColor, String towards) {

        this.id = id;
        this.connection = connection;
        this.distance = distance;
        this.setTowards(towards);
        this.setConnectionColor(connectionColor);
    }

    public void setConnections(String id, String connection, String distance,
                               String connectionColor) {

        this.id = id;
        this.connection = connection;
        this.distance = distance;
        this.setConnectionColor(connectionColor);
    }

    public String getId() {
        return id;
    }

    public String getConnection() {
        return connection;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public String toString() {

        return String.format(
                "Connection ::\n ID : %s\n Connection : %s\n Distance : %s\n",
                id, connection, distance);
    }

    public String getConnectionColor() {
        return connectionColor;
    }

    public void setConnectionColor(String connectionColor) {
        this.connectionColor = connectionColor;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

}
