package doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * 登录————实现登录操作
 */
public class Login_DoctorManagement extends SQLConnection implements KeyListener{
    //成员变量
    JFrame frame=new JFrame("登录");
    JPanel panel=new JPanel();
    JLabel accountNumber=new JLabel("账号：");
    JLabel password=new JLabel("密码：");
    JTextField accountNumberField=new JTextField(20);
    JPasswordField passwordField=new JPasswordField(40);
    JButton loginBtn=new JButton("登陆");
    JLabel register=new JLabel("注册账号");
    ImageIcon img=new ImageIcon("e://textfile//text//login.jpg");
    JLabel bg=new JLabel(img);
    public Login_DoctorManagement() {
        frame.setLayout(null);
        frame.setSize(350,360);
        frame.setLocationRelativeTo(null);
        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        frame.setIconImage(image);
        //设置背景
        ((JPanel)frame.getContentPane()).setOpaque(false);
        frame.getLayeredPane().add(bg,new Integer(Integer.MIN_VALUE));
        bg.setBounds(0,0,350,360);
        login(); //登录
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 登录方法————含有组件的摆放及添加
     */
   public void login(){
        panel.setLayout(null);
        frame.add(panel);
        panel.add(accountNumber);
        panel.add(accountNumberField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(loginBtn);
        panel.add(register);
        panel.setOpaque(false);
        panel.setBounds(0,150,350,210);
        accountNumber.setBounds(40,0,40,30);
        accountNumberField.setBounds(80,0,200,30);
        password.setBounds(40,45,40,30);
        passwordField.setBounds(80,45,200,30);
        loginBtn.setBounds(90,100,160,30);
        register.setBounds(280,140,50,30);
        register.setFont(new Font("黑体",Font.BOLD,11));

        //添加键盘事件
        accountNumberField.addKeyListener(this);
        passwordField.addKeyListener(this);
        //给登录按钮增加监听事件
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountTemp=new String();//用来临时存储输入的用户名
                String passwordTemp=new String();//用来临时存储输入的密码
                accountTemp=accountNumberField.getText();
                passwordTemp= String.valueOf(passwordField.getPassword());
                if(accountTemp.equals("")){//账号为空时
                    JOptionPane.showMessageDialog(null,"账号不能为空");
                }else if(passwordTemp.equals("")){//密码为空时
                    JOptionPane.showMessageDialog(null,"密码错误");
                }else{

                    try {
                        rs=stmt.executeQuery("select * from account where account='"+accountTemp+"'");
                        if(!rs.next()){//判断账号是否存在
                            JOptionPane.showMessageDialog(null,"账号不存在");
                        }else{
                            String sql="select * from account where account='"+accountTemp+
                                    "'and password='"+passwordTemp+"'";//sql语句
                            rs=stmt.executeQuery(sql);
                            if(!rs.next()) {//判断数据库中是否存在键入用户名
                                //显示提示信息
                                JOptionPane.showMessageDialog(null, "密码错误");
                                //因为账号不存在，密码文本框置空
                                passwordField.setText("");
                            }
                            else{//用户名密码匹配成功
                                new ManagementInterface();
                                frame.dispose();
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        });
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                new Register_DoctorManagement();
            }

            @Override
            public void mousePressed(MouseEvent e) {
               register.setForeground(Color.RED);
               register.setBorder(BorderFactory.createLineBorder(Color.lightGray));

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                register.setForeground(Color.BLACK);
            }
        });
    }
    //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        //如果按钮回车按钮则执行登录按钮
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            loginBtn.doClick();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new Login_DoctorManagement();
    }
}
