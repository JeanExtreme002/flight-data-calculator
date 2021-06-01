package flight;

public final class FlightLeg {
    private Waypoint origin;
    private Waypoint destination;

    public FlightLeg(Waypoint origin, Waypoint destination) {
        this.origin = origin;
        this.destination = destination;
    }

    private double getEstimatedAcceleration() {
        float distance = this.getDistanceInMeters();
        float initialGroundSpeed = this.origin.getGroundSpeedInMetersPerSecond();
        float finalGroundSpeed = this.destination.getGroundSpeedInMetersPerSecond();

        /* Torricelli's equation:
           Final Velocity ^ 2 = Initial Velocity ^ 2 + 2 * Acceleration * Distance
           Acceleration = (Final Velocity ^ 2 - Initial Velocity ^ 2) / (2 * Distance)
        */
        return (Math.pow(finalGroundSpeed, 2) - Math.pow(initialGroundSpeed, 2)) / (2 * distance);
    }

    private float getDistanceInMeters() {
         float distance = this.destination.getDistanceInMeters() - this.origin.getDistanceInMeters();
        return distance < 0 ? distance * -1 : distance; // Returns a positive number.
    }

    public String toString() {
        String route = this.origin.getId() + " -> " + this.destination.getId();
        return "FlightLeg(" + route + " | " + this.getDistance() + "NM" + ")";
    }

    public float getDistance() {
         float distance = this.destination.getDistance() - this.origin.getDistance();
        return distance < 0 ? distance * -1 : distance; // Returns a positive number.
    }

    /**
    * Returns the estimated time in minutes.
    */
    public float getEstimatedTime() {
        float initialGroundSpeed = this.origin.getGroundSpeedInMetersPerSecond();
        float finalGroundSpeed = this.destination.getGroundSpeedInMetersPerSecond();
        double acceleration = this.getEstimatedAcceleration();

        /* Final Velocity Formula:
           Final Velocity = Acceleration * Time + Initial Velocity
           Time = (Final Velocity - Initial Velocity) / Acceleration
        */
        return (float) ((finalGroundSpeed - initialGroundSpeed) / acceleration / 60);
    }

    /**
    * Returns the estimated vertical speed in feet per minute.
    */
    public float getEstimatedVerticalSpeed() {
        float altitude = this.origin.getAltitude() - this.destination.getAltitude();
        return (altitude / this.getEstimatedTime()) * -1;
    }

    public Waypoint getOrigin() {
        return this.origin;
    }

    public Waypoint getDestination() {
        return this.destination;
    }
}
