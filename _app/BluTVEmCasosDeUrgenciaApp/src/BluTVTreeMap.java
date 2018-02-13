
/**
 * BluTV - Bringing All Users To The Television Platform					    
 * Engine  BluTVGenericXMLParser			   				            
 * Module  BluTVTreeMap.java
 * @author Prof. Joao Benedito dos Santos Junior, Ph.D.
 * @author Rinaldi Fonseca Nascimento									
 * @date May-October, 2009
 */
import java.util.*;

public class BluTVTreeMap {

    @SuppressWarnings("unchecked")
    private ArrayList<TreeMap> mapList;

    /**
     * Construct a list for receiving data
     */
    @SuppressWarnings("unchecked")
    public BluTVTreeMap() {
        mapList = new ArrayList<TreeMap>();
    }

    /**
     * Add a map on list of maps
     * @param element - the map of current element
     * @return void
     *
     */
    protected void add(TreeMap<?, ?> element) {
        mapList.add(element);
    }

    /**
     * Get the value of first element indicated by key
     * @param  key - name of attribute on item tag
     * @return String
     */
    public String getValue(String key) {
        for (int i = 0; i < this.getMapList().size(); i++) {
            if ((String) mapList.get(i).get(key) != null) {
                return (String) mapList.get(i).get(key);
            }
        }

        return "";
    }

    /**
     * Get the value of attribute based on level of the element
     * Level 0 (zero) - reference to first tag <element> on document
     * @param  key - name of attribute on tag <item>
     * @param  level - level of the attribute based on document map
     * @return String
     */
    public String getValue(String key, int level) throws IndexOutOfBoundsException {
        // The number of maps is equal to number of levels on structure tree
        if (level > mapList.size()) {
            return "";
        }

        try {
            return (String) mapList.get(level).get(key);
        } catch (IndexOutOfBoundsException jitvException) {
            System.out.println("BluTVGenericXMLParser Notification: Index Unbound");
            jitvException.printStackTrace();
        }
        return "";
    }

    /**
     * Get the value of attribute based on "id" value on tag <item>
     * @param  id - identification of tag <element>
     * @param  attribute - name of the attribute
     * @return String
     */
    public String getValueById(String id, String attribute) throws IndexOutOfBoundsException {
        for (int i = 0; i < this.getMapList().size(); i++) {
            // id value found
            if (this.getMapList().get(i).get("id").equals(id)) {
                if (this.getMapList().get(i).containsKey(attribute)) {
                    return (String) this.getMapList().get(i).get(attribute);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Get an array of values based on "id" value
     * @param id - the id value of the attribute
     * @return String[]
     */
    @SuppressWarnings("unchecked")
    public String[] getArrayById(String id) throws BluTVGenericXMLParserIDValueUnboundException {
        boolean idFlagExists = false;
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < this.getMapList().size(); i++) {
            // id found
            if (this.getMapList().get(i).get("id").equals(id)) {
                idFlagExists = true;
                Collection collection = this.getMapList().get(i).values();
                Iterator iterator = collection.iterator();

                while (iterator.hasNext()) {
                    String temp = (String) iterator.next();
                    if (!temp.equals(id)) {
                        list.add(temp);
                    }
                }
            }
        }

        // if any "id" found
        if (idFlagExists == false) {
            throw new BluTVGenericXMLParserIDValueUnboundException("BluTVGenericXMLParser Notification: the ID \"" + id + "\" not Found in XML Document");
        }

        String[] arrayOfValues = list.toArray(new String[list.size()]);

        return arrayOfValues;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<TreeMap> getMapList() {
        return mapList;
    }
}
