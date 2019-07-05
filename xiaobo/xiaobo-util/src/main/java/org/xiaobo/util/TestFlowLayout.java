package org.xiaobo.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

public class TestFlowLayout {
	 public static Integer width=800;
	 public static Integer height=500;
	 
	 public static Integer row_height=30;
	 //MySQL 1  
	 public static Integer DATA_BASE_TYPE=1;
	 public static String URL="";
	 public static String ACCOUNT="";
	 public static String PASSWORD="";
	 public static String PACKAGE_NAME="";
	 public static String FILE_PATH="";
	 public static String[] TABLE_INCLUDES= {};
	 public static String[] TABLE_EXCLUSIVES= {};
	 
	 public static void main(String[] args) {
		    JFrame jf = new JFrame("代码生成工具");
		    
		    JPanel panelAll=new JPanel(new FlowLayout(FlowLayout.LEFT));
		    
		    JPanel panelDataType=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel dataTypeLabel=new JLabel("数据库类型");
	        JRadioButton mysqlRadioButton = new JRadioButton("MySql");
	        mysqlRadioButton.setSelected(true);
	        mysqlRadioButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					DATA_BASE_TYPE=1;
				}
			});
	        panelDataType.add(dataTypeLabel);
	        panelDataType.add(mysqlRadioButton);
	        panelDataType.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelDataType);
		    
		    //URL
	        JPanel panelURL=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel urlLabel=new JLabel("数据库链接");
	        JTextField urlField=new JTextField(50);
	        urlField.setText("jdbc:mysql://localhost:3306/xiaobo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
	        panelURL.add(urlLabel);
	        panelURL.add(urlField);
	        panelURL.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelURL);
	        
	        //账号
	        JPanel panelName=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel nameLabel=new JLabel("数据库账号");
	        JTextField nameField=new JTextField(10);
	        nameField.setText("root");
	        panelName.add(nameLabel);
	        panelName.add(nameField);
	        panelName.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelName);
	        //密码
	        JPanel panelPass=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel passLabel=new JLabel("数据库密码");
	        JTextField passField=new JTextField(10);
	        passField.setText("pass");
	        panelPass.add(passLabel);
	        panelPass.add(passField);
	        panelPass.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelPass);
	         
	        //生成文件包名
	        JPanel panelPackagePath=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel packagePathLabel=new JLabel("生成文件包名");
	        JTextField packagePathField=new JTextField(10);
	        packagePathField.setText("org.example.test");
	        panelPackagePath.add(packagePathLabel);
	        panelPackagePath.add(packagePathField);
	        panelPackagePath.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelPackagePath);
	        
	        //生成文件路径
	        JPanel panelPath=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel pathLabel=new JLabel("生成文件路径");
	        JTextField pathField=new JTextField(10);
	        pathField.setText("C:/entity-java");
	        panelPath.add(pathLabel);
	        panelPath.add(pathField);
	        panelPath.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelPath);
	        
	        
	        //选择生成表名称
	        JPanel panelIncludeTable=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel includeTableLabel=new JLabel("选择生成表名称:");
	        JTextField includeTableField=new JTextField(10);
	        includeTableField.setText("a,b");
	        panelIncludeTable.add(includeTableLabel);
	        panelIncludeTable.add(includeTableField);
	        panelIncludeTable.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelIncludeTable);
	        
	        
	        //选择生成表名称
	        JPanel panelNotIncludeTable=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JLabel notIncludeTableLabel=new JLabel("选择不生成表名称:");
	        JTextField notIncludeTableField=new JTextField(10);
	        notIncludeTableField.setText("c,d");
	        panelNotIncludeTable.add(notIncludeTableLabel);
	        panelNotIncludeTable.add(notIncludeTableField);
	        panelNotIncludeTable.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelNotIncludeTable);
	        
	        //按钮
	        JPanel panelBut=new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JButton startBut = new JButton("生成");
	        startBut.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 URL = urlField.getText().trim();
					 ACCOUNT = nameField.getText().trim();
					 PASSWORD = passField.getText().trim();
					 FILE_PATH = pathField.getText().trim();
					 PACKAGE_NAME = packagePathField.getText().trim();
					 String includes = includeTableField.getText().trim();
					 if(StringUtils.isNotEmpty(includes)) {
						 TABLE_INCLUDES = includes.split(",");
					 }
					 String notIncludes = notIncludeTableField.getText().trim();
					 if(StringUtils.isNotEmpty(notIncludes)) {
						 TABLE_EXCLUSIVES = notIncludes.split(",");
					 }
					 JDBCUtil3.RUN_FLAG=true;
					 if(DATA_BASE_TYPE==1) {
						 FramUtil.mysqlBuild();
					 }
						
				}
			});
	        JButton stopBut = new JButton("停止");
	        stopBut.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JDBCUtil3.RUN_FLAG=false;					
				}
			});
	        panelBut.add(startBut);
	        panelBut.add(stopBut);
	        panelBut.setPreferredSize(new Dimension(width, row_height));
	        panelAll.add(panelBut);
	        
	        jf.add(panelAll); //把按钮放入边框布局的中部
	        
	        jf.setSize(width, height);               //设置按钮的大小
	        jf.setLocation(500, 100);
	        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
	        jf.setVisible(true);//显示窗口
	    }
}
