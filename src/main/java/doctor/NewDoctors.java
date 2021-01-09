package doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * 新医生入职界面
 */
public class NewDoctors extends SQLConnection implements KeyListener {
    JFrame functionFrame =new JFrame("新医生入职");
    JLabel mes=new JLabel("入职信息添加");
    JLabel idLabel=new JLabel("医生ID:");
    JLabel nameLabel=new JLabel("姓名:");
    JLabel ageLabel=new JLabel("年龄:");
    JLabel departmentLabel=new JLabel("科室:");
    JLabel sexLabel=new JLabel("性别:");
    JLabel idNumber=new JLabel("身份证号:");
    JLabel telLabel=new JLabel("联系方式:");
    JLabel workTimeLabel=new JLabel("工作时间:");
    JLabel yearLabel=new JLabel("年");
    JLabel monthLabel=new JLabel("月");
    JLabel dayLabel=new JLabel("日");
    JLabel addressLabel=new JLabel("所在地:");
    JLabel affiliatedHospital=new JLabel("所属医院:");

    final JTextField idField=new JTextField(15);
    final JTextField nameField=new JTextField(15);
    final JTextField ageField=new JTextField(15);
    final JTextField departmentField=new JTextField(15);
    JTextField idNumberField=new JTextField(18);
    final JTextField telField=new JTextField(15);
    JTextField workTime_Year=new JTextField(5);
    JTextField workTime_Month=new JTextField(5);
    JTextField workTime_Day=new JTextField(5);
    JTextField addressField=new JTextField(30);

    JTextField affiliatedHospitalField=new JTextField(10);
    final JRadioButton menRbtn=new JRadioButton("男",true);
    JRadioButton womenRbtn=new JRadioButton("女");
    JButton confirmBtn=new JButton("确认");
    JButton resetBtn=new JButton("重置");
    //单选按钮组
    ButtonGroup group=new ButtonGroup();

