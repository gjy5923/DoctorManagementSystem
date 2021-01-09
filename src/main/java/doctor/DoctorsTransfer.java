package doctor;

import org.apache.commons.lang3.builder.ToStringExclude;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;


/**
 * 医生调岗功能————经过输入的id和姓名查找相应的人员信息，可以先确认信息
 * 在进行医生的调岗操作
 */
public class DoctorsTransfer extends SQLConnection implements KeyListener {
    JFrame frames=new JFrame("医生调岗");
    JPanel transferPanel=new JPanel();//调岗信息确认区
    JPanel departmentPanel=new JPanel();//科室选择区
    JPanel btnPanel=new JPanel();//按钮区

    //调岗信息组件
    JLabel transferMes=new JLabel("要调岗的人员ID和姓名");
    JLabel idLabel=new JLabel("医生ID:");
    JLabel nameLabel=new JLabel("姓名:");
    JTextField idField=new JTextField(15);
    JTextField nameField=new JTextField(15);
    JButton lookBtn=new JButton("查看");

    //科室选择区组件
    JLabel idLabel1=new JLabel("医生ID：");
    JLabel idMes=new JLabel();
    JLabel nameLabel1=new JLabel("姓名：");
    JLabel nameMes=new JLabel();
    JLabel originalDepartment=new JLabel("原科室：");
    JLabel departmentMes=new JLabel();
    JLabel targetDepartment=new JLabel("要调往的科室：");
    JComboBox box=new JComboBox();
    int count=0;
    String []label=null;

    //按钮区组件
    JButton transferBtn=new JButton("调岗");
    JButton cancelBtn=new JButton("取消");

    //图片
    ImageIcon img=new ImageIcon("e://textfile//text//transfer.jpg");
    JLabel bg=new JLabel(img);
    //医生调岗
    public DoctorsTransfer(){
        frames.setLayout(null);
        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        frames.setIconImage(image);

        bg.setBounds(0,0,500,80);
        //调岗人员信息确认区
        transferConfirm();
        //科室下拉框选项
        Drop_Down_Options();
        //科室区
        departmentSet();
        //按钮区
        button();
        frames.add(bg);
        bg.setBounds(0,0,490,80);
        frames.setSize(500,350);
        frames.setBackground(Color.WHITE);
        frames.setLocationRelativeTo(null);
        frames.setResizable(false);
        frames.setVisible(true);
        //添加键盘事件
        idField.addKeyListener(this);
        nameField.addKeyListener(this);
        frames.addKeyListener(this);

        //给查询按钮添加监听事件
        lookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                looking();
            }
        });
        //给调岗按钮添加监听事件
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();//对查询区的确认以及岗位的调动
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
     *调岗界面信息确认区
     */
    public void transferConfirm(){
        //调岗区域组件添加
        transferPanel.setLayout(null);
        transferPanel.add(transferMes);
        transferPanel.add(idLabel);
        transferPanel.add(idField);
        transferPanel.add(nameLabel);
        transferPanel.add(nameField);
        transferPanel.add(lookBtn);

        //调岗信息组件摆放
        transferMes.setBounds(200,0,130,30);
        idLabel.setBounds(50,40,50,30);
        idField.setBounds(100,40,80,30);
        nameLabel.setBounds(200,40,50,30);
        nameField.setBounds(250,40,80,30);
        lookBtn.setBounds(360,40,80,30);
        frames.add(transferPanel);
        transferPanel.setBounds(0,80,500,80);
    }
    /**
     * 科室区域组件及摆放
     */
    public void departmentSet(){
        //科室区域组件及摆放
        departmentPanel.setLayout(null);
        departmentPanel.add(idLabel1);
        departmentPanel.add(idMes);
        departmentPanel.add(nameLabel1);
        departmentPanel.add(nameMes);
        departmentPanel.add(originalDepartment);
        departmentPanel.add(departmentMes);
        departmentPanel.add(targetDepartment);
        departmentPanel.add(box);
        idLabel1.setBounds(130,20,50,30);
        idMes.setBounds(190,20,60,30);
        nameLabel1.setBounds(280,20,50,30);
        nameMes.setBounds(340,20,50,30);
        originalDepartment.setBounds(130,60,50,30);
        departmentMes.setBounds(190,60,50,30);
        targetDepartment.setBounds(250,60,100,30);
        box.setBounds(350,60,80,30);
       departmentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frames.add(departmentPanel);
        departmentPanel.setBounds(0,160,500,100);
    }

    /**
     * 医生调岗界面中的要调往的科室的科室下拉框中的内容设置
     */
    public void Drop_Down_Options(){
        //科室的下拉框的内容
        try {
            rs=stmt.executeQuery("select department from message group by department");

            if(rs.next()){
                rs.last();
                count=rs.getRow();
                label =new String[count];
                rs.first();
                for(int i=0;i<count;i++){
                    label[i]=rs.getString(1);
                    box.addItem(label[i]);
                    rs.next();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *按钮区组件添加及摆放
     */
    public void button(){
        //按钮区组件添加及摆放
        btnPanel.setLayout(null);
        btnPanel.add(transferBtn);
        btnPanel.add(cancelBtn);
        transferBtn.setBounds(100,25,100,30);
        cancelBtn.setBounds(300,25,100,30);

        frames.add(btnPanel);
        btnPanel.setBounds(0,250,500,100);

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
                rs=stmt.executeQuery(
                        "select * from message where id='"+String.valueOf(idField.getText())
                                +"'and name='"+String.valueOf(nameField.getText())+"'");
                if(rs.next()){
                    idMes.setText(rs.getString(1));
                    nameMes.setText(rs.getString(2));
                    departmentMes.setText(rs.getString(7));
                }else{//没有时，返回提示信息
                    JOptionPane.showMessageDialog(null,"未找到此人员或信息错误");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 对查询区的判断以及修改岗位
     */
    public void transfer(){
        try {
            if(idField.getText().equals("")||nameField.getText().equals("")){//判断医生ID是否为空，为空则返回提示信息

                JOptionPane.showMessageDialog(null,"医生ID或姓名不可为空");

            }else{//不为空时，判断是否有该人员，有时，进行删除并返回删除成功信息

                rs=stmt.executeQuery(
                        "select * from message where id='"+String.valueOf(idField.getText())
                                +"'and name='"+String.valueOf(nameField.getText())+"'");

                if(rs.next()){
                    new SQLConnection().stmt.executeUpdate("update message set department='" + box.getSelectedItem()
                            + "' where id='"+String.valueOf(idField.getText())+"'");

                    JOptionPane.showMessageDialog(null,"岗位调动成功");
                }else{//没有时，返回提示信息
                    JOptionPane.showMessageDialog(null,"未找到此人员或信息错误");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
