import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UMLParser extends DefaultHandler {

	private String classModel = "uml:Class";
	private String stateMachine = "uml:StateMachine";
	private boolean show = false;

	public List<String> options = null;

	public UMLParser() {
		options = new ArrayList<String>();
	}

	public static void main(String[] args) throws Exception {

		new UMLParser().process();

	}

	public void process() throws SAXException, IOException, ParserConfigurationException {
		DefaultHandler handler = new UMLParser();

		SAXParserFactory factory = SAXParserFactory.newInstance();

		factory.setValidating(false);

		SAXParser parser = factory.newSAXParser();

		parser.parse(new File("PingPong.uml"), handler);
		
	}

	public void getOptions() {
		System.out.println(options);
	}

	@Override

	public void startElement(String uri, String localName, String qName,

	Attributes attributes) throws SAXException {
		int length = attributes.getLength();
		for (int i = 0; i < length; i++) {

			String name = attributes.getQName(i);
			if (name.equals("xmi:type")) {
				if (isClassDiag(attributes.getValue(i)))
					show = true;
				else
					show = false;
				// if (show)
				// System.out.println(attributes.getValue(i));
			}
			if (name.equals("name")) {
				if (show) {
					// System.out.println(attributes.getValue(i));
					options.add(attributes.getValue(i));
				}
			}
		}

		
	}

	private boolean isClassDiag(String name) {
		if (name.equals(classModel)) {
			return true;
		}

		if (name.equals(stateMachine)) {
			getOptions();
			System.exit(1);
			return false;
		}

		return true;
	}
}
