package flight;

import java.util.ArrayList;
import java.util.List;

public class Flight {
	private List<Waypoint> waypoints = new ArrayList<>();
	private float averageFuelConsumption;

	public Flight(float averageFuelConsumption) {
		this.averageFuelConsumption = averageFuelConsumption;
	}

	private FlightLeg getFlightLeg(int index) {
		return new FlightLeg(this.waypoints.get(index), this.waypoints.get(index + 1));
	}

	public void addWaypoint(Waypoint waypoint) {
		this.waypoints.add(waypoint);
	}

	public List<FlightLeg> getLegs() {
		List<FlightLeg> legs = new ArrayList<>();

		for (int index = 0; index < this.waypoints.size() - 1; index++) {
			legs.add(this.getFlightLeg(index));
		}
		return legs;
	}

	public float getEstimatedFuel(FlightLeg flightLeg) {
		// Converts the consumption per hour to consumption per minute.
		float averageFuelConsumption = this.averageFuelConsumption / 60;
		return averageFuelConsumption * flightLeg.getEstimatedTime();
	}

	public float getEstimatedFuel() {
		float total = 0;

		for (FlightLeg flightLeg : this.getLegs()) {
			total += this.getEstimatedFuel(flightLeg);
		}
		return total;
	}

	public float getEstimatedTime() {
		float total = 0;

		for (FlightLeg flightLeg : this.getLegs()) {
			total += flightLeg.getEstimatedTime();
		}
		return total;
	}
}
