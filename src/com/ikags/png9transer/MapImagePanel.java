package com.ikags.png9transer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class MapImagePanel extends JPanel {
	/**
	 * 
	 */

	
	public int count=0;
	private static final long serialVersionUID = 1L;
	public Thread thread;

	public MapImagePanel() {
		this.setAutoscrolls(true);
		MyListener myListener = new MyListener();
		addMouseListener(myListener);
		addMouseMotionListener(myListener);
		thread=new Thread(){
			public void run(){
			 try{
				 while(PNG9Def.isRunning){
					 update_Srceen(); 
					 Thread.sleep(34);
					 count++;
				 }
			 }catch(Exception ex){
				 ex.printStackTrace();
			 }
			}
		};
		thread.start();
	}



	class MyListener extends MouseInputAdapter {

		public void mouseMoved(MouseEvent e) {
			update_Srceen();
		}

		public void mousePressed(MouseEvent e) {
			// 拖拽功能，方便整体移动。
			 if (SwingUtilities.isRightMouseButton(e)) {
				change_xxx = e.getX() - xxx;
				change_yyy = e.getY() - yyy;
				update_Srceen();
			}
		}

		// 鼠标拖拽
		public void mouseDragged(MouseEvent e) {
			
			// 右键拖拽全局
			if (SwingUtilities.isRightMouseButton(e)) {
				xxx = e.getX() - change_xxx;
				yyy = e.getY() - change_yyy;
				update_Srceen();
			}
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

	int xxx = 32;
	int yyy = 32;
	int change_xxx = 0;
	int change_yyy = 0;

	int colsize=0;
	
	public void updata() {



	}

	
	Color bgcolor=new Color(PNG9Def.BG_R, PNG9Def.BG_G, PNG9Def.BG_B);
	Color linecolor=new Color(PNG9Def.SG_R, PNG9Def.SG_G, PNG9Def.SG_B);
	Color colcolor1=new Color(PNG9Def.CG_R1, PNG9Def.CG_G1, PNG9Def.CG_B1);
	Color colcolor2=new Color(PNG9Def.CG_R2, PNG9Def.CG_G2, PNG9Def.CG_B2);
	Color blackcolor=new Color(0, 0, 0);
	public void paint(Graphics g) {
		g.setColor(bgcolor);
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(linecolor);
		int maxlength = Math.max(g.getClipBounds().width, g.getClipBounds().height);
		for (int i = 0; i < (maxlength / 32) + 1; i++) {
			g2.drawLine(0, 32 * i, maxlength, 32 * i);
			g2.drawLine(32 * i, 0, 32 * i, maxlength);
		}

		updata();
		// 图像开始

//		if (PNG9Def.mMapImage != null) {
//			g2.drawImage(PNG9Def.mMapImage, xxx, yyy, null);
//		}
		if(PNG9Def.new_bfimg!=null){
			BufferedImage[] bfimg =PNG9Def.new_bfimg;
			draw9png(g2 ,bfimg,xxx,yyy);
		}else if (PNG9Def.org_bfimg != null) {
			BufferedImage[] bfimg =PNG9Def.org_bfimg;
			draw9png(g2 ,bfimg,xxx,yyy);
		}

		

		g2.setColor(blackcolor);
		g2.drawString(xxx+","+yyy,15,15);
		g2.drawString("img="+PNG9Def.ImageFilepath,15,30);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	
	public static void draw9png(Graphics2D g2 ,BufferedImage[] bfimg,int xxx,int yyy){
		try{
		g2.drawImage(bfimg[0], xxx, yyy, null);
		g2.drawImage(bfimg[1], xxx+bfimg[0].getWidth(), yyy, null);
		g2.drawImage(bfimg[2], xxx+bfimg[0].getWidth()+bfimg[1].getWidth(), yyy, null);
		
		g2.drawImage(bfimg[3], xxx, yyy+bfimg[0].getHeight(), null);
		g2.drawImage(bfimg[4], xxx+bfimg[3].getWidth(), yyy+bfimg[0].getHeight(), null);
		g2.drawImage(bfimg[5], xxx+bfimg[3].getWidth()+bfimg[4].getWidth(), yyy+bfimg[0].getHeight(), null);
		
		g2.drawImage(bfimg[6], xxx, yyy+bfimg[0].getHeight()+bfimg[3].getHeight(), null);
		g2.drawImage(bfimg[7], xxx+bfimg[6].getWidth(), yyy+bfimg[0].getHeight()+bfimg[3].getHeight(), null);
		g2.drawImage(bfimg[8], xxx+bfimg[6].getWidth()+bfimg[7].getWidth(), yyy+bfimg[0].getHeight()+bfimg[3].getHeight(), null);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

		
		
	

	public void update_Srceen() {
		repaint();
	}

}
