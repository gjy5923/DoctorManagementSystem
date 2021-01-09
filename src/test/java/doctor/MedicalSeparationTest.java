package doctor;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class MedicalSeparationTest {

    @Test
    public void leaving() {
        Connection conn=new SQLConnection().conn;
        try {
            PreparedStatement pstmt=conn.prepareStatement("delete from message where id='00000000'");
            int i=pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}