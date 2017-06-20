package com.ikags.png9transer;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class PNGLogicManager {

	public int[] create4matirx(BufferedImage image){
		int[] intlines=new int[4];
		if(image!=null){
			//按照上下左右的顺序,返回8个坐标点
			int width=image.getWidth();
			int height=image.getHeight();
			//上1
			for(int i=0;i<width;i++){
				int arpg_color=image.getRGB(i, 0);
				if(arpg_color==0xff000000){
					intlines[0]=i;
					break;
				}
			}
			//上2
			for(int i=width-1;i>=0;i--){
				int arpg_color=image.getRGB(i, 0);
				if(arpg_color==0xff000000){
					intlines[1]=i+1;
					break;
				}
			}
			
			//左1
			for(int i=0;i<height;i++){
				int arpg_color=image.getRGB(0,i);
				if(arpg_color==0xff000000){
					intlines[2]=i;
					break;
				}
			}
			//左2
			for(int i=height-1;i>=0;i--){
				int arpg_color=image.getRGB(0,i);
				if(arpg_color==0xff000000){
					intlines[3]=i+1;
					break;
				}
			}
			
		}
		return intlines;
	}
	
	
	public BufferedImage[] create9pics(BufferedImage image,int[] newrage){
		//根据int[]把图切成9个
		BufferedImage[] bfimg=new BufferedImage[9];
		Graphics gg ;
		for(int i=0;i<bfimg.length;i++){
			try{
			switch(i){
			case 0:
				bfimg[i] = new BufferedImage(newrage[0]-1, newrage[2]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-1,-1, null);
			break;
			case 1:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0], newrage[2]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[0],-1, null);
			break;
			case 2:
				bfimg[i] = new BufferedImage(image.getWidth()-newrage[1]-1, newrage[2]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[1],-1, null);
			break;
			
			
			case 3:
				bfimg[i] = new BufferedImage(newrage[0]-1, newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-1,-newrage[2], null);
			break;
			case 4:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0], newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[0],-newrage[2], null);
			break;
			case 5:
				bfimg[i] = new BufferedImage(image.getWidth()-newrage[1]-1, newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[1],-newrage[2], null);
			break;
			
			
			case 6:
				bfimg[i] = new BufferedImage(newrage[0]-1, image.getHeight()-newrage[3]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-1,-newrage[3], null);
			break;
			case 7:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0], image.getHeight()-newrage[3]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[0],-newrage[3], null);
			break;
			case 8:
				bfimg[i] = new BufferedImage(image.getWidth()-newrage[1]-1,  image.getHeight()-newrage[3]-1, BufferedImage.TYPE_INT_ARGB);
				 gg = bfimg[i] .getGraphics();
				gg.drawImage(image,-newrage[1],-newrage[3], null);
			break;
			}
			System.out.println(i+"=="+bfimg[i].getWidth()+","+bfimg[i].getHeight());
			}catch(Exception ex){
				//ex.printStackTrace();
				bfimg[i] = new BufferedImage(1,  1, BufferedImage.TYPE_INT_ARGB);
			}

		}
		return bfimg;
	}
	
	
	
	public int[] createNewMatirx(int newwidth,int newheight, BufferedImage[] orgimg){
		int[] intlines=new int[4];
		intlines[0]=orgimg[3].getWidth();
		intlines[1]=newwidth-orgimg[5].getWidth();
		intlines[2]=orgimg[1].getHeight();
		intlines[3]=newheight-orgimg[7].getHeight();
		return intlines;
	}
	
	
	public BufferedImage[] createNewpics(BufferedImage[] image,int[] newrage,int newwidth,int newheight){
		//根据int[]把图切成9个
		BufferedImage[] bfimg=new BufferedImage[9];
		Graphics gg ;
		for(int i=0;i<bfimg.length;i++){
			try{
			switch(i){
			case 0:
				bfimg[i] = new BufferedImage(newrage[0], newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 1:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0], newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 2:
				bfimg[i] = new BufferedImage(newwidth-newrage[1], newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			
			
			case 3:
				bfimg[i] = new BufferedImage(newrage[0], newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 4:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0], newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 5:
				bfimg[i] = new BufferedImage(newwidth-newrage[1], newrage[3]-newrage[2], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			
			
			case 6:
				bfimg[i] = new BufferedImage(newrage[0],newheight-newrage[3], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 7:
				bfimg[i] = new BufferedImage(newrage[1]-newrage[0],newheight-newrage[3], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			case 8:
				bfimg[i] = new BufferedImage(newwidth-newrage[1],newheight-newrage[3], BufferedImage.TYPE_INT_ARGB);
				scaleImage(image[i],bfimg[i]);
			break;
			}
			System.out.println(i+"=="+bfimg[i].getWidth()+","+bfimg[i].getHeight());
			}catch(Exception ex){
				//ex.printStackTrace();
				bfimg[i] = new BufferedImage(1,  1, BufferedImage.TYPE_INT_ARGB);
			}

		}
		return bfimg;
	}
	
	
	
	// 旋转
	public static AffineTransform transform;
	public static AffineTransformOp op;

	private static BufferedImage scaleImage(BufferedImage oldimg,BufferedImage filteredImage){
		double sx=(double)filteredImage.getWidth()/(double)oldimg.getWidth();
		double sy=(double)filteredImage.getHeight()/(double)oldimg.getHeight();
			transform = AffineTransform.getScaleInstance(sx, sy);
			op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			op.filter(oldimg, filteredImage);
		return filteredImage;
	}
	
	
}
