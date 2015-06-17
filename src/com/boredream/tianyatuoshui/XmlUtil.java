package com.boredream.tianyatuoshui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.io.XMLWriter;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class XmlUtil {

//	static SAXReader saxReader = new SAXReader();
//	
//	/**
//	 * 获得Xml文档对象
//	 */
//	public static Document readFromString(String xmlStr) {
//		StringReader sr = new StringReader(xmlStr); 
//		InputSource is = new InputSource(sr); 
//		
//		Document document = null;
//		try {
//			document = saxReader.read(is);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
//
//	/**
//	 * 获得Xml文档对象
//	 */
//	public static Document read(File xmlFile) {
//		Document document = null;
//		try {
//			document = saxReader.read(xmlFile);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
//
//	/**
//	 * 获得Xml文档对象
//	 * 
//	 * @param xmlFileName xml文件的绝对路径
//	 */
//	public static Document read(String xmlFileName) {
//		return read(new File(xmlFileName));
//	}
//
//	/**
//	 * 通过指向xml 文件的URL获得Document对象
//	 * 
//	 * @param url
//	 * @return Document
//	 */
//	public static Document read(URL url) {
//		Document document = null;
//		try {
//			document = saxReader.read(url);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
	
	/**
	 * 递归获取到的element集合
	 */
	static List<Element> elements = new ArrayList<Element>();
	/**
	 * 递归获取全部element
	 * 
	 * @param parentElement	发起递归的根element
	 */
	public static void getElements(Element parentElement) {
		@SuppressWarnings("unchecked")
		List<Element> elementList = parentElement.getAllElements();
		for(Element element : elementList) {
			elements.add(element);
			if(element.getAllElements().size() > 0) {
				getElements(element);
			}
		}
	}
	
//	/**
//	 * 获取document下全部element
//	 * @param Document
//	 * @return
//	 */
//	public static List<Element> getAllElements(Document doc) {
//		Element rootElement = doc.getRootElement();
//		
//		elements = new ArrayList<Element>();
//		getElements(rootElement);
//		return elements;
//	}
	
	/**
	 * 是否包含某属性值
	 * @param element	需要查询的节点
	 * @param attrName	参数名(注android:这样的命名空间不用加)
	 * @return
	 */
	public static boolean hasAttribute(Element element, String attrName) {
		@SuppressWarnings("unchecked")
		Attributes attributes = element.attributes();
		for(Attribute attr : attributes) {
			if(attrName.equals(attr.getKey())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将Document写回至xml文件
	 * @param file
	 * @param doc
	 */
	public static void write2xml(File file, Document doc) {
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(file));
			writer.write(doc);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}