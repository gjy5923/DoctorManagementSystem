package doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *注册界面————实现注册操作
 **/
public class Register_DoctorManagement extends SQLConnection implements KeyListener{
    JFrame frame=new JFrame("账号注册");
    JPanel panel=new JPanel();
    JLabel accountNumber=new JLabel("账号：");
    JLabel password=new JLabel("密码：");
    JLabel againPassword=new JLabel("再次输入密码：");
    JLabel login=new JLabel("已有帐号，前往登录");
    JTextField accountNumberField=new JTextField(20);
    JPasswordField passwordField=new JPasswordField(40);
    JPasswordField againPasswordField=new JPasswordField(40);
    JButton registerBtn=new JButton("注册");
    //背景图片
    ImageIcon img=new ImageIcon("e://textfile//text//register.jpg");
    JLabel bg=new JLabel(img);

    /**
     * 构造方法
     */
    public Register_DoctorManagement() {
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
        register();//注册方法
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 注册方法————含有组件的摆放及添加以及相应的事件
     */
    public void register(){
        panel.setLayout(null);
        panel.add(accountNumber);
        panel.add(accountNumberField);
        panel.add(password);
        panel.add(againPassword);
        panel.add(passwordField);
        panel.add(againPasswordField);
        panel.add(registerBtn);
        panel.add(login);
        frame.add(panel);
        panel.setOpaque(false);
        panel.setBounds(0,100,350,260);
        accountNumber.setBounds(50,0,40,30);
        accountNumberField.setBounds(95,0,200,30);
        password.setBounds(50,45,40,30);
        passwordField.setBounds(95,45,200,30);
        againPassword.setBounds(5,90,85,30);
        againPasswordField.setBounds(95,90,200,30);
        registerBtn.setBounds(80,140,180,30);
        login.setBounds(220,193,120,30);
        login.setFont(new Font("黑体",Font.PLAIN,11));
        //添加回车执行注册按钮
        accountNumberField.addKeyListener(this);
        passwordField.addKeyListener(this);
        againPasswordField.addKeyListener(this);
        //给第三个文本框增加监听事件
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountTemp=new String();
                String passwordTemp=new String();
                String aPasswordTemp=new String();
                accountTemp=accountNumberField.getText();
                passwordTemp= String.valueOf(passwordField.getPassword());
                aPasswordTemp= String.valueOf(againPasswordField.getPassword());
                if(accountTemp.equals(""))//账号为空时
                    JOptionPane.showMessageDialog(null,"用户名不能为空");
                else if(passwordTemp.equals("")){//密码为空时
                    JOptionPane.showMessageDialog(null,"密码不能为空");
                }else if(passwordTemp.equals(aPasswordTemp)==false){
                    JOptionPane.showMessageDialog(null,"两次密码输入不一致");

                }else{
                    String sql="select * from account where account='"+accountTemp+"'";//sql语句
                    try {
                        rs=stmt.executeQuery(sql);
                        String m= String.valueOf(rs.next());
                        if(m.equals("true")) {//判断数据库中是否存在键入用户名
                            //显示提示信息
                            JOptionPane.showMessageDialog(null, "账号已存在");
                            //因为账号存在所有所有文本框置空
                            accountNumberField.setText("");
                            passwordField.setText("");
                            againPasswordField.setText("");
                        }
                        else {//用户名数据库中不存在且两次密码都相同，存入到数据库中
                            String sql1="insert into account(account,password) values("+accountTemp+","+passwordTemp+")";
                            stmt.executeUpdate(sql1);
                            JOptionPane.showMessageDialog(null,"用户创建成功");
                        }
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        //给登录标签添加鼠标事件
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标单击标签时
                frame.setVisible(true);//把当前窗口关闭
                new Login_DoctorManagement();//调用登录界面
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下时，设置标签变化
                login.setForeground(Color.RED);
                login.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                login.setForeground(Color.BLACK);
                frame.dispose();
                new Login_DoctorManagement();
            }
        });
    }
    //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        //如果按下回车则执行注册按钮
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            registerBtn.doClick();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
