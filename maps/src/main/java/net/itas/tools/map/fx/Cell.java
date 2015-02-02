package net.itas.tools.map.fx;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import net.itas.tools.map.ElementType;


class Cell extends Label {

	/** 元素所在行*/
	private int elementRow;
	
	/** 元素位数*/
	private int elementIndex;
	
	/** 当前的图标元素*/
	protected Element element;

	protected Cell(ElementType elmentType) {
		this.element = new Element(elmentType);
	}
	
	public void setBounds(GridPane pane) {
		CellIcon icon = element.getIcon(elementRow);
		pane.getColumnConstraints().add(new ColumnConstraints(icon.getWidth()));
		setPrefSize(icon.getWidth(), icon.getHight());
		setGraphic(icon);
	}
	
	public Element getElement() {
		return element;
	}
	
	public Cell copy(int row) {
		this.elementRow = row;
		Cell cell = new Cell(element.getElmentType());
		cell.elementRow = row;
		
		return cell;
	}
	
	public void changeIcon(List<Cell> cellList, boolean isInc) {
		int indexValue = isInc ? addIndex(cellList) : decIndex(cellList);;
		
		this.element = cellList.get(indexValue % cellList.size()).element;
		setGraphic(element.getIcon(elementRow));
	}
	
	public short getMapTypeValue() {
		return (short)element.getElmentType().type;
	}
	
	private int addIndex(List<Cell> cellList) {
		this.elementIndex ++;
		if (elementIndex > cellList.size()) {
			elementIndex = 0;
		}
		
		return elementIndex;
	}

	private int decIndex(List<Cell> cellList) {
		this.elementIndex --;
		if (elementIndex < 0) {
			elementIndex = cellList.size();
		}
		
		return elementIndex;
	}

}
