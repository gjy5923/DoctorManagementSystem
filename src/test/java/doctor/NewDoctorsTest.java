package doctor;

import org.junit.Test;
import java.sql.*;


public class NewDoctorsTest {
    Connection conn=new SQLConnection().conn;
    @Test
    public void messageInput() {
        try {
            PreparedStatement pstmt=conn.prepareStatement("insert into message(ID,name,idnumber,age,sex,telphone,department,worktime,address,hospital) values(?,?,?,?,?,?,?,?,?,?)");

                //把新添加的信息全部存入数据库中
                pstmt.setString(1,"00000000");
                pstmt.setString(2,"张三");
                pstmt.setString(3,"123456789012345678");
                pstmt.setInt(4,10);
                pstmt.setString(5,"男");
                pstmt.setString(6,"15575239215");
                pstmt.setString(7,"内科");
                pstmt.setDate(8, Date.valueOf("2021-01-09"));
                pstmt.setString(9,"湖南张家界");
                pstmt.setString(10,"人民医院");
                int i=pstmt.executeUpdate();
                System.out.println(i);
                conn.close();
                pstmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}