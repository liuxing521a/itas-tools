package net.itas.tools.map.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MapEdit extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int width;
	
	private int height;
	
	private EditPanel jpanel;

	public MapEdit() {
		this.width = 780;
		this.height = 800;
		
		setSize(width, height);
		initMenubar();

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initMenubar() {
		JMenuBar jMenubar = new JMenuBar();
		JMenu fileMenu = new JMenu("文件");
		jMenubar.add(fileMenu);
		JMenu editMenu = new JMenu("编辑");
		jMenubar.add(editMenu);
		JMenu helpMenu = new JMenu("帮助");
		jMenubar.add(helpMenu);
		JMenu closeMenu = new JMenu("关闭");
		jMenubar.add(closeMenu);

		JMenuItem create = new JMenuItem("创建6x6地图");
		fileMenu.add(create);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createMap();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem guid = new JMenuItem("创建3x6地图");
		fileMenu.add(guid);
		guid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createGuidMap();
					
					setSize(width, 450);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem open6x6 = new JMenuItem("打开6x6地图");
		fileMenu.add(open6x6);
		open6x6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openMap(13);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem open3x6 = new JMenuItem("打开3x6地图");
		fileMenu.add(open3x6);
		open3x6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openMap(7);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem save = new JMenuItem("保存");
		fileMenu.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveMap();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		this.add(jMenubar, BorderLayout.NORTH);
	}

	public void createMap() throws IOException {
		this.setVisible(false);
		
		if (jpanel != null) {
			this.remove(jpanel);
		}
		
		jpanel = new EditPanel();
		ByteBuffer buf = jpanel.newMap();
		jpanel.drawMap(buf, 13);
		
		this.add(jpanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void createGuidMap() throws IOException {
		this.setVisible(false);
		
		if (jpanel != null) {
			this.remove(jpanel);
		}
		
		jpanel = new EditPanel();
		ByteBuffer buf = jpanel.newMap();
		jpanel.drawGuidMap(buf);
		
		this.add(jpanel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public void openMap(int size) throws Exception {
		this.setVisible(false);
		if (jpanel != null) {
			this.remove(jpanel);
		}
		
		jpanel = new EditPanel();
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("iux", "iux");
		chooser.setFileFilter(filter); // 过滤器可以不要
		
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile().getAbsoluteFile();
			FileChannel channel = null;
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
				channel = input.getChannel();
				ByteBuffer buffer = ByteBuffer.allocate(768);
				channel.read(buffer);
				
				buffer.flip();
				jpanel.drawMap(buffer, size);
			} finally {
				channel.close();
				input.close();
			}
			this.add(jpanel, BorderLayout.CENTER);
		}
		this.setVisible(true);
	}
	
	public void openMap3x6() throws Exception {
		this.setVisible(false);
		if (jpanel != null) {
			this.remove(jpanel);
		}
		
		jpanel = new EditPanel();
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("iux", ".iux");
		chooser.setFileFilter(filter); // 过滤器可以不要

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile().getAbsoluteFile();
			FileChannel channel = null;
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);
				channel = input.getChannel();
				ByteBuffer buffer = ByteBuffer.allocate(768);
				channel.read(buffer);
				
				buffer.flip();
				jpanel.drawMap(buffer, 7);
			} finally {
				channel.close();
				input.close();
			}
			this.add(jpanel, BorderLayout.CENTER);
		}
		this.setVisible(true);
	}

	public void saveMap() throws Exception {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("iux", ".iux");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION && jpanel != null) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			if (!path.endsWith(".iux")) {
				path += ".iux";
			}
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			
			FileChannel channel = null;
			FileOutputStream output = null;
			
			try {
				output = new FileOutputStream(file);
				channel = output.getChannel();
				
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				List<Cell> cellList = jpanel.getCellList();
				Element element;
				for (Cell cell : cellList) {
					element = cell.getElement();
					buffer.putShort((short)element.getElmentType().type);
				}
				
				buffer.flip();
				channel.write(buffer);
			} finally {
				channel.close();
				output.close();
			}
		}
	}

	public static void main(String[] args) {
		new MapEdit();
	}

}
