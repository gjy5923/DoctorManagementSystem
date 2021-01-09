package doctor;

import java.sql.*;

/**
 * 数据库连接及读取数据的操作
 */

public class SQLConnection {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    String driverName="com.mysql.cj.jdbc.Driver";
    String uri="jdbc:mysql://localhost:3306/doctorMessage?user=root&password=Gujiyu1234&serverTimezone=GMT";

    public SQLConnection() {
        try {
            Class.forName(driverName);
            conn= DriverManager.getConnection(uri);
            stmt=conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据库中的数据到二维数组中
     */

   public Object [][] readSQL(String sql){

        Object[][] data=new Object[getAmount(sql)][6];
        int i=0;
        try {
            rs=stmt.executeQuery(sql);
            while(rs.next()){
                data[i][0]=rs.getString(1);
                data[i][1]=rs.getString(2);
                data[i][2]=rs.getInt(3);
                data[i][3]=rs.getString(4);
                data[i][4]=rs.getString(5);
                data[i][5]=rs.getString(6);
                i++;
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭数据库连接
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return data;
    }

    /**
     * 获取信息的总行数
     */

   public int getAmount(String sql){
        int rows=0;//总行数
        ResultSet rs1 = null;
        try {
            rs1=stmt.executeQuery(sql);//执行sql语句
            rs1.last();
            rows=rs1.getRow();//获取总行数
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                rs1.close();//关闭结果集
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
}
