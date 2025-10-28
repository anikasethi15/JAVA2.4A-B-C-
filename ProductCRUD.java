import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root";
        String password = "yourpassword";
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // ✅ Transaction control

            while (true) {
                System.out.println("\n==== Product Management Menu ====");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: // CREATE
                        System.out.print("Enter Product Name: ");
                        String name = sc.next();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        PreparedStatement ps1 = con.prepareStatement(
                            "INSERT INTO Product(ProductName, Price, Quantity) VALUES(?,?,?)"
                        );
                        ps1.setString(1, name);
                        ps1.setDouble(2, price);
                        ps1.setInt(3, qty);
                        ps1.executeUpdate();
                        con.commit();
                        System.out.println("✅ Product Added Successfully!");
                        break;

                    case 2: // READ
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM Product");
                        System.out.println("ProductID | Name | Price | Quantity");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " +
                                               rs.getDouble(3) + " | " + rs.getInt(4));
                        }
                        break;

                    case 3: // UPDATE
                        System.out.print("Enter Product ID to Update: ");
                        int pid = sc.nextInt();
                        System.out.print("Enter New Price: ");
                        double newPrice = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement(
                            "UPDATE Product SET Price=? WHERE ProductID=?"
                        );
                        ps2.setDouble(1, newPrice);
                        ps2.setInt(2, pid);
                        int rows = ps2.executeUpdate();
                        if (rows > 0) {
                            con.commit();
                            System.out.println("✅ Product Updated!");
                        } else System.out.println("❌ Product Not Found.");
                        break;

                    case 4: // DELETE
                        System.out.print("Enter Product ID to Delete: ");
                        int did = sc.nextInt();

                        PreparedStatement ps3 = con.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                        ps3.setInt(1, did);
                        int del = ps3.executeUpdate();
                        if (del > 0) {
                            con.commit();
                            System.out.println("✅ Product Deleted!");
                        } else System.out.println("❌ Product Not Found.");
                        break;

                    case 5:
                        con.close();
                        System.out.println("👋 Exiting... Goodbye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
