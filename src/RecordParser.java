

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecordParser {

	public static void parser() {

		try {
			File file = new File("acron_collection_UTF8.xml");
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"newFile.txt"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("record");
			String description = "";

			//System.out.println("Information of all records");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;

					NodeList fstNmElmntLst = fstElmnt
							.getElementsByTagName("id");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					NodeList fstNm = fstNmElmnt.getChildNodes();
					// description = description +((Node)
					// fstNm.item(0)).getNodeValue();
	//				System.out.print(((Node) fstNm.item(0)).getNodeValue() + "\t");
					out.write(((Node) fstNm.item(0)).getNodeValue() + " \t ");

					NodeList secNmElmntLst = fstElmnt
							.getElementsByTagName("title");
					Element secNmElmnt = (Element) secNmElmntLst.item(0);
					NodeList secNm = secNmElmnt.getChildNodes();
//					System.out.print(((Node) secNm.item(0)).getNodeValue()
//							+ "\t");
					out.write(((Node) secNm.item(0)).getNodeValue() + " \t ");

					// NodeList trdNmElmntLst = fstElmnt
					// .getElementsByTagName("author");
					// Element trdNmElmnt = (Element) trdNmElmntLst.item(0);
					// NodeList trdNm = trdNmElmnt.getChildNodes();
					// System.out.print(((Node) trdNm.item(0)).getNodeValue()
					// + "\t");
					// out.write(((Node) trdNm.item(0)).getNodeValue() + "\t");

					NodeList forNmElmntLst = fstElmnt
							.getElementsByTagName("text");
					Element forNmElmnt = (Element) forNmElmntLst.item(0);
					NodeList forNm = forNmElmnt.getChildNodes();
//					System.out.print(((Node) forNm.item(0)).getNodeValue()
//							+ "\t");
					out.write(((Node) forNm.item(0)).getNodeValue() + " /WW "
							+ " \n ");

				}

			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
