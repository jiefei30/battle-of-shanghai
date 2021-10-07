package people;

import java.awt.*;
/*
 * 游戏所有实例物体的父类
 */
public class BodyObject {
	
	//父成员，可供子类调用
	 protected Image img;
	 public int x;
	 public int y;
	 protected int speed;
	 protected int width,height;
	
	 //画自己，被子类继承
	 public void draw_ObjectSelf(Graphics g)
	 {
		 g.drawImage(img,x,y, null);
	 }

	//无参构造器
	
	public BodyObject() {}
	
	//返回物体所在的矩形，用于碰撞检测
	
	public Rectangle getRect() {return new Rectangle(x, y, width, height);}


}
