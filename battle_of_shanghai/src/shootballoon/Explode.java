package shootballoon;
import javax.swing.*;

import weapon.Grenade;

import java.awt.*;
public class Explode {
	private Image[] explodes =new Image[16];					//∆’Õ®µ–»À±¨’®

	private Image[] bombs =new Image[16];						// ÷¡ÒµØ¥Û±¨’®

	public static boolean bomb =false;							// ÷¡ÒµØ «∑Ò±¨’®

	protected static int number=0;								//¥Û±¨’®Õº∆¨¬÷≤•º∆ ˝

	public Explode() 
	{
		for(int i=0;i<16;i++)							//∆’Õ®–°±¨’®
		{
			explodes[i] =(new ImageIcon(Constant.IMAGEPATH+"explode\\e"+i+".gif")).getImage();

		}
		for(int i=0;i<16;i++)							// ÷¡ÒµØ¥Û±¨’®
		{
			bombs[i] =(new ImageIcon(Constant.IMAGEPATH+"bomb\\e"+i+".gif")).getImage();

		}
	}


	public void drawexplode(Graphics g,int x,int y) 	//ª≠–°±¨’®
	{								

		for(int i=0;i <= 15;i++)
		{
			g.drawImage(explodes[i],  x,  y, null);
		}

	}

	public void drawbomb(Graphics g)					//ª≠¥Û±¨’®
	{
		if(number<16) 
		{
			g.drawImage(bombs[number],(Grenade.x_grenade-150) ,  10, null);
		}
		number++;
	}
	public Rectangle getRect()															//≈ˆ◊≤
	{
		return new Rectangle((Grenade.x_grenade-150),10, 360, 500);
	}
}




