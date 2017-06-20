package com.ikags.png9transer;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.ikags.util.CommonUtil;


public class PNG9Viewer extends JFrame implements ActionListener {

	String mVerion="-Version 1.0.0 in 2014-6-25";
	String titlename = "Ika 9PNG Resizer    "+mVerion;
	// 关于
	private String aboutStr = " IKA 9PNG Resizer\n  " + "Creator by airzhangfish \n " + mVerion+"\n " + " E-mail:52643971@qq.com\n http://www.ikags.com/";

	private static final long serialVersionUID = 1L;
	private JTabbedPane jtp;
	
	private JMenuBar jMenuBar1 = new JMenuBar();
	private JMenu jMenuFile = new JMenu("File");
	private JMenuItem jMenuFileLoadImage = new JMenuItem("Load 9PNG file...");
	private JMenuItem jMenuFileSaveImage = new JMenuItem("export PNG file...");
	private JMenuItem jMenuFileExit = new JMenuItem("Exit");

	
	PNGLogicManager mPNGLogicManager=new PNGLogicManager();
	private JMenu jMenuHelp = new JMenu("Help");
	private JMenuItem jMenuHelpAbout = new JMenuItem("About");
	private JMenuItem jMenuHelpHomepage = new JMenuItem("Homepage");
	MapImagePanel mapPanel = new MapImagePanel();

	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();


		if (source == jMenuFileLoadImage) {
			FileDialog xs = new FileDialog(this, "load png file", FileDialog.LOAD);
			xs.setFile("*.9.png*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
			PNG9Def.ImageFilepath=lastDir + f;
			loadMapImage(PNG9Def.ImageFilepath);
			}
		}
		
		
		// 保存图片文件
		if (source == jMenuFileSaveImage) {
			FileDialog xs = new FileDialog(this, "save PNG file", FileDialog.SAVE);
			xs.setFile("*.*");
			xs.setVisible(true);
			String f = xs.getFile();
			String lastDir = xs.getDirectory();
			if (f != null) {
				savePNG(lastDir + f);
			}
		}

		// 关于
		if (source == jMenuHelpAbout) {
			JOptionPane.showMessageDialog(this, aboutStr, "About", JOptionPane.INFORMATION_MESSAGE);
		}
		// 软件退出
		if (source == jMenuFileExit) {
			System.exit(0);
		}
		// 打开作者主页
		if (source == jMenuHelpHomepage) {
			CommonUtil.browserURL("http://www.ikags.com");
		}
		
		if (source == button_newset) {
			updateNewImage();
		}
		
	}


	public void updateUI(){
		if(PNG9Def.mMapImage!=null){
			newWdithText.setText(""+(PNG9Def.mMapImage.getWidth()-2));
			newHeightText.setText(""+(PNG9Def.mMapImage.getHeight()-2));	
			
			PNG9Def.defaultMatirx=mPNGLogicManager.create4matirx(PNG9Def.mMapImage);
			System.out.println("=");
			System.out.print("defaultMatirx=");
			for(int i=0;i<PNG9Def.defaultMatirx.length;i++){
				System.out.print(PNG9Def.defaultMatirx[i]+",");
			}
			System.out.println("=");
			PNG9Def.org_bfimg=mPNGLogicManager.create9pics(PNG9Def.mMapImage,PNG9Def.defaultMatirx);
			PNG9Def.newMatirx=null;
			PNG9Def.new_bfimg=null;
		}
	}
	
	public void updateNewImage(){
		try{
		int newwidth=Integer.parseInt(""+newWdithText.getText());
		int newheight=Integer.parseInt(""+newHeightText.getText());
		if(newwidth<PNG9Def.mMapImage.getWidth()-2||newheight<PNG9Def.mMapImage.getHeight()-2){
			JOptionPane.showMessageDialog(this,"Can not resize 9PNG smaller than origin", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//根据宽高生成新坐标
		PNG9Def.newMatirx=mPNGLogicManager.createNewMatirx(newwidth, newheight, PNG9Def.org_bfimg);
		System.out.print("newMatirx=");
		for(int i=0;i<PNG9Def.newMatirx.length;i++){
			System.out.print(PNG9Def.newMatirx[i]+",");
		}
		System.out.println("=");
		PNG9Def.new_bfimg=mPNGLogicManager.createNewpics(PNG9Def.org_bfimg,PNG9Def.newMatirx,newwidth,newheight);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "progressing image error :\r\n"+ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
		}

			
	}
	
	
	
	
	/**
	 * 读取图片
	 * @param path
	 */
	public void loadMapImage(String path){
		try{
		//读取整图
		PNG9Def.mMapImage = ImageIO.read(new File(path));
		updateUI();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Load image error, info :\r\n"+ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
		}
	}



	//保存单帧图片
	public void savePNG(String path) {
		try {
			if(PNG9Def.new_bfimg==null){
				JOptionPane.showMessageDialog(this, "Can not gen new image , please click 'resize' button.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int newwidth=Integer.parseInt(""+newWdithText.getText());
			int newheight=Integer.parseInt(""+newHeightText.getText());
			BufferedImage bfimg = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_ARGB);
		
				Graphics gg = bfimg.getGraphics();
				Graphics2D g2 = (Graphics2D) gg;
				BufferedImage[] bfimgxx =PNG9Def.new_bfimg;
				MapImagePanel.draw9png(g2, bfimgxx, 0, 0);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				File pngfile=new File(path+".png");
			    ImageIO.write(bfimg, "png", pngfile);
				
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "save PNG error"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	JPanel buttonPanel = new JPanel(new GridLayout(1, 6));	
	JPanel mAllPanel = new JPanel();	
	JFormattedTextField newWdithText = new JFormattedTextField();
	JFormattedTextField newHeightText = new JFormattedTextField();
	JButton button_newset = new JButton("Resize");
	public void initButtonLayout(){
		mAllPanel.setLayout(new BorderLayout());
		buttonPanel.add(newWdithText);
		buttonPanel.add(new JLabel("X",JLabel.CENTER));
		buttonPanel.add(newHeightText);
		buttonPanel.add(button_newset);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		mAllPanel.add(buttonPanel, BorderLayout.NORTH);
		mAllPanel.add(mapPanel, BorderLayout.CENTER);
		button_newset.addActionListener(this);
	}
	
	

	
	public PNG9Viewer() {

		this.setSize(640, 480); // 窗体的大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true); // 窗体
		this.setTitle(titlename); // 设置标题

		enableInputMethods(true);

		jMenuFile.add(jMenuFileLoadImage);
		jMenuFile.add(jMenuFileSaveImage);
		jMenuFile.add(jMenuFileExit);
		jMenuFileLoadImage.addActionListener(this);
		jMenuFileSaveImage.addActionListener(this);
		jMenuFileExit.addActionListener(this);

		
		jMenuHelp.add(jMenuHelpAbout);
		jMenuHelpAbout.addActionListener(this);
		jMenuHelp.add(jMenuHelpHomepage);
		jMenuHelpHomepage.addActionListener(this);
		// 总工具栏

		jMenuBar1.add(jMenuFile);
		jMenuBar1.add(jMenuHelp);
		this.setJMenuBar(jMenuBar1);

		Container contents = getContentPane();
		jtp = new JTabbedPane(JTabbedPane.TOP);
		initButtonLayout();
		jtp.addTab("9PNG2PNG", mAllPanel);
		contents.add(jtp);
		setVisible(true);
	}

	public static void main(String args[]) {
		CommonUtil.setMySkin(2);
		new PNG9Viewer();
	}
	

}
