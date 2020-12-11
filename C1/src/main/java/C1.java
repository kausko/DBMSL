import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Formatter;

public class C1 {

    public static void main(String[] args) throws SQLException, IOException {
        // write your code here
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JDBC",
                "root",
                "1234"
        );
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Formatter f;
        PreparedStatement ps;
        ResultSet rs;
        String name;
        String email;
        int id;
        while (true) {
            System.out.println("JDBC USERS CONSOLE");
            System.out.println("1. View Users Accounts");
            System.out.println("2. Create User Account");
            System.out.println("3. Edit User Details");
            System.out.println("4. Delete User Account");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1:
                    ps = conn.prepareStatement("select * from users");
                    rs = ps.executeQuery();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    f = new Formatter();
                    f.format("%5s%31s%30s", "ID", "NAME", "EMAIL");
                    System.out.println(f);
                    while (rs.next()) {
                        f = new Formatter();
                        f.format("%5s%31s%30s", rs.getInt("id"), rs.getString("name"), rs.getString("email"));
                        System.out.println(f);
                    }
                    break;
                case 2:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("Enter User's Name: ");
                    name = br.readLine();
                    System.out.print("Enter User's Email: ");
                    email = br.readLine();
                    ps = conn.prepareStatement("insert into users (name, email) values(?, ?)");
                    ps.setString(1, name);
                    ps.setString(2, email);
                    System.out.println(ps.executeUpdate() + " rows created");
                    break;
                case 3:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("Enter the ID of the user to update: ");
                    id = Integer.parseInt(br.readLine());
                    System.out.print("Enter new name: ");
                    name = br.readLine();
                    System.out.print("Enter new email: ");
                    email = br.readLine();
                    ps = conn.prepareStatement("update users set name=?, email=? where id=?");
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setInt(3, id);
                    System.out.println(ps.executeUpdate() + " rows updated");
                    break;
                case 4:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.print("Enter the ID of the user to be deleted: ");
                    id = Integer.parseInt(br.readLine());
                    ps = conn.prepareStatement("delete from users where id=?");
                    ps.setInt(1, id);
                    System.out.println(ps.executeUpdate() + " rows deleted");
                    break;
                case 0:
                    return;
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Invalid option. Please try again");
                    break;
            }
        }
    }
}
