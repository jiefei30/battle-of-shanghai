package shootballoon;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class StartFrame extends JFrame{

	Image startscreen=(new ImageIcon(Constant.IMAGEPATH+"startscreen.jpg")).getImage();    //背景
	Image icon=(new ImageIcon(Constant.IMAGEPATH+"icon.png")).getImage();					//许文强
	ImageIcon marchsoft=(new ImageIcon(Constant.IMAGEPATH+"marchsoft.png"));						//三月

//	StartFrame startframe_obj;																//定义窗口对象
	JButton btn_start =new JButton("开始游戏");												//开始游戏  按钮
	JButton btn_rule =new JButton("规则");
	JButton btn_about =new JButton("关于",marchsoft);										
	JButton btn_exit =new JButton ("退出游戏");
	
	JPanel start_panel_obj =new Start_panel(this);											//新建面板对象，自建新面板类，参数为框架对象

	JTextArea about = new JTextArea("三月-九组-王明灿",1,1);									//关于
	JTextArea rules = new JTextArea("左右键移动，空格发射子弹，A键扔手雷"
			+"\n"+"击中忍者加10声望，击中日本军官加50声望，击中人质减30声望，"											//规则说明
			+"\n"	+ "每局20发子弹，1颗手雷，在35秒内获取最大声望！"+"\n"
			+"，没有子弹后按空格结束游戏，加油！",5,15);

	/*
	 * 
	 * 【构造方法】窗口初始化
	 * 
	 */
	public	StartFrame() {                                                        		  //框架初始构造

		this.setTitle("血战上海滩");															//标题
		this.setIconImage(icon);															//图标
		this.setVisible(true);																//窗口可见
		this.setSize(Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT);							//大小
		this.setLocation(200, 10);															//坐标
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								//关闭窗口
		this.setLayout(null);																//取消布局	
		this.setResizable(false);
		this.add(start_panel_obj);															//加上面板		


	}
	/*
	 * 
	 * 主方法
	 * 
	 */

	public static void main(String[] args)						
	{
		new StartFrame();     																//创建框架对象
	}



	/*
	 * 【内部类】自定义自己的面板类
	 * 
	 */
	public class Start_panel extends JPanel implements ActionListener
	{
		JFrame jf;																			//为了得到当前的框架对象
		//构造
		public Start_panel(JFrame jf)
		{

			this.jf=jf;																			//用来调用当前框架
			this.setBounds(0, 0, 1020, 768);
			this.setVisible(true);
			this.setLayout(null);

			btn_start.setBounds(620, 430, 150, 50);
			btn_rule.setBounds(620, 510, 150, 50);
			btn_about.setBounds(620, 590, 150, 50);
			btn_exit.setBounds(620, 680, 150, 50);

			this.add(btn_rule);
			this.add(btn_start); 
			this.add(btn_about);
			this.add(btn_exit);

			btn_start.addActionListener(this);
			btn_about.addActionListener(this);
			btn_rule.addActionListener(this);
			btn_exit.addActionListener(this);
		}
		/*
		 * 
		 * 		实现ActionListener接口的方法
		 */
		public void actionPerformed(ActionEvent e)					
		{		
			if (e.getSource()==btn_start) 														//如果点击开始游戏
			{			
				new MyGameFrame();														//转入下一个窗口
				jf.dispose();                                                   				//关闭当前窗口
			}
			if(e.getSource()==btn_rule) 

			{	
				JOptionPane.showConfirmDialog(jf, rules,"规则",JOptionPane.DEFAULT_OPTION);    	//对话框
			}

			if	(e.getSource()==btn_about) 
			{
				JOptionPane.showConfirmDialog(jf, about,"关于",JOptionPane.DEFAULT_OPTION);		//对话框
			}
			if	(e.getSource()==btn_exit) 
			{
				int n=JOptionPane.showConfirmDialog(jf,"你确认要退出游戏吗","退出游戏",JOptionPane.YES_NO_OPTION);	//对话框
				if(n==JOptionPane.YES_OPTION) jf.dispose();
				if(n==JOptionPane.NO_OPTION) JOptionPane.showConfirmDialog(jf, "继续玩吧^_^","恭喜恭喜",JOptionPane.DEFAULT_OPTION);	
			}
		}


		/*
		 * 
		 * 默认paint方法
		 */	
		public void paintComponent(Graphics g)
		{
			g.drawImage(startscreen,0,0, 1050, 768,null);

		}
	}
}



