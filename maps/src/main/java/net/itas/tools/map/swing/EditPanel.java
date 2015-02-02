package net.itas.tools.map.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import net.itas.tools.map.ElementType;


class EditPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/** 要保存列表*/
	private List<Cell> cellList;
	
	/** 可更换列表*/
	private List<Cell> changeList;

	/** 存在元素*/
	private Map<Integer, Cell> cellMap;
	
	
	
	public EditPanel() throws IOException {
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
		
		this.setLayout(null);
		this.setBackground(new Color(231, 206, 156));
	}
	
	public Component addCell(Cell cell) {
		cellList.add(cell);
		return super.add(cell);
	}
	
	/** 创建新地图*/
	public ByteBuffer newMap() {
		ByteBuffer buffer = ByteBuffer.allocate(768);
		
		for (int i = 0; i < 13; i++) {
			if (i % 2 == 0) {
				imparLine(buffer);
			} else {
				evenLine(buffer);
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
	
	public void drawMap(ByteBuffer buffer, int number) throws IOException {
		int posY = 0;
		for (int row = 0; row < number; row++) {
			if (row % 2 == 0) {
				drawImparLine(row, posY, buffer);
				posY += 20;
			} else {
				drawEvenLine(row, posY, buffer);
				posY += 100;
			}
		}
	}
	
	public void drawGuidMap(ByteBuffer buffer) throws IOException {
		int posY = 0;
		for (int row = 0; row < 7; row++) {
			if (row % 2 == 0) {
				drawImparLine(row, posY, buffer);
				posY += 20;
			} else {
				drawEvenLine(row, posY, buffer);
				posY += 100;
			}
		}
	}
	
	private void drawImparLine(int row, int posY, ByteBuffer buf) throws IOException {
		int beginX = 0;
		for (int i = 0; i < 13; i++) {
			if (i % 2 == 0) {
				final Cell jLabel = getCell(buf.getShort(), row);
				jLabel.setBounds(beginX, posY);
				beginX += 20;
				this.addCell(jLabel);
			} else {
				final Cell jLabel = getCell(buf.getShort(), row);
				jLabel.addMouseWheelListener(new MouseWheelListener() {
					@Override
					public void mouseWheelMoved(MouseWheelEvent e) {
						if (e.getWheelRotation() > 0) {
							jLabel.addIndex(changeList);
						} else {
							jLabel.decIndex(changeList);
						}
						jLabel.repaint();
					}
				});
				jLabel.setBounds(beginX, posY);
				beginX += 102;
				this.addCell(jLabel);
			}
		}
	}
	
	private void drawEvenLine(int row, int posY, ByteBuffer buf) throws IOException {
		int beginX = 0;
		for (int i = 0; i < 13; i++) {
			
			if (i % 2 == 0) {
				final Cell jLabel = getCell(buf.getShort(), row);
				jLabel.addMouseWheelListener(new MouseWheelListener() {
					@Override
					public void mouseWheelMoved(MouseWheelEvent e) {
						if (e.getWheelRotation() > 0) {
							jLabel.addIndex(changeList);
						} else {
							jLabel.decIndex(changeList);
						}
						jLabel.repaint();
					}
				});
				jLabel.setBounds(beginX, posY);
				beginX += 20;
				this.addCell(jLabel);
			} else {
				final Cell jLabel = getCell(buf.getShort(), row);
				jLabel.setBounds(beginX, posY);
				beginX += 102;
				this.addCell(jLabel);
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
	
	private Cell getCell(int type, int row) throws IOException {
		return cellMap.get(type).copy(row);
	}
	
}
