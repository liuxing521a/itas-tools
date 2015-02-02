package net.itas.tools.map.swing;

import java.awt.Image;

import javax.swing.ImageIcon;

class CellImageIcon extends ImageIcon {

	private static final long serialVersionUID = -8519715596568006938L;

	private int width;
	
	private int hight;
	
	public CellImageIcon(int width, int hight) {
		super();
		this.width = width;
		this.hight = hight;
	}

	public CellImageIcon(Image image, int width, int hight) {
		super(image);
		this.width = width;
		this.hight = hight;
	}

	public int getWidth() {
		return width;
	}

	public int getHight() {
		return hight;
	}
	
}
