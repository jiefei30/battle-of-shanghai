package people;

import java.awt.Graphics;


import javax.swing.ImageIcon;
import shootballoon.Constant;
public class Ninja extends BodyObject{
	
	public boolean get_shoot=false;				//是否被击中
	
	public boolean draw=false;						//是否已画
	
	//初始化
	public Ninja()
	{
		img=(new ImageIcon(Constant.IMAGEPATH+"\\ninja.png")).getImage();	
		x=(int)(950*Math.random());
		y=130;
		this.speed=0;
		this.width=img.getWidth(null);
		this.height=img.getHeight(null);
	}
	
	//画自己
	public void draw_ObjectSelf(Graphics g) 
	{
		if(!get_shoot )
		{
		g.drawImage(img, x, y, null);
		}
	}
}
