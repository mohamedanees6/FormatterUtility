package com.gamer.develop;

import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author mohamedanees
 *
 */
public class MagicBrick {

	private static final String ENTER_VALID_XML = "Enter valid XML";
	private static final String FORMAT_BUTTON_LABEL = "Format";
	private static final String CUSTOM_FORMATTER = "Custom Formatter";
	private static final String ENTER_XML_HERE = "Enter XML Here";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FlowLayout fl = new FlowLayout(100, 50, 40);
		final JFrame f = new JFrame(CUSTOM_FORMATTER);
		final JTextArea t1;
		t1 = new JTextArea(ENTER_XML_HERE, 10, 10);
		t1.setLineWrap(false);
		JScrollPane scroll = new JScrollPane(t1);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		t1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (t1.getText().equals(ENTER_XML_HERE)) {
					t1.setText("");
					t1.repaint();
				}
			}
		});
		t1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				t1.repaint();
			}
		});

		JButton button = new JButton(FORMAT_BUTTON_LABEL);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					t1.setText(prettyPrint(toXmlDocument(t1.getText().replaceAll("\\r\\n|\\r|\\n", ""))));
				} catch (Exception e1) {
					t1.setText(ENTER_VALID_XML);
				}
				f.repaint();
			}
		});
		button.setBounds(70, 100, 10, 10);
		f.getContentPane().setLayout(fl);
		f.add(scroll);
		f.add(button);
		f.setSize(600, 600);
		f.setVisible(true);
	}

	private static String prettyPrint(Document document) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		DOMSource source = new DOMSource(document);
		StringWriter strWriter = new StringWriter();
		StreamResult result = new StreamResult(strWriter);
		transformer.transform(source, result);
		return strWriter.getBuffer().toString();

	}

	private static Document toXmlDocument(String str) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new InputSource(new StringReader(str)));
		return document;
	}

}
