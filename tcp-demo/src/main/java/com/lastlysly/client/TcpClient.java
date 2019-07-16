package com.lastlysly.client;

import cn.hutool.core.util.ObjectUtil;
import com.lastlysly.entity.KeyValueItem;
import com.lastlysly.entity.MyResult;
import com.lastlysly.exception.MyCustomException;
import com.lastlysly.utils.ByteUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-01-25 11:06
 **/
public class TcpClient extends JFrame implements ActionListener {

    /**
     * 文本域
     */
    private JTextArea jTextArea;

    private JScrollPane jScrollPane;
    /**
     * 北部面板
     */
    private JPanel northPanel;
    /**
     * 中部面板
     */
    private JPanel centerPanel;
    /**
     * 南部面板
     */
    private JPanel southPanel;
    /**
     * ip地址输入框
     */
    private JTextField ipInput;

    /**
     * 端口号输入框
     */
    private JTextField portInput;
    /**
     * 信息输入框
     */
    private JTextField msgInput;


    private JComboBox comboBox;


    /**
     * 连接按钮
     */
    private JButton connectBtn;
    /**
     * 断开连接按钮
     */
    private JButton disConnectBtn;

    /**
     * 发送按钮
     */
    private JButton sendBtn;


    private Socket socket = null;
    private BufferedOutputStream outputStream = null;
    private BufferedInputStream inputStream = null;


    private String sendAfterHexStringToBytes = "十六进制转为Byte后传输";
    private String sendAfterStringToUS_ASCIIBytes = "转US-ASCII类型Byte后传输";




