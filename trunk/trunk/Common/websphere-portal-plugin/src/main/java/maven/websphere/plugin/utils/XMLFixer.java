package maven.websphere.plugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLFixer {

	private static final Log log = LogFactory.getLog(XMLFixer.class);
	private static final String namespace_uri = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String namespace_attr = "xmlns:xsi";

	public static void fixNamespace(File inputXMLFile) {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(inputXMLFile));

			Attr namespace = DOMUtil.getRoot(doc).getAttributeNode(
					namespace_attr);
			log.info("Replace DOM namespace:[" + namespace.getValue()
					+ "] with " + namespace_uri);
			namespace.setValue(namespace_uri);
			// replace file content
			Source source = new DOMSource(doc);
			Result result = new StreamResult(inputXMLFile);
			Transformer xformer = TransformerFactory.newInstance()
					.newTransformer();
			xformer.transform(source, result);
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (TransformerConfigurationException e) {

			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {

			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}
	}
}
