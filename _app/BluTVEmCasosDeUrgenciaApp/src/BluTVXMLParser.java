
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

//CLASSE RESPONSÁVEL POR RECUPERAR OS DADOS CONTIDOS NO XML
public class BluTVXMLParser {

    BluTVConfigurationDataToStart configData;
    String dataFileName;
    BluTVTreeMap dataTreeMap;
    BluTVGenericXMLParser dataXMLParser;

    public BluTVXMLParser(String applicationType,
            String channelIDNumber,
            String applicationID, String dataXML) {
        configData = new BluTVConfigurationDataToStart();

        if (applicationType.equals("Broadcasted")) {
            dataFileName = configData.getFileSystemBroadcasters()
                    + configData.getChannelLabel() + channelIDNumber + "/"
                    + "broadcasted/applications/"
                    + configData.getApplicationLabel() + applicationID
                    + configData.getFileSystemPersistenceBroadcastedApplicationsData()
                    + "/" + dataXML;
        }

        if (applicationType.equals("Downloaded")) {
            dataFileName = configData.getFileSystemBroadcasters()
                    + configData.getChannelLabel() + channelIDNumber + "/"
                    + "downloaded/applications/"
                    + configData.getApplicationLabel() + applicationID
                    + configData.getFileSystemPersistenceBroadcastedApplicationsData()
                    + "/" + dataXML;
        }

        if (applicationType.equals("Resident")) {
            dataFileName = configData.getFileSystemBroadcasters()
                    + configData.getChannelLabel() + channelIDNumber + "/"
                    + "resident/applications/"
                    + configData.getApplicationLabel() + applicationID
                    + configData.getFileSystemPersistenceBroadcastedApplicationsData()
                    + "/" + dataXML;
        }

        if (applicationType.equals("Embedded")) {
            dataFileName = configData.getFileSystemPersistenceEmbeddedTerminalApplications()
                    + configData.getApplicationLabel() + applicationID
                    + configData.getFileSystemPersistenceEmbeddedTerminalApplicationsData()
                    + "/" + dataXML;
        }

        if (applicationType.equals("ReturnChannel")) {
            dataFileName = configData.getFileSystemReturnChannel()
                    + configData.getChannelLabel() + channelIDNumber + "/"
                    + configData.getApplicationLabel() + applicationID
                    + configData.getFileSystemReturnChannelApplicationsData()
                    + "/" + dataXML;
        }

        dataTreeMap = new BluTVTreeMap();
        try {
            dataXMLParser = new BluTVGenericXMLParser(dataFileName, dataTreeMap);
        } catch (IOException | IllegalArgumentException | BluTVGenericXMLParserAttributeUnboundException | BluTVGenericXMLParserItemTagUnboundException | BluTVGenericXMLParserElementTagUnboundException | ParserConfigurationException | SAXException dataException) {
            System.out.println("BluTVXMLDataApplicationDemo Notification: XML File not Found" + dataException.getMessage());
        }
    }

    public String[] getCompleteListOfResults(String element) {
        String[] results;

        try {
            results = dataTreeMap.getArrayById(element);
        } catch (Exception dataException) {
            System.out.println("BluTVXMLDataApplicationDemo Notification: " + dataException.getMessage());
            return null;
        }
        return results;
    }

    public String[] getPartialListOfResults(String element, String data) {
        int i, j;
        String[] results, dataValues;

        try {
            results = dataTreeMap.getArrayById(element);

            dataValues = new String[results.length];
            for (i = 0, j = 1; i < dataValues.length; i++, j++) {
                dataValues[i] = dataTreeMap.getValueById(element, data + Integer.toString(j));
            }
        } catch (BluTVGenericXMLParserIDValueUnboundException | IndexOutOfBoundsException dataException) {
            System.out.println("BluTVXMLDataApplicationDemo Notification: " + dataException.getMessage());
            return null;
        }

        return dataValues;
    }

    public void showValues() {
        int i;
        String[] results = getPartialListOfResults("states", "value");

        for (i = 0; i < results.length && results[i] != null; i++) {
            System.out.println(results[i]);
        }
    }

    public String[] returnValuesList(String value1, String value2) {
        String[] results = getPartialListOfResults(value1, value2);
        return results;
    }
}
