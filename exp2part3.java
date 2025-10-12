import java.sql.*;
import java.util.Scanner;

public class JDBCApplication {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/companydb";
        String user = "root";
        String password = "your_password";
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("\n--- Employee Data ---");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
            System.out.println("EmpID\tName\tSalary");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpID") + "\t" + rs.getString("Name") + "\t" + rs.getDouble("Salary"));
            }
            rs.close();
            stmt.close();

            con.setAutoCommit(false);
            int choice;
            do {
                System.out.println("\n--- Product Management Menu ---");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Product Name: ");
                        String name = sc.next();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();
                        String insertSQL = "INSERT INTO Product(ProductName, Price, Quantity) VALUES(?, ?, ?)";
                        PreparedStatement ps1 = con.prepareStatement(insertSQL);
                        ps1.setString(1, name);
                        ps1.setDouble(2, price);
                        ps1.setInt(3, qty);
                        ps1.executeUpdate();
                        con.commit();
                        System.out.println("Product added successfully!");
                        break;

                    case 2:
                        String selectSQL = "SELECT * FROM Product";
                        Statement st = con.createStatement();
                        ResultSet rs2 = st.executeQuery(selectSQL);
                        System.out.println("ID\tName\tPrice\tQuantity");
                        while (rs2.next()) {
                            System.out.println(rs2.getInt(1) + "\t" + rs2.getString(2) + "\t" + rs2.getDouble(3) + "\t" + rs2.getInt(4));
                        }
                        rs2.close();
                        st.close();
                        break;

                    case 3:
                        System.out.print("Enter Product ID to Update: ");
                        int id = sc.nextInt();
                        System.out.print("Enter new Price: ");
                        double newPrice = sc.nextDouble();
                        String updateSQL = "UPDATE Product SET Price=? WHERE ProductID=?";
                        PreparedStatement ps2 = con.prepareStatement(updateSQL);
                        ps2.setDouble(1, newPrice);
                        ps2.setInt(2, id);
                        ps2.executeUpdate();
                        con.commit();
                        System.out.println("Product updated successfully!");
                        break;

                    case 4:
                        System.out.print("Enter Product ID to Delete: ");
                        int delID = sc.nextInt();
                        String deleteSQL = "DELETE FROM Product WHERE ProductID=?";
                        PreparedStatement ps3 = con.prepareStatement(deleteSQL);
                        ps3.setInt(1, delID);
                        ps3.executeUpdate();
                        con.commit();
                        System.out.println("Product deleted successfully!");
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            } while (choice != 5);

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
