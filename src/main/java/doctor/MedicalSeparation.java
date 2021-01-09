package doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

/**
 * 医生离职界面————经过输入的id和姓名查找相应的人员信息，可以先确认信息
 * 在决定是否进行办理离职操作
 */
public class MedicalSeparation extends SQLConnection implements KeyListener {
    /**
     * 成员变量
     */
    JFrame frames=new JFrame("医生离职");

    JPanel checkPanel=new JPanel();//离职信息确认区
    JPanel basicMessagePanel=new JPanel();//基本信息区
    JPanel btnPanel=new JPanel();//按钮区

    //离职信息确认区组件
    JLabel departuresMes=new JLabel("要离职的人员信息");
    JLabel idLabel=new JLabel("医生ID:");
    JLabel nameLabel=new JLabel("姓名:");
    JTextField idField=new JTextField(15);
    JTextField nameField=new JTextField(15);
    JButton lookBtn=new JButton("查看");

    //基本信息区组件
    JLabel idLabel1=new JLabel("医生ID：");
    JLabel idMes=new JLabel();
    JLabel nameLabel1=new JLabel("姓名：");
    JLabel nameMes=new JLabel();
    JLabel ageLabel=new JLabel("年龄：");
    JLabel ageMes=new JLabel();
    JLabel sexLabel=new JLabel("性别：");
    JLabel sexMes=new JLabel();
    JLabel telLabel=new JLabel("联系方式：");
    JLabel telMes=new JLabel();
    JLabel departmentLabel=new JLabel("科室：");
    JLabel departmentMes=new JLabel();

    //按钮区组件
    JButton quitBtn=new JButton("离职");
    JButton cancelBtn=new JButton("取消");

    //图片
    ImageIcon img=new ImageIcon("e://textfile//text//leave.jpg");
    JLabel bg=new JLabel(img);
    public MedicalSeparation(){

        frames.setLayout(null);
        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        frames.setIconImage(image);
        leavingConfirm();//查找信息确认方法调用
        basicMessage();//基本信息显示方法调用
        button();//按钮调用
        frames.add(bg);
        bg.setBounds(0,0,500,80);
        frames.setSize(500,400);
        frames.setLocationRelativeTo(null);
        frames.setResizable(false);
        frames.setVisible(true);
        //添加键盘事件
        idField.addKeyListener(this);
        nameField.addKeyListener(this);
        frames.addKeyListener(this);

        //查找按钮添加监听事件
        lookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               looking();//医生id和姓名在数据库中的查找和详细信息的显示
            }
        });
        //给离职按钮添加监听事件
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaving();
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
     *离职人员信息查找
     */
    public void leavingConfirm(){
        //离职信息确认区组件添加
        checkPanel.setLayout(null);
        checkPanel.add(departuresMes);
        checkPanel.add(idLabel);
        checkPanel.add(idField);
        checkPanel.add(nameLabel);
        checkPanel.add(nameField);
        checkPanel.add(lookBtn);

        //离职信息查看确认区中组件摆放
        departuresMes.setBounds(200,0,100,30);
        idLabel.setBounds(50,40,50,30);
        idField.setBounds(100,40,80,30);
        nameLabel.setBounds(200,40,50,30);
        nameField.setBounds(250,40,80,30);
        lookBtn.setBounds(360,40,80,30);
        checkPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        checkPanel.setOpaque(false);
        frames.add(checkPanel);
        checkPanel.setBounds(0,80,500,80);
    }

    /**
     *查找出来的离职人员的基本信息
     */
    public void basicMessage(){
        //把组件添加到基本信息容器中
        basicMessagePanel.setLayout(null);
        basicMessagePanel.add(idLabel1);
        basicMessagePanel.add(idMes);
        basicMessagePanel.add(nameLabel1);
        basicMessagePanel.add(nameMes);
        basicMessagePanel.add(ageLabel);
        basicMessagePanel.add(ageMes);
        basicMessagePanel.add(sexLabel);
        basicMessagePanel.add(sexMes);
        basicMessagePanel.add(telLabel);
        basicMessagePanel.add(telMes);
        basicMessagePanel.add(departmentLabel);
        basicMessagePanel.add(departmentMes);
        basicMessagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        //基本信息容器中组件摆放
        idLabel1.setBounds(130,20,50,30);
        idMes.setBounds(190,20,60,30);
        nameLabel1.setBounds(280,20,50,30);
        nameMes.setBounds(340,20,50,30);
        ageLabel.setBounds(130,60,50,30);
        ageMes.setBounds(190,60,50,30);
        sexLabel.setBounds(280,60,50,30);
        sexMes.setBounds(340,60,50,30);
        telLabel.setBounds(130,100,80,30);
        telMes.setBounds(190,100,80,30);
        departmentLabel.setBounds(280,100,50,30);
        departmentMes.setBounds(340,100,50,30);
        basicMessagePanel.setBounds(0,160,500,230);
        basicMessagePanel.setOpaque(false);
        frames.add(basicMessagePanel);
    }

    /**
     * 医生id和姓名在数据库中的查找和详细信息的显示
     */
   public void looking(){
       //调取数据库读取查询到的数据
       try {
           if(idField.getText().equals("")){//判断医生ID是否为空，为空则返回提示信息
               JOptionPane.showMessageDialog(null,"医生ID不可为空");
           }else{//不为空时，判断是否有该人员，有时，进行删除并返回删除成功信息
               rs=new SQLConnection().stmt.executeQuery(
                       "select id,name,age,sex,telphone,department from message where id='"+String.valueOf(idField.getText())
                               +"'and name='"+String.valueOf(nameField.getText())+"'");
               if(rs.next()){
                   idMes.setText(rs.getString(1));
                   nameMes.setText(rs.getString(2));
                   ageMes.setText(String.valueOf(rs.getInt(3)));
                   sexMes.setText(rs.getString(4));
                   telMes.setText(rs.getString(5));
                   departmentMes.setText(rs.getString(6));
               }else{//没有时，返回提示信息
                   JOptionPane.showMessageDialog(null,"未找到此人员或信息错误");
               }
           }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
   }

    /**
     * 对查找区域的判断，查找到了就可离职
     */
   public void leaving(){
       try {
           if(idField.getText().equals("")){//判断id是否为空，为空则返回提示信息
               JOptionPane.showMessageDialog(null,"医生ID不可为空");
           }else {//不为空时，判断是否有该人员，有时，进行删除并返回删除成功信息
               rs = new SQLConnection().stmt.executeQuery(
                       "select * from message where id='" + String.valueOf(idField.getText())
                               + "'and name='" + String.valueOf(nameField.getText()) + "'");
               if(rs.next()){
                   new SQLConnection().stmt.executeUpdate("delete from message where id='"+String.valueOf(idField.getText())+"'");
                   JOptionPane.showMessageDialog(null,"删除成功！");
               }else{//没有时，返回提示信息
                   JOptionPane.showMessageDialog(null,"未找到此人员或信息错误");
               }
           }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
   }
    /**
     * 离职界面的按钮
     */
    public void button(){
        //按钮区
        btnPanel.setLayout(null);
        btnPanel.add(quitBtn);
        btnPanel.add(cancelBtn);
        quitBtn.setBounds(100,10,100,30);
        cancelBtn.setBounds(300,10,100,30);
        btnPanel.setOpaque(false);
        frames.add(btnPanel);
        btnPanel.setBounds(0,310,500,90);
    }
    //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER) {
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
