package flight;

import java.util.ArrayList;
import java.util.List;

public final class Waypoint {
    private static final List<String> registeredIds = new ArrayList<String>();
    private String id;
    private float distance;
    private int altitude;
    private int groundSpeed;

    /**
    * distance    : (nautical miles >= 0)
    * altitude    : (feet >= 0)
    * groundSpeed : (knots >= 0)
    */
    public Waypoint(String id, float distance, int altitude, int groundSpeed) {
        this.addId(id.toUpperCase().trim());
        this.setDistance(distance);
        this.setAltitude(altitude);
        this.setGroundSpeed(groundSpeed);
    }

    private void addId(String id) {
        if (Waypoint.registeredIds.contains(id)) {
            throw new IllegalArgumentException("The waypoint ID \"" + id + "\" has been already registered");
        }
        if (id.length() == 0) {
            throw new IllegalArgumentException("The waypoint ID cannot be an empty string or only spaces");
        }
        Waypoint.registeredIds.add(id);
        this.id = id;
    }

    public String toString() {
        String distance = "" + this.distance + "NM";
        String altitude = this.altitude >= 12000 ? "" + (this.altitude / 100) + "FL" : "" + this.altitude + "FT";
        String groundSpeed = "" + this.groundSpeed + "KTS";

        return "Waypoint(" + String.join(" | ", this.id, distance, altitude, groundSpeed) + ")";
    }

    public String getId() {
        return this.id;
    }

    public float getAltitude() {
        return this.altitude;
    }

    public float getDistance() {
        return this.distance;
    }

    public float getDistanceInMeters() {
        return this.getDistance() * 1852; // Converts nautical miles to meters.
    }

    public float getGroundSpeed() {
        return this.groundSpeed;
    }

    public float getGroundSpeedInMetersPerSecond() {
        return (float) this.getGroundSpeed() / 1.944f; // Converts knots to meters per second.
    }

    public void setAltitude(int altitude) {
        if (altitude < 0) {
            throw new IllegalArgumentException("The altitude must be a positive number");
        }
        this.altitude = altitude;
    }

    public void setDistance(float distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("The distance must be a positive number");
        }
        this.distance = distance;
    }

    public void setGroundSpeed(int groundSpeed) {
        if (groundSpeed < 0) {
            throw new IllegalArgumentException("The ground speed must be a positive number");
        }
        this.groundSpeed = groundSpeed;
    }
}
