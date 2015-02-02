package net.itas.tools.map.fx;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class CellIcon extends ImageView {

	private int width;

	private int hight;
	
	public CellIcon(int width, int hight) {
		super();
		this.width = width;
		this.hight = hight;
	}

	public CellIcon(Image image, int width, int hight) {
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
	
	@Override
	protected CellIcon clone()  {
		return new CellIcon(getImage(), width, hight);
	}

}
