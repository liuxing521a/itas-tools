package net.itas.tools.xml.base;

import junit.framework.Assert;
import net.itas.tools.xls.base.g_sheet;

import org.junit.Test;

public class TestG_xml_attribute {

	@Test
	public void testAllParseAttributeName() {
		g_sheet g_xml_file = new g_sheet("中国人(nihao)");
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_client());
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_server());
	}
	
	@Test
	public void testClientParseAttributeName() {
		g_sheet g_xml_file = new g_sheet("中国人(c@nihao)");
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_client());
		Assert.assertEquals(null, g_xml_file.getXml_file_name_server());
	}
	
	@Test
	public void testServerParseAttributeName() {
		g_sheet g_xml_file = new g_sheet("中国人(s@nihao)");
		Assert.assertEquals(null, g_xml_file.getXml_file_name_client());
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_server());
	}
	
	@Test
	public void testServerAndClientParseAttributeName() {
		g_sheet g_xml_file = new g_sheet("中国人(sc@nihao)");
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_client());
		Assert.assertEquals("nihao", g_xml_file.getXml_file_name_server());
	}
	
}
