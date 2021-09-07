import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class Config {
    private final String configPath = "";
    private FileInputStream propertyFile;
    public Properties PROPERTIES;

    public Config(String path) {
        try {
            propertyFile = new FileInputStream(path);
            PROPERTIES = new Properties();
            PROPERTIES.load(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String val){
        return PROPERTIES.getProperty(val);
    }
    public int size(){return PROPERTIES.size();}
}
