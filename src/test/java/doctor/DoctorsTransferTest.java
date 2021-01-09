package doctor;

import junit.framework.TestCase;
import org.junit.Test;

import java.sql.*;


public class DoctorsTransferTest extends TestCase {
    @Test
    public void testLooking() {

        try {
            Connection conn=new SQLConnection().conn;
            PreparedStatement pstmt=conn.prepareStatement("SELECT * FROM MESSAGE where id='"+"00000000"+"'");
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                String id=rs.getString("id");
                String name=rs.getString("name");
                String idNumber=rs.getString("idnumber");
                int age=rs.getInt("age");
                String sex=rs.getString("sex");
                String telphone=rs.getString("telphone");
                String department=rs.getString("department");
                Date date=rs.getDate("worktime");
                String address=rs.getString("address");
                String hospital=rs.getString("hospital");
                System.out.println(id+","+name+","+idNumber+","+age+","+sex+","+telphone+","+department+","+
                date+","+address+","+hospital);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testTransfer() {
        Connection conn=new SQLConnection().conn;
        try {
            PreparedStatement pstmt=conn.prepareStatement("UPDATE message SET department='"+"外科"+"' where id='00000000'");
           int i=pstmt.executeUpdate();
            System.out.println(i);
            conn.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}