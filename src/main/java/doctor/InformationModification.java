package doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * 医生信息修改————经过输入的id和姓名查找相应的人员信息，可以先确认信息
 *在进行医生的信息修改操作
 */
public class InformationModification extends SQLConnection implements KeyListener{
    JFrame frames=new JFrame("信息修改");

    //容器
    JPanel confirmPanel=new JPanel();//信息确认区
    JPanel alterPanel=new JPanel();//信息修改区
    JPanel btnPanel=new JPanel();//按钮区

    //信息确认区组件
    JLabel idLabel=new JLabel("医生ID:");
    JLabel nameLabel=new JLabel("姓名:");
    JTextField idField=new JTextField(15);
    JTextField nameField=new JTextField(15);
    JButton lookBtn=new JButton("查看");

    //信息修改区
    JLabel nameLabel1=new JLabel("姓名：");
    JTextField nameMes=new JTextField(10);
    JLabel sexLabel=new JLabel("性别：");
    JTextField sexMes=new JTextField(5);
    JLabel idNumber=new JLabel("身份证号：");
    JTextField idNumberField=new JTextField(18);
    JLabel telLabel=new JLabel("联系方式：");
    JTextField telField=new JTextField(11);
    JLabel workTime=new JLabel("工作时间：");
    JLabel yearLabel=new JLabel("年");
    JLabel monthLabel=new JLabel("月");
    JLabel dayLabel=new JLabel("日");
    //工作时间的年
    String []year=new String[42];
    JComboBox yearBox;
    //工作时间的月
    String[] month={"1","2","3","4","5","6","7","8","9","10","11","12"};
    JComboBox monthBox=new JComboBox(month);
    //工作时间的日定义
    String []days=new String[31];
    JComboBox daysBox;
    JLabel address=new JLabel("所在地：");
    JTextField addressField=new JTextField(30);
    JLabel affiliatedHospital=new JLabel("所属医院：");
    JTextField affiliatedHospitalField=new JTextField(10);

    //按钮区组件
    JButton alterBtn=new JButton("修改");
    JButton cancelBtn=new JButton("取消");
    //图片
    ImageIcon img=new ImageIcon("e://textfile//text//altermessage.jpg");
    JLabel bg=new JLabel(img);

    public InformationModification(){
        frames.setLayout(null);
        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        frames.setIconImage(image);
        messageConfirm();
        messageAlter();
        button();
        //把带有图片的标签加入到界面中
        frames.add(bg);
        bg.setBounds(0,0,450,520);
        frames.setSize(450,520);
        frames.setLocationRelativeTo(null);
        frames.setResizable(false);
        frames.setVisible(true);
        //添加键盘事件
        idField.addKeyListener(this);
        nameField.addKeyListener(this);
        frames.addKeyListener(this);

        //给查看按钮添加监听事件
        lookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               looking();
            }
        });

        //给修改按钮添加监听事件
        alterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //调取数据库读取查询到的数据
                try {
                    if(idField.getText().equals("")||nameField.getText().equals("")){//判断医生ID是否为空，为空则返回提示信息
                        JOptionPane.showMessageDialog(null,"医生ID及姓名不可为空");
                    }else{//不为空时，判断是否有该人员，有时，进行删除并返回删除成功信息

                        stmt.executeUpdate("update message set name='"+nameMes.getText()+"',sex='"+sexMes.getText()+"',idnumber='"
                                +idNumberField.getText()+"',telphone='"+telField.getText()+"',worktime='"+Date.valueOf(yearBox.getSelectedItem()+"-"+monthBox.getSelectedItem()+"-"+daysBox.getSelectedItem())
                                +"',address='"+addressField.getText()+"',hospital='"+affiliatedHospitalField.getText()+"' where id='"+idField.getText()+"'");

                    }
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        //给取消按钮添加监听事件
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //关闭页面
                frames.setVisible(false);
            }
        });
    }

    /**
     *根据输入的医生ID及姓名,查找到相应的信息并显示在界面上
     */

    public void messageConfirm(){
        //修改信息确认区组件添加及摆放
        confirmPanel.setLayout(null);
        confirmPanel.add(idLabel);
        confirmPanel.add(idField);
        confirmPanel.add(nameLabel);
        confirmPanel.add(nameField);
        confirmPanel.add(lookBtn);
        //组件摆放
        idLabel.setBounds(50,40,50,30);
        idField.setBounds(100,40,80,30);
        nameLabel.setBounds(200,40,50,30);
        nameField.setBounds(250,40,80,30);
        lookBtn.setBounds(360,42,60,25);
        confirmPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        frames.add(confirmPanel);
        confirmPanel.setOpaque(false);
        confirmPanel.setBounds(3,80,430,90);

    }

    /**
     * 查找数据库中是否存在所输入的医生ID和姓名
     */
    public void looking(){
        //调取数据库读取查询到的数据
        try {
            if(idField.getText().equals("")||nameField.getText().equals("")){//判断医生ID是否为空，为空则返回提示信息
                JOptionPane.showMessageDialog(null,"医生ID及姓名不可为空");
            }else{//不为空时，判断是否有该人员，有时，进行删除并返回删除成功信息
                rs=new SQLConnection().stmt.executeQuery(
                        "select * from message where id='"+String.valueOf(idField.getText())
                                +"'and name='"+String.valueOf(nameField.getText())+"'");
                if(rs.next()){//得到所查到的数据并把数据显示到界面中
                    nameMes.setText(rs.getString(2));
                    idNumberField.setText(rs.getString(3));
                    sexMes.setText(rs.getString(5));
                    telField.setText(rs.getString(6));
                    //获取数据库中的Date型数据
                    Calendar c=Calendar.getInstance();
                    c.setTime(rs.getDate(8));
                    System.out.println(String.valueOf(c.get(Calendar.MONTH)));
                    yearBox.setSelectedItem(String.valueOf(c.get(Calendar.YEAR)));
                    monthBox.setSelectedItem(String.valueOf(c.get(Calendar.MONTH)+1));
                    daysBox.setSelectedItem(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
                    addressField.setText(rs.getString(9));
                    affiliatedHospitalField.setText(rs.getString(10));

                }else{//没有时，返回提示信息
                    JOptionPane.showMessageDialog(null,"未找到此人员或信息错误");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     *根据查找出来的信息对信息进行修改
     */
    public void messageAlter(){
        //工作时间的年
        for(int i=1980,j=0;i<=2021;i++,j++){
            year[j]= String.valueOf(i);
        }

        yearBox=new JComboBox(year);
        //工作时间的日
        for(int i=0;i<31;i++){//日期天数
            days[i]=String.valueOf(i+1);
        }
        daysBox=new JComboBox(days);

        //信息修改区组件添加及摆放
        alterPanel.setLayout(null);
        alterPanel.add(nameLabel1);
        alterPanel.add(nameMes);
        alterPanel.add(sexLabel);
        alterPanel.add(sexMes);
        alterPanel.add(idNumber);
        alterPanel.add(idNumberField);
        alterPanel.add(telLabel);
        alterPanel.add(telField);
        alterPanel.add(workTime);
        alterPanel.add(yearBox);
        alterPanel.add(yearLabel);
        alterPanel.add(monthBox);
        alterPanel.add(monthLabel);
        alterPanel.add(daysBox);
        alterPanel.add(dayLabel);
        alterPanel.add(address);
        alterPanel.add(addressField);
        alterPanel.add(affiliatedHospital);
        alterPanel.add(affiliatedHospitalField);

        alterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        yearLabel.setFont(new Font("黑体",Font.PLAIN,15));
        monthLabel.setFont(new Font("黑体",Font.PLAIN,15));
        dayLabel.setFont(new Font("黑体",Font.PLAIN,15));
        nameLabel1.setBounds(70,10,40,30);
        nameMes.setBounds(120,10,80,30);
        sexLabel.setBounds(280,10,40,30);
        sexMes.setBounds(320,10,50,30);
        idNumber.setBounds(50,55,80,30);
        idNumberField.setBounds(120,55,250,30);
        telLabel.setBounds(50,100,80,30);
        telField.setBounds(120,100,150,30);
        workTime.setBounds(50,145,80,30);
        yearBox.setBounds(120,145,60,30);
        yearLabel.setBounds(192,145,20,30);
        monthBox.setBounds(220,145,60,30);
        monthLabel.setBounds(285,145,20,30);
        daysBox.setBounds(310,145,60,30);
        dayLabel.setBounds(375,145,20,30);
        address.setBounds(50,190,50,30);
        addressField.setBounds(120,190,300,30);
        affiliatedHospital.setBounds(50,225,80,30);
        affiliatedHospitalField.setBounds(120,225,200,30);

        frames.add(alterPanel);
        alterPanel.setOpaque(false);
        alterPanel.setBounds(0,170,500,260);
    }

    /**
     *信息修改界面的按钮所在
     */
    public void button(){
        btnPanel.setLayout(null);
        //按钮区组件添加
        btnPanel.add(alterBtn);
        btnPanel.add(cancelBtn);
        alterBtn.setBounds(80,10,100,30);
        cancelBtn.setBounds(250,10,100,30);

       btnPanel.setBounds(0,430,500,50);
       btnPanel.setOpaque(false);
        frames.add(btnPanel);

    }

   //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            lookBtn.doClick();
        }
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            frames.dispose();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
