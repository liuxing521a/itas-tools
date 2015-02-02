package net.itas.tools.map.swing;

import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;

import net.itas.tools.map.ElementType;

class Cell extends JLabel {

	private static final long serialVersionUID = 5220813226956161587L;
	
	/** 元素所在行*/
	private int elementRow;
	
	/** 元素位数*/
	private int elementIndex;
	
	/** 当前的图标元素*/
	protected Element element;

	protected Cell(ElementType elmentType) throws IOException {
		this.element = new Element(elmentType);
	}
	
	public void setBounds(int x, int y) {
		CellImageIcon icon = element.getIcon(elementRow);
		super.setIcon(icon);
		super.setBounds(x, y, icon.getWidth(), icon.getHight());
	}
	
	public Element getElement() {
		return element;
	}
	
	public Cell copy(int row) throws IOException {
		this.elementRow = row;
		Cell cell = new Cell(element.getElmentType());
		cell.elementRow = row;
		
		return cell;
	}
	
	public void addIndex(List<Cell> cellList) {
		this.elementIndex ++;
		if (elementIndex > cellList.size()) elementIndex = 0;
		
		int index = elementIndex % cellList.size();
		this.element = cellList.get(index).element;
		super.setIcon(element.getIcon(elementRow));
	}

	public void decIndex(List<Cell> cellList) {
		this.elementIndex --;
		if (elementIndex < 0) elementIndex = cellList.size();
		
		int index = elementIndex % cellList.size();
		this.element = cellList.get(index).element;
		super.setIcon(element.getIcon(elementRow));
	}
	
	

}
