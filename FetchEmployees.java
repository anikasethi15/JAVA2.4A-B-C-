import java.sql.*;

public class FetchEmployees {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb"; // change to your DB name
        String user = "root";
        String password = "yourpassword";

        try {
            // 1️⃣ Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Establish Connection
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database Successfully!");

            // 3️⃣ Create Statement
            Statement stmt = con.createStatement();

            // 4️⃣ Execute Query
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

            // 5️⃣ Display Results
            System.out.println("Employee Records:");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(id + " | " + name + " | " + salary);
            }

            // 6️⃣ Close Resources
            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
