package net.itas.tools.map;

public class Main {
	
	public static void main(String[] args) {
		String type = "fx";
		
		if (args.length > 0) {
			type = args[0];
		}
		
		if ("fx".equals(type)) {
			net.itas.tools.map.fx.MapEdit.main(args);
		}
		
		if ("swing".equals(type)) {
			net.itas.tools.map.swing.MapEdit.main(args);
		}
	}
	
}
