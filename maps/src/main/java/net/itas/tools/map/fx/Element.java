package net.itas.tools.map.fx;

import net.itas.tools.map.ElementType;
import javafx.scene.image.Image;

/**
 * <p>地图元素</p>
 * @author liu
 */
class Element {
	
	/** 元素类型*/
	private ElementType elmentType;
	/** 图标*/
	private CellIcon icon_0;
	/** 图标*/
	private CellIcon icon_1;
	
	public Element(ElementType elmentType) {
		this.elmentType = elmentType;
		if (elmentType == ElementType.skintype_1) {
			Image image = new Image(this.getClass().getResourceAsStream("/" + elmentType.type + "_0.png"));
			icon_0 = new CellIcon(image, 100, 100);
			icon_1 = new CellIcon(image, 100, 100);
		} else if (elmentType == ElementType.skintype_2) {
			icon_0 = new CellIcon(100, 30);
			icon_1 = new CellIcon(30, 100);
		} else if (elmentType == ElementType.skintype_7) {
			icon_0 = new CellIcon(30, 30);
			icon_1 = new CellIcon(30, 30);
		} else {
			Image image = new Image(this.getClass().getResourceAsStream("/" + elmentType.type + "_0.png"));
			icon_0 = new CellIcon(image, 100, 30);
			image = new Image(this.getClass().getResourceAsStream("/" + elmentType.type + "_1.png"));
			icon_1 = new CellIcon(image, 30, 100);
		}
	}

	public ElementType getElmentType() {
		return elmentType;
	}

	public CellIcon getIcon(int row) {
		return (row % 2) == 0 ? icon_0.clone() : icon_1.clone();
	}
}
