package net.itas.tools.map.fx;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.itas.tools.map.ElementType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


class EditPanel extends GridPane {

	/** 要保存列表*/
	private List<Cell> cellList;
	
	/** 可更换列表*/
	private List<Cell> changeList;

	/** 存在元素*/
	private Map<Integer, Cell> cellMap;
	
	
	public EditPanel() {
		this.changeList = new ArrayList<Cell>();
		this.cellList = new ArrayList<Cell>(400);
		this.cellMap = new HashMap<Integer, Cell>();
		
		ElementType[] types = ElementType.values();
		Cell cell;
		for (ElementType type : types) {
			cell = new Cell(type);
			cellMap.put(type.type, cell);
			addChangeList(type, cell);
		}
		
		setStyle("-fx-background-color: #E7CE9C");
	}
	
	@Override
	public void add(Node child, int columnIndex, int rowIndex) {
		cellList.add((Cell)child);
		super.add(child, columnIndex, rowIndex);
	}
	
	/** 创建新地图[6x6:338][3x6:182]*/
	public ByteBuffer newMap(int size) {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		
		for (int i = 0; i < 13; i++) {
			if (i % 2 == 0) {
				imparLine(buffer);
			} else {
				evenLine(buffer);
			}
			
			if (buffer.position() >= buffer.capacity()) {
				break;
			}
		}
		
		buffer.flip();
		return buffer;
	}
	
	/** 初始化地图偶数行*/
	private void imparLine(ByteBuffer buf) {
		for (int i = 0; i < 13; i++) {
			buf.putShort((short)((i % 2 == 0) ? 7 : 2));
		}
	}
	
	/** 初始化地图奇数行*/
	private void evenLine(ByteBuffer buf) {
		for (int i = 0; i < 13; i++) {
			buf.putShort((short)((i % 2 == 0) ? 2 : 1));
		}
	}
	
	public int drawMap(ByteBuffer buffer) {
		for (int row = 0; row < 13; row++) {
			if (!buffer.hasRemaining()) {
				return 480;
			}
			
			if (row % 2 == 0) {
				drawImparLine(row, buffer);
				getRowConstraints().add(new RowConstraints(30));
			} else {
				drawEvenLine(row, buffer);
				getRowConstraints().add(new RowConstraints(100));
			}
		}
		
		return 870;
	}
	
	private void drawImparLine(int row, ByteBuffer buf)  {
		for (int i = 0; i < 13; i++) {
			final Cell jLabel = getCell(buf.getShort(), row);
			jLabel.setBounds(this);
			this.add(jLabel, i, row);
			if (i % 2 != 0) {
				jLabel.setOnScroll(e->{
					jLabel.changeIcon(changeList, e.getDeltaY() > 0);
				});
			} 
		}
	}
	
	private void drawEvenLine(int row, ByteBuffer buf) {
		for (int i = 0; i < 13; i++) {
			final Cell jLabel = getCell(buf.getShort(), row);
			jLabel.setBounds(this);
			this.add(jLabel, i, row);
			
			if (i % 2 == 0) {
				jLabel.setOnScroll(e->{
					jLabel.changeIcon(changeList, e.getDeltaY() > 0);
				});
			} 
		}
	}
	
	
	private void addChangeList(ElementType type, Cell cell) {
		if (type == ElementType.skintype_1 || type == ElementType.skintype_7) {
			return;
		} 
			
		changeList.add(cell);
	}
	
	public List<Cell> getCellList() {
		return cellList;
	}
	
	private Cell getCell(int type, int row)  {
		return cellMap.get(type).copy(row);
	}
	
}
