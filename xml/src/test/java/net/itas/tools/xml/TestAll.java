package net.itas.tools.xml;

import net.itas.tools.xml.base.TestG_xml_attribute;
import net.itas.tools.xml.base.TestG_xml_file;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
//指定运行器
@Suite.SuiteClasses({ 
	TestG_xml_file.class,
	TestG_xml_attribute.class
	})
public class TestAll {
	
}
