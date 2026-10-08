import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TankerPredictor {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tanker_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Pass@123"; // Replace with your actual MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT name, current_water_liters, daily_avg_consumption FROM societies LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String name = rs.getString("name");
                double currentWater = rs.getDouble("current_water_liters");
                double dailyUsage = rs.getDouble("daily_avg_consumption");

                int daysRemaining = (int) (currentWater / dailyUsage);
                LocalDate bookingDate = LocalDate.now().plusDays(daysRemaining);

                System.out.println("\n------------------------------------------");
                System.out.println("   TANKER NEED PREDICTION SYSTEM");
                System.out.println("------------------------------------------");
                System.out.println("Society Name                : " + name);
                System.out.println("Current Water Reserves     : " + currentWater + " L");
                System.out.println("Daily Average Usage         : " + dailyUsage + " L/day");
                System.out.println("Estimated Days Until Empty  : " + daysRemaining + " days");
                System.out.println("Action Required             : Dispatch Tanker on " + bookingDate);
                System.out.println("------------------------------------------\n");
            }
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
        }
    }
}