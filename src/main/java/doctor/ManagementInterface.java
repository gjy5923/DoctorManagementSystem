package doctor; /**
 * @ClassName doctor.ManagementInterface
 * @Author GJY
 * @Version 1.0
 **/
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 *管理界面（主界面）————主要是提供一个功能的转换平台，并且在该类中把人员基本信息显示在了界面的表格中
 */
public class ManagementInterface {

    //菜单栏
    JMenuBar menuBar=new JMenuBar();
    JMenu aboutMenu=new JMenu("关于");
    JMenu helpMenu=new JMenu("帮助");

    JFrame frame=new JFrame("医生管理系统");
    JPanel tablePanel=new JPanel();//存放表格的容器
    JTable table;
    String[] titles={"医生ID","姓名","年龄","性别","联系方式","科室"};//表格首行
    Object[][] datas;//数据
    DefaultTableModel model;//模板
    DefaultTableCellRenderer cellModel=new DefaultTableCellRenderer();//单元格模板
    //
    JLabel titleFrame=new JLabel("医 生 信 息 列 表");
    //功能区
    JPanel checkPanel=new JPanel();
    JLabel checkLabel=new JLabel("人员查找");
    JButton nameCheck=new JButton("姓名查找");
    JButton departmentCheck=new JButton("科室查找");
    JPanel function=new JPanel();
    JButton addBtn=new JButton("新医生入职");
    JButton alterBtn=new JButton("医生信息修改");
    JButton quitBtn=new JButton("医生离职");
    JButton transferBtn=new JButton("医生调岗");
    JLabel flush=new JLabel(" 刷新 ");
    //图片
    ImageIcon img=new ImageIcon("e://textfile//text//main.jpg");
    JLabel bg=new JLabel(img);
    //鼠标右击菜单
    JPopupMenu jpm;
    //构造方法
    public ManagementInterface() {
        frame.setSize(710,525);
        frame.setLocationRelativeTo(null);//设置窗格居中
        frame.setLayout(null);

        //设置程序图标
        Image image = Toolkit.getDefaultToolkit().getImage("e://textfile//text//programIcon.jpg");
        frame.setIconImage(image);

        menuBar.add(aboutMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
        titleFrame.setFont(new Font("隶书",Font.BOLD,30));
        titleFrame.setHorizontalAlignment(JLabel.CENTER);//设置标签文字居中
        titleFrame.setForeground(Color.blue);

        frame.add(titleFrame);
        titleFrame.setBounds(0,10,440,40);
        datas=new SQLConnection().readSQL("select id,name,age,sex,telphone,department from message");//读取数据库中的所有信息
        tableType(datas);//表格写入
        checkDoctor();//查找区域

        //功能区域
        function.setLayout(null);
        function.add(addBtn);
        function.add(alterBtn);
        function.add(quitBtn);
        function.add(transferBtn);
        function.setOpaque(false);
        frame.add(function);
        function.setBounds(470,150,220,390);
        alterBtn.setBounds(25,60,150,40);
        addBtn.setBounds(25,110,150,40);
        quitBtn.setBounds(25,160,150,40);
        transferBtn.setBounds(25,210,150,40);

        //设置背景图片
        ((JPanel)frame.getContentPane()).setOpaque(false);
         frame.getLayeredPane().add(bg,new Integer(Integer.MIN_VALUE));
        bg.setBounds(0,0,710,525);

        frame.setVisible(true);//设置窗体可见
        frame.setResizable(false);//设置窗格不可操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //给姓名查找按钮添加监听事件
        nameCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp=null;//临时存放输入的信息
                //弹出输入对话框
                temp=JOptionPane.showInputDialog(null,"输入需要查找的姓名","姓名查找",JOptionPane.PLAIN_MESSAGE);
                if(temp==null){
                    //不执行任何操作
                }
               else if(temp.equals(""))//当输入为空时，返回提示信息
                  JOptionPane.showMessageDialog(null,"输入不可为空");
                else{
                  Object[][]dataTemp= new SQLConnection().readSQL("select id,name,age,sex,telphone,department from message where name='"+temp+"'");
                  tableType(dataTemp);//调用表格组件，重绘表格
              }
            }
        });
        //给科室查找按钮添加监听事件
        departmentCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp;//临时存放输入的信息
                //弹出输入对话框
                temp=JOptionPane.showInputDialog(null,"输入需要查找的科室","科室查找",JOptionPane.PLAIN_MESSAGE);
                if(temp==null){
                }
                else if(temp.equals(""))//当输入为空时，返回提示信息
                    JOptionPane.showMessageDialog(null,"输入不可为空");
                else{
                    Object[][]dataTemp= new SQLConnection().readSQL("select id,name,age,sex,telphone,department from message where department='"+temp+"'");
                    tableType(dataTemp);//调用表格组件，重绘表格
                }
            }
        });
        //给刷新按钮添加监听事件
        flush.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[][]dataTemp= new SQLConnection().readSQL("select id,name,age,sex,telphone,department from message");
                tableType(dataTemp);//调用表格组件，重绘表格
            }
        });
        //给医生信息修改按钮添加监听事件
        alterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformationModification();//调用医生信息修改方法
            }
        });
        //给新医生入职按钮添加监听事件
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewDoctors();//添加医生信息
            }
        });

        //给医生离职按钮添加监听事件
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicalSeparation();//调用医生离职方法
            }
        });
        //给医生调岗按钮添加监听事件
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new DoctorsTransfer();//调用医生调岗方法
            }
        });
        //设置菜单栏帮助的菜单栏事件
        helpMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd /c start file:///E:/textfile/text/javadoc/doctor/package-summary.html");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        //设置菜单栏关于的菜单栏事件
      aboutMenu.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
             setAboutMenu();
          }
      });
        }
    /**
     * 菜单栏——关于,说明此系统的基本作用
     */
    public void setAboutMenu(){
        JFrame aboutFrame=new JFrame("关于");
        aboutFrame.setLayout(null);
        JTextArea mes=new JTextArea();
        mes.setLineWrap(true);
        mes.setEditable(false);
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("e://textfile//text//about.txt")));
            String data=null;
            mes.append("\n");
            while((data=br.readLine())!=null){//按行读取txt文档里的数据
                mes.append(data);
                mes.append("\n");

            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置文本框
        mes.setFont(new Font("黑体",Font.PLAIN,15));
        mes.setBounds(0,0,285,250);
        aboutFrame.setSize(300,250);
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setResizable(false);
        aboutFrame.add(mes);
        aboutFrame.setVisible(true);

    }

    /**
     * 方法用于主界面中的表格绘制及重新绘制，并且设置了表格中的鼠标右击操作
     */
   public void tableType(Object[][] data){
            tablePanel.removeAll();
            tablePanel.setVisible(false);
            model=new DefaultTableModel(data,titles);//设置模板
            cellModel.setHorizontalAlignment(JLabel.CENTER);//设置单元格居中
            table=new JTable(model){
                public boolean isCellEditable(int row,int column){
                    return  false;//设置表格不可编辑
                }
            };
            table.getTableHeader().setReorderingAllowed(false);//设置不可拖动列
            table.getTableHeader().setResizingAllowed(false);//设置不可拖动改变列宽
            table.setDefaultRenderer(Object.class,cellModel);
            //按比例设置单元格的列宽
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(80);
            table.getColumnModel().getColumn(2).setPreferredWidth(50);
            table.getColumnModel().getColumn(3).setPreferredWidth(50);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(5).setPreferredWidth(100);

            table.setRowHeight(25);//设置行高
            table.setGridColor(Color.cyan);//设置表格边框颜色
            tablePanel.add(new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));//添加滚动条
            table.setFont(new Font("宋体",Font.PLAIN,15));//设置表格中的字体样式
            tablePanel.setVisible(true);
            frame.add(tablePanel);
            tablePanel.setBounds(0,40,465,500);//设置表格位置
            //设置表格鼠标单击事件
            flush.setFont(new Font("黑体",Font.BOLD,15));
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == MouseEvent.BUTTON3){
                        jpm = new JPopupMenu();
                        jpm.setPopupSize(50,25);
                        jpm.add(flush);
                        jpm.show(table,e.getX(),e.getY());
                    }
                }
            });
        }

    /**
     * checkDoctor()方法主要是主界面中人员查找区的组件的添加及摆放
     */
    public void checkDoctor(){
        checkPanel.setLayout(null);
        checkPanel.add(checkLabel);
        checkPanel.add(nameCheck);
        checkPanel.add(departmentCheck);
        checkPanel.setBackground(Color.lightGray);
        checkPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        checkLabel.setFont(new Font("楷体",Font.BOLD,20));//设置标签字体样式
        nameCheck.setFont(new Font("宋体",Font.BOLD,15));//设置姓名查找按钮字体样式
        departmentCheck.setFont(new Font("宋体",Font.BOLD,15));//设置科室查找按钮字体样式
        checkPanel.setOpaque(false);
        //设置组件在容器中的位置
        checkLabel.setBounds(67,0,100,40);
        nameCheck.setBounds(5,50,95,40);
        departmentCheck.setBounds(120,50,95,40);
        frame.add(checkPanel);
        checkPanel.setBounds(470,40,220,100);

    }
}