    /**
     * 构造方法————调用addDoctor()方法、界面中的按钮监听事件
     */
    public NewDoctors(){

        functionFrame.setLayout(null);
        functionFrame.setSize(380,450);
        functionFrame.setLocationRelativeTo(null);

        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        functionFrame.setIconImage(image);

        functionFrame.add(mes);
        //设置开头信息
        mes.setBounds(30,0,300,40);
        mes.setFont(new Font("隶书",Font.BOLD,25));
        mes.setForeground(Color.blue);
        mes.setOpaque(true);
        mes.setHorizontalAlignment(JLabel.CENTER);//设置标签居中
        //调用插入信息的组件添加和摆放方法
        addDoctor();
        functionFrame.setVisible(true);//设置窗体可见
        functionFrame.setResizable(false);//设置窗口不可改变
        //添加键盘事件
        idField.addKeyListener(this);
        nameField.addKeyListener(this);
        ageField.addKeyListener(this);
        departmentField.addKeyListener(this);
        idNumberField.addKeyListener(this);
        telField.addKeyListener(this);
        workTime_Year.addKeyListener(this);
        workTime_Month.addKeyListener(this);
        workTime_Day.addKeyListener(this);
        addressField.addKeyListener(this);
        affiliatedHospitalField.addKeyListener(this);
        functionFrame.addKeyListener(this);

        //给确认按钮添加监听事件
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageInput();//信息的判断以及存入数据库的方法调用

            }
        });
        //给重置按钮添加监听事件
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //所有信息置空
                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                departmentField.setText("");
                idNumberField.setText("");
                telField.setText("");
                workTime_Day.setText("");
                workTime_Month.setText("");
                workTime_Year.setText("");
                addressField.setText("");
                affiliatedHospitalField.setText("");
            }
        });
    }

    /**
     *添加人员的组件
     */
    public void addDoctor(){
        //医生ID
        functionFrame.add(idLabel);
        functionFrame.add(idField);
        idLabel.setFont(new Font("黑体",Font.BOLD,15));
        idLabel.setBounds(40,50,60,25);
        idField.setBounds(100,50,80,25);
        //姓名
        functionFrame.add(nameLabel);
        functionFrame.add(nameField);
        nameLabel.setFont(new Font("黑体",Font.BOLD,15));
        nameLabel.setBounds(200,50,50,25);
        nameField.setBounds(250,50,80,25);
        //年龄
        functionFrame.add(ageLabel);
        functionFrame.add(ageField);
        ageLabel.setFont(new Font("黑体",Font.BOLD,15));
        ageLabel.setBounds(40,90,50,25);
        ageField.setBounds(100,90,80,25);
        //科室
        functionFrame.add(departmentLabel);
        functionFrame.add(departmentField);
        departmentLabel.setFont(new Font("黑体",Font.BOLD,15));
        departmentLabel.setBounds(200,90,50,25);
        departmentField.setBounds(250,90,80,25);
        //性别
        group.add(menRbtn);
        group.add(womenRbtn);
        functionFrame.add(sexLabel);
        functionFrame.add(menRbtn);
        functionFrame.add(womenRbtn);
        sexLabel.setFont(new Font("黑体",Font.BOLD,15));
        menRbtn.setFont(new Font("黑体",Font.BOLD,15));
        womenRbtn.setFont(new Font("黑体",Font.BOLD,15));
        sexLabel.setBounds(40,130,50,25);
        menRbtn.setBounds(100,130,50,25);
        womenRbtn.setBounds(160,130,50,25);
        //身份证号
        functionFrame.add(idNumber);
        functionFrame.add(idNumberField);
        idNumber.setFont(new Font("黑体",Font.BOLD,15));
        idNumber.setBounds(30,170,80,25);
        idNumberField.setBounds(100,170,200,25);
        //联系方式
        functionFrame.add(telLabel);
        functionFrame.add(telField);
        telLabel.setFont(new Font("黑体",Font.BOLD,15));
        telLabel.setBounds(30,210,80,25);
        telField.setBounds(100,210,150,25);
        //工作时间
        functionFrame.add(workTimeLabel);
        functionFrame.add(workTime_Year);
        functionFrame.add(yearLabel);
        functionFrame.add(workTime_Month);
        functionFrame.add(monthLabel);
        functionFrame.add(workTime_Day);
        functionFrame.add(dayLabel);
        workTimeLabel.setFont(new Font("黑体",Font.BOLD,15));
        yearLabel.setFont(new Font("黑体",Font.BOLD,15));
        monthLabel.setFont(new Font("黑体",Font.BOLD,15));
        dayLabel.setFont(new Font("黑体",Font.BOLD,15));
        workTimeLabel.setBounds(30,250,80,25);
        workTime_Year.setBounds(100,250,50,25);
        yearLabel.setBounds(155,250,20,25);
        workTime_Month.setBounds(175,250,50,25);
        monthLabel.setBounds(230,250,20,25);
        workTime_Day.setBounds(255,250,50,25);
        dayLabel.setBounds(310,250,20,25);

        //所在地
        functionFrame.add(addressLabel);
        functionFrame.add(addressField);
        addressLabel.setFont(new Font("黑体",Font.BOLD,15));
        addressLabel.setBounds(30,290,80,25);
        addressField.setBounds(100,290,250,25);
        //所属医院
        functionFrame.add(affiliatedHospital);
        functionFrame.add(affiliatedHospitalField);
        affiliatedHospital.setFont(new Font("黑体",Font.BOLD,15));
        affiliatedHospital.setBounds(30,330,80,25);
        affiliatedHospitalField.setBounds(100,330,250,25);
        //设置按钮位置
        functionFrame.add(confirmBtn);
        functionFrame.add(resetBtn);
        confirmBtn.setBounds(50,370,80,30);
        resetBtn.setBounds(230,370,80,30);
    }

    /**
     * 医生ID是否存在的判断以及所有信息不可为空的执行并且把信息保存到数据库中
     */
    public void messageInput(){
        //把键入的所有信息存入到临时空间中
        String idTemp=idField.getText();
        String nameTemp=nameField.getText();
        String idNumberTemp=idNumberField.getText();
        String sexTemp;
        if(menRbtn.isSelected())//判断是否为男
            sexTemp="男";
        else
            sexTemp="女";
        String tleTemp=telField.getText();
        String departmentTemp=departmentField.getText();
        String addressTemp=addressField.getText();
        String hospitalTemp=affiliatedHospitalField.getText();
        //因为每次添加进去的日期总比输的要少1，所以加1
        int t;
        int ageTemp;
        String workTimeTemp;
        if(idNumberTemp.equals("")||nameTemp.equals("")||departmentTemp.equals("")||idNumberTemp.equals("")||ageField.equals("")||
                tleTemp.equals("")||workTime_Year.getText().equals("")||workTime_Month.equals("")||workTime_Day.equals("")||
                addressTemp.equals("")||hospitalTemp.equals("")){
            JOptionPane.showMessageDialog(null,"信息不可为空");
        }else{
            ageTemp=Integer.valueOf(ageField.getText());
            //因为每次添加进去的日期总比输的要少1，所以加1
            t=Integer.valueOf(workTime_Day.getText())+1;
            workTimeTemp=workTime_Year.getText()+"-"+workTime_Month.getText()+"-"+String.valueOf(t);

            String sql1="select * from message where ID='"+idTemp+"'";
            try {
                rs=stmt.executeQuery(sql1);
                if(rs.next()){//如何数据库中年存在新添加的医生ID,弹出提示信息
                    JOptionPane.showMessageDialog(null,"该医生ID已存在");
                }else{

                    //把新添加的信息全部存入数据库中
                    String sql="insert into message(ID,name,idnumber,age,sex,telphone,department,worktime,address,hospital) values(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstmt=new SQLConnection().conn.prepareStatement(sql);
                    pstmt.setString(1,idTemp);
                    pstmt.setString(2,nameTemp);
                    pstmt.setString(3,idNumberTemp);
                    pstmt.setInt(4,ageTemp);
                    pstmt.setString(5,sexTemp);
                    pstmt.setString(6,tleTemp);
                    pstmt.setString(7,departmentTemp);
                    pstmt.setDate(8, Date.valueOf(workTimeTemp));
                    pstmt.setString(9,addressTemp);
                    pstmt.setString(10,hospitalTemp);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null,"入职成功");
                    pstmt.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER) {
            confirmBtn.doClick();
        }
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            functionFrame.dispose();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
