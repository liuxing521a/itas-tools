package net.itas.tools.map.fx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MapEdit extends Application {

	 private EditPanel jpanel;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("地图编辑器");
		
		VBox box = new VBox();
		Scene scene = new Scene(box, 810, 840, Color.BLACK);
		stage.setScene(scene);

		MenuBar menus = new MenuBar();

		Menu mFile = createMenu("文件", stage, box);

		Menu mEdit = new Menu("编辑");
		MenuItem edit = new MenuItem("[未开放]");
		mEdit.getItems().add(edit);
		
		Menu mHelp = new Menu("帮助");
		MenuItem help = new MenuItem("[未开放]");
		mHelp.getItems().add(help);
		
		Menu mClose = new Menu("关闭");
		MenuItem close = new MenuItem("关闭");
		close.setOnAction(e-> { System.exit(0);});
		mClose.getItems().add(close);

		menus.getMenus().addAll(mFile, mEdit, mHelp, mClose);
		box.getChildren().add(menus);
		
		stage.show();
	}
	
	private Menu createMenu(String name, Stage stage, final VBox box) {
		Menu mFile = new Menu(name);
		
		MenuItem item6X6 = new MenuItem("创建6x6地图");
		item6X6.setOnAction(e->{ createMap(stage, box, 338);});
		
		MenuItem item3X6 = new MenuItem("创建3x6地图");
		item3X6.setOnAction(e->{ createMap(stage, box, 182);});

		MenuItem open = new MenuItem("打开");
		open.setOnAction(e->{openMap(stage, box);});
		
		MenuItem save = new MenuItem("保存");
		save.setOnAction(e->{saveMap(stage, box);});
		
		MenuItem close = new MenuItem("关闭");
		close.setOnAction(e-> { System.exit(0);});
		
		mFile.getItems().addAll(item6X6, item3X6, open, save, close);
		return mFile;
	}

	 public void createMap(Stage stage, VBox box, int size) {
		 if (Objects.nonNull(jpanel)) {
			 box.getChildren().remove(jpanel);
		 }
		
		 jpanel = new EditPanel();
		 ByteBuffer buf = jpanel.newMap(size);
		 int height = jpanel.drawMap(buf);
		
		 box.getChildren().add(jpanel);
		 stage.setHeight(height);
	 }
	
	
	 public void openMap(Stage stage, VBox box) {
		 if (Objects.nonNull(jpanel)) {
			 box.getChildren().remove(jpanel);
		 }
		
		 jpanel = new EditPanel();
		 box.getChildren().add(jpanel);
		
		 FileChooser chooser = new FileChooser();
		 chooser.getExtensionFilters().addAll(new ExtensionFilter("maps", "*.iux"));

		 File file = chooser.showOpenDialog(stage);
		 if (Objects.isNull(file)) {
			return;
		 }
		 
		 try (FileInputStream input = new FileInputStream(file);
			  FileChannel channel = input.getChannel()){
			 
			 ByteBuffer buffer = ByteBuffer.allocate(338);
			 channel.read(buffer);
			 buffer.flip();
			 int height = jpanel.drawMap(buffer);
			 stage.setHeight(height);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }
	
	 public void saveMap(Stage stage, final VBox box) {
		 if (Objects.isNull(jpanel)) {
			 return;
		 }
		 
		 FileChooser chooser = new FileChooser();
		 chooser.getExtensionFilters().addAll(new ExtensionFilter("maps", "*.iux"));
		 
		 File file = chooser.showSaveDialog(stage);
		 if (Objects.isNull(file)) {
		 	return;
		 }
		 
		 try (FileOutputStream output = new FileOutputStream(file);
			  FileChannel channel = output.getChannel()){
				
			 ByteBuffer buffer = ByteBuffer.allocate(338);
			 jpanel.getCellList().forEach(c->{buffer.putShort(c.getMapTypeValue());});;
			 
			 buffer.flip();
			 channel.write(buffer);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

	public static void main(String[] args) {
		launch(args);
	}

}