    /**
     *
     * @param title
     * @throws HeadlessException
     */
    public TcpClient(String title) throws HeadlessException {
        super(title);
        this.setLocation(300, 200);
        this.setSize(800, 600);
        //设置窗口大小可自由控制
        this.setResizable(true);

        //顶部组件
        northPanel = new JPanel();
        ipInput = new JTextField(10);
        portInput = new JTextField(5);
        connectBtn = new JButton("连接");
        connectBtn.setEnabled(true);
        disConnectBtn = new JButton("断开");
        disConnectBtn.setEnabled(false);
        JLabel ipLabel = new JLabel("ip：");
        JLabel portLabel = new JLabel("端口：");
        northPanel.add(ipLabel);
        northPanel.add(ipInput);
        northPanel.add(portLabel);
        northPanel.add(portInput);
        northPanel.add(connectBtn);
        northPanel.add(disConnectBtn);
        this.add(northPanel,BorderLayout.NORTH);

        //中部组件
        jTextArea = new JTextArea();
        jTextArea.setBackground(Color.CYAN);
        jTextArea.setMargin(new Insets(10, 10, 10, 10));
        //自动换行
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        jScrollPane = new JScrollPane(jTextArea);
        //垂直滚动条自动出现
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(jScrollPane,BorderLayout.CENTER);

        //南部组件
        southPanel = new JPanel();
        comboBox=new JComboBox();
        comboBox.addItem(sendAfterHexStringToBytes);
        comboBox.addItem(sendAfterStringToUS_ASCIIBytes);

        msgInput = new JTextField(20);
        sendBtn = new JButton("发送");
        southPanel.add(comboBox);
        southPanel.add(msgInput);
        southPanel.add(sendBtn);
        this.add(southPanel,BorderLayout.SOUTH);

        this.setVisible(true);

        /**
         *
         * 0：EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。
         * 1：HIDE_ON_CLOSE，只隐藏界面，setVisible(false)。
         * 2：DISPOSE_ON_CLOSE,隐藏并释放窗体，dispose()，当最后一个窗口被释放后，则程序也随之运行结束。
         * 3：EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。
         */
//        this.setDefaultCloseOperation(2);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int exi = JOptionPane.showConfirmDialog (null,
                        "要退出该程序吗？", "提示",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (exi == JOptionPane.YES_OPTION)
                {
                    System.exit (0);
                }
                else
                {
                    return;
                }


            }
        });

        sendBtn.addActionListener(this);
        connectBtn.addActionListener(this);
        disConnectBtn.addActionListener(this);

    }

    /**
     * 连接到服务器
     * @param ip
     * @param port
     */
    public void connectToServer(String ip,int port){
        try {

            // 1、创建客户端的 Socket 服务
            socket = new Socket(ip,port);
            // 2、获取 Socket 流中输入流
            outputStream = new BufferedOutputStream(socket.getOutputStream());
            inputStream = new BufferedInputStream(socket.getInputStream());
            JOptionPane.showMessageDialog(this,"连接成功");
            connectBtn.setEnabled(false);
            disConnectBtn.setEnabled(true);
            ipInput.setEnabled(false);
            portInput.setEnabled(false);
            new Thread(new ReceiveThread()).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"连接失败");
        }
    }

    /**
     * 断开连接，关闭socket服务
     * @param socket
     */
    public void disConnectFromServer(Socket socket){
        if (socket != null){
            try {
                socket.close();
                JOptionPane.showMessageDialog(this,"已断开连接");
                disConnectBtn.setEnabled(false);
                connectBtn.setEnabled(true);
                ipInput.setEnabled(true);
                portInput.setEnabled(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,"断开连接出现错误");
            }
        }
    }

    /**
     * 消息发送
     * @param msgStr
     */
    public MyResult sendMsg(String msgStr){

        byte[] sendStr = null;
        try {
            if (sendAfterStringToUS_ASCIIBytes.equals(comboBox.getSelectedItem())){
                sendStr = msgStr.getBytes("US-ASCII");
                outputStream.write(sendStr);
            }

            if (sendAfterHexStringToBytes.equals(comboBox.getSelectedItem())){
                sendStr = ByteUtils.hexStringToBytes(msgStr);
                outputStream.write(sendStr);
            }

            outputStream.flush();
            return new MyResult(1,"发送成功",sendStr);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"写入时出现IO异常");
            return new MyResult(0,"发送失败",sendStr);
        }
    }



    public static void main(String[] args) {
        new TcpClient("TCP客户端");
    }


    /**
     * 事件监听器
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (e.getSource() == connectBtn){
            String ipStr = ipInput.getText().trim();
            int portStr = Integer.parseInt(portInput.getText().trim());
            connectToServer(ipStr, portStr);
        }
        if (e.getSource() == disConnectBtn){
            disConnectFromServer(socket);
        }
        if (e.getSource() == sendBtn){

            if (socket == null || !socket.isConnected()){
                JOptionPane.showMessageDialog(this,"请先完成连接");
                return;
            }
            if (socket.isClosed()){
                JOptionPane.showMessageDialog(this,"连接已断开，请重新连接");
                return;
            }
            String msgStr = msgInput.getText();

            if (msgStr != null && msgStr != ""){
                MyResult res = sendMsg(msgStr);
                if (res.getCode() == 1){
                    jTextArea.append(simpleDateFormat.format(new Date()) + "  发送成功，发送信息为：" + msgStr + "，传输信息为：" + res.getData() + "\n");
                }else{
                    jTextArea.append(simpleDateFormat.format(new Date()) + "  发送失败，发送信息为：" + msgStr + "，传输信息为：" + res.getData() + "\n");
                }

                msgInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this,"请输入值");
            }


        }
    }



    //接收服务器发来的消息的线程类
    private class ReceiveThread implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    String str = String.valueOf(inputStream.read());
                    if ("disconnect".equals(str)) {
                        break;
                    }
                    //将接收到的新消息添加到文本域中
                    System.out.println(str);
                    jTextArea.append("tcp client接收到服务器返回的消息:\n");
                    jTextArea.append(str + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null){
                        inputStream.close();
                    }
                    if (outputStream != null){
                        outputStream.close();
                    }
                    if (socket != null){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                System.exit(0);
            }
        }

    }


}
