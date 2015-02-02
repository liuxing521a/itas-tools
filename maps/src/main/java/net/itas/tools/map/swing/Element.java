package net.itas.tools.map.swing;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.itas.tools.map.ElementType;

/**
 * <p>地图元素</p>
 * @author liu
 */
class Element {
	
	/** 元素类型*/
	private ElementType elmentType;
	/** 图标*/
	private CellImageIcon icon_0;
	/** 图标*/
	private CellImageIcon icon_1;
	
	public Element(ElementType elmentType) throws IOException {
		this.elmentType = elmentType;
		if (elmentType == ElementType.skintype_1) {
			Image image = ImageIO.read(this.getClass().getResourceAsStream("/" + elmentType.type + "_0.png"));
			icon_0 = new CellImageIcon(image, 100, 100);
			icon_1 = new CellImageIcon(image, 100, 100);
		} else if (elmentType == ElementType.skintype_2) {
			icon_0 = new CellImageIcon(100, 20);
			icon_1 = new CellImageIcon(20, 100);
		} else if (elmentType == ElementType.skintype_7) {
			icon_0 = new CellImageIcon(20, 20);
			icon_1 = new CellImageIcon(20, 20);
		} else {
			Image image = ImageIO.read(this.getClass().getResourceAsStream("/" + elmentType.type + "_0.png"));
			icon_0 = new CellImageIcon(image, 100, 20);
			image = ImageIO.read(this.getClass().getResourceAsStream("/" + elmentType.type + "_1.png"));
			icon_1 = new CellImageIcon(image, 20, 100);
		}
	}

	public ElementType getElmentType() {
		return elmentType;
	}

	public CellImageIcon getIcon(int row) {
		return (row % 2) == 0 ? icon_0 : icon_1;
	}
	
}
