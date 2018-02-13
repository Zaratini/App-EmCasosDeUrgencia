
/**
 * BluTV - Bringing All Users To The Television Platform					    
 * Engine  BluTVGenericXMLParser			   				            
 * Module  BluTVGenericXMLParser.java
 * @description Implements a generic XML Parser for general purposes and uses on
 * BluTV Platform
 * @author Prof. Joao Benedito dos Santos Junior, Ph.D.
 * @author Rinaldi Fonseca Nascimento									
 * @date May-October, 2009
 */
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Essential class to construct a new BluTVGenericXMLParser object
 */
public class BluTVGenericXMLParser {

    private static TreeMap<String, String> temporaryMap;
    private DocumentBuilderFactory builderDocument;
    private DocumentBuilder constructorDocument;
    private Document xmlDocument;
    private Element xmlElement;
    private Element xmlAuxiliarElement;
    private NodeList listOfTreeNodes, listOfTreeChild;
    private File xmlFile;
    private String charSetName;

    /**
     * Construct a new BluTVGenericXMLParser
     * @param  xmlFileName name of the XML file to parser
     * @param  map a BluTVTreeMap object for receiving all data recovered from XML file
     */
    public BluTVGenericXMLParser(String xmlFileName, BluTVTreeMap map) throws
            FileNotFoundException,
            IOException,
            IllegalArgumentException,
            BluTVGenericXMLParserAttributeUnboundException,
            BluTVGenericXMLParserItemTagUnboundException,
            BluTVGenericXMLParserElementTagUnboundException,
            ParserConfigurationException,
            SAXException {
        this.charSetName = null;
        loadDataFromXMLFile(xmlFileName, map);
    }

    /**
     * Construct a new BluTVGenericXMLParser
     * @param  string XML string object to parser
     * @param charSet set of chars for parsing
     * @param  map a BluTVTreeMap object for receiving all data recovered from XML file
     */
    public BluTVGenericXMLParser(String string, String charSet,
            BluTVTreeMap map) throws FileNotFoundException,
            IOException,
            IllegalArgumentException,
            BluTVGenericXMLParserAttributeUnboundException,
            BluTVGenericXMLParserItemTagUnboundException,
            BluTVGenericXMLParserElementTagUnboundException,
            ParserConfigurationException,
            SAXException {
        this.charSetName = charSet;
        loadDataFromXMLFile(string, map);
    }

    /**
     * Parse the XML file searching by tags <element> and <item>
     * @param  xmlFileName name of the xML file to parser
     * @param  map a BluTVTreeMap object for receiving all data recovered from XML file
     * @return void
     */
    private void loadDataFromXMLFile(String xmlFileName, BluTVTreeMap map) throws
            ParserConfigurationException,
            IOException,
            IllegalArgumentException,
            BluTVGenericXMLParserAttributeUnboundException,
            BluTVGenericXMLParserItemTagUnboundException,
            BluTVGenericXMLParserElementTagUnboundException,
            SAXException {
        builderDocument = DocumentBuilderFactory.newInstance();
        constructorDocument = builderDocument.newDocumentBuilder();

        xmlFile = new File(xmlFileName);
        if (xmlFile.exists()) {
            if (this.charSetName == null) {
                xmlDocument = constructorDocument.parse(xmlFileName);
            } else {
                xmlDocument = constructorDocument.parse(new InputSource(new InputStreamReader(new FileInputStream(xmlFile), this.charSetName)));
            }
        } else {
            InputSource inputSource = new InputSource(new ByteArrayInputStream(xmlFileName.getBytes()));
            xmlDocument = constructorDocument.parse(inputSource);
        }

        xmlElement = xmlDocument.getDocumentElement();
        listOfTreeNodes = xmlElement.getElementsByTagName("element");

        if (listOfTreeNodes.getLength() > 0) {
            for (int i = 0; i < listOfTreeNodes.getLength(); i++) {
                temporaryMap = new TreeMap<String, String>();
                xmlAuxiliarElement = (Element) listOfTreeNodes.item(i);
                listOfTreeChild = xmlAuxiliarElement.getElementsByTagName("data");

                // each tag <element> must have attribute "id"
                temporaryMap.put("id", xmlAuxiliarElement.getAttribute("id"));

                if (listOfTreeChild.getLength() > 0) // parsing all tags <item>
                {
                    for (int j = 0; j < listOfTreeChild.getLength(); j++) {
                        // recovering the list of attributes associated to <item>
                        NamedNodeMap atributos = listOfTreeChild.item(j).getAttributes();

                        // parsing the attributes and adding to BluTVTreeMap
                        if (atributos.getLength() > 0) {
                            for (int k = 0; k < atributos.getLength(); k++) {
                                temporaryMap.put(atributos.item(k).getNodeName(), atributos.item(k).getNodeValue());
                            }
                        } else {
                            throw new BluTVGenericXMLParserAttributeUnboundException("BluTVGenericXMLParser Notification: There is some tag \"item\" without attributes");
                        }
                    }
                } else {
                    throw new BluTVGenericXMLParserItemTagUnboundException("BluTVGenericXMLParser Notification: There is some tag element without tag \"item\"");
                }

                // any tag <element> has it yourself map of elements
                map.add(temporaryMap);
            }
        } else {
            throw new BluTVGenericXMLParserElementTagUnboundException("BluTVGenericXMLParser Notification: The XML Document has not tags <element>");
        }
    }
}
