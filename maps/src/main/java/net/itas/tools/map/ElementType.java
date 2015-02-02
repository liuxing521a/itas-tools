package net.itas.tools.map;

/** 地图组成基本元素*/
public enum ElementType {

	skintype_1(1),		//格子
	skintype_2(2),		//格子边缘
	skintype_3(3),		//墙
	skintype_4(4),		//门
	skintype_5(5),		//火墙
	skintype_6(6),		//荆棘
	skintype_7(7),		//柱子
	;

	public final int type;
	
	ElementType(int type) {
		this.type = type;
	}
	
}
