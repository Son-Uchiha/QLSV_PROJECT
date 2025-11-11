// DBConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Đổi lại URL, USER, PASS cho đúng với MySQL của cậu
    private static final String URL  = "jdbc:mysql://localhost:3306/QLSV";
    private static final String USER = "root";
    private static final String PASS = "Son21042005@";

    // Nạp driver MySQL (phòng trường hợp cần)
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Hàm trả về 1 Connection để dùng trong CRUD
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
