package org.xiaobo.util;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class FramTest extends JFrame {
	
	public FramTest() {
		this.setTitle("test");
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		
		//单选按钮
		JRadioButton b1 = new JRadioButton("男");
		JRadioButton b2 = new JRadioButton("女");
		JRadioButton b3 = new JRadioButton("外星人");
		JTextArea jt = new JTextArea();
		ButtonGroup g = new ButtonGroup();  //单选按钮组
		
		
		//复选框
		JCheckBox c1 = new JCheckBox("读书");
		JCheckBox c2 = new JCheckBox("音乐");
		JCheckBox c3 = new JCheckBox("电影");
		
		
		
		g.add(b1);g.add(b2);g.add(b3);
		Container bcon = new Container();
		bcon.setLayout(new FlowLayout());
		con.add(BorderLayout.WEST, bcon);
		bcon.add(b1);bcon.add(b2);bcon.add(b3);
		
		
		con.add(jt);
		Container ccon = new Container();
		ccon.setLayout(new FlowLayout());
		con.add(BorderLayout.EAST, ccon);
		ccon.add(c1);ccon.add(c2);ccon.add(c3);
		
		
		
		b1.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jt.append("您选中了“男”单选按钮\n");
			}
		});
		b2.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jt.append("您选中了“女”单选按钮\n");
			}
			
		});
		b3.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jt.append("您选中了“外星人”单选按钮\n");
			}
			
		});
		
		c1.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jt.append("您选中了“读书”复选框\n");
			}
			
		});
		c2.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jt.append("您选中了“音乐”复选框\n");
			}
			
		});
		c3.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jt.append("您选中了“电影”复选框\n");
			}
			
		});
		
		this.setVisible(true);
		this.setBounds(50, 50, 600, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);            //设置关闭方式，可以选择多种关闭玄子选项
	}
    public static void main(String[] args) {
    	new FramTest();
    }
}
