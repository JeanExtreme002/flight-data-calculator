import flight.Flight;
import flight.FlightLeg;
import flight.Waypoint;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Calculator {
    public static Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Average fuel consumption (per hour): ");
        float averageFuelConsumption = Float.parseFloat(Calculator.inputScanner.nextLine().replace(",", "."));

        System.out.println("\nInsert the route waypoints:\n");
        Flight flight = Calculator.createFlight(averageFuelConsumption);

        try (PrintStream printStream = new PrintStream(args.length == 0 ? "route_data.txt" : args[0])) {
            Calculator.writeRouteDataTo(printStream, flight);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            Calculator.inputScanner.nextLine();
        }
    }

    public static Flight createFlight(float averageFuelConsumption) {
        Flight flight = new Flight(averageFuelConsumption);

        do {
            System.out.println("------------------------------------------");

            System.out.print("Waypoint ID: ");
            String waypointId = Calculator.inputScanner.nextLine();

            System.out.print("Distance (Nautical Miles): ");
            float waypointDistance = Float.parseFloat(Calculator.inputScanner.nextLine().replace(",", "."));

            System.out.print("Altitude (Feet): ");
            int waypointAltitude = Integer.parseInt(Calculator.inputScanner.nextLine());

            System.out.print("Ground Speed (Knots): ");
            int waypointVelocity = Integer.parseInt(Calculator.inputScanner.nextLine());

            Waypoint waypoint = new Waypoint(waypointId, waypointDistance, waypointAltitude, waypointVelocity);
            flight.addWaypoint(waypoint);

            System.out.print("Add new waypoint? (Y/N):  ");
        } while (Calculator.inputScanner.nextLine().toUpperCase().equals("Y"));

        return flight;
    }

    public static void writeRouteDataTo(PrintStream file, Flight flight) {
        String format = "| %-16s | %-8s | %-10s | %-14s | %-14s |";

        file.println(" ----------------------------------------------------------------------------");
        file.println(String.format(format, "Route", "ETE", "Distance", "Vertical Speed", "Estimated Fuel"));
        file.println("|----------------------------------------------------------------------------|");

        for (FlightLeg leg : flight.getLegs()) {
            String route = String.format("%-6s -> %s", leg.getOrigin().getId(), leg.getDestination().getId());
            String estimatedTime = String.format("%-6.1f m", leg.getEstimatedTime());
            String distance = String.format("%-7.1f NM", leg.getDistance());
            String verticalSpeed = String.format("%-9d Ft/m", Math.round(leg.getEstimatedVerticalSpeed()));
            String estimatedFuel = String.format("%d", Math.round(flight.getEstimatedFuel(leg)));

            file.println(String.format(format, route, estimatedTime, distance, verticalSpeed, estimatedFuel));
        }

        file.println(" ----------------------------------------------------------------------------");
        file.println();
        file.println("FLIGHT ETA: " + String.format("%.1f m", flight.getEstimatedTime()));
        file.println("TOTAL FUEL: " + String.format("%d", Math.round(flight.getEstimatedFuel())));
    }
}
