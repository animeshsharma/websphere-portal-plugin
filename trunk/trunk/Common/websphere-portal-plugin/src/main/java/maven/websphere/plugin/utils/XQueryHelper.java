package maven.websphere.plugin.utils;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/**
 * Utility class to get the text content using xpath.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class XQueryHelper {
	public static String queryTextContent(String xml, String xquery) {
		String value = null;
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		try {
			XPathExpression q = xPath.compile(xquery);

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new ByteArrayInputStream(
					xml.getBytes("utf-8"))));

			Node node = (Node) q.evaluate(doc, XPathConstants.NODE);

			if (null != node) {
				value = node.getNodeValue();
				if (StringUtils.isBlank(value)) {
					value = node.getTextContent();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}
}
