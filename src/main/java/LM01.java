import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LM01 {
    public static void main(String[] args) throws FileNotFoundException {
        Config conf = new Config("src/main/resources/config.properties");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        driver.get(conf.getProperty("URL"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sap-password")));
        driver.findElement(By.id("sap-password")).sendKeys(conf.getProperty("password"));
        driver.findElement(By.className("MobileLoginStdBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("label"))).click();
        driver.findElement(By.className("MobileLoginStdBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text4[1]"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text1[1]"))).click();

        String warehousePlacesConfig = conf.getProperty("warehousePlaces");
        Hashtable<String,Integer> warehousePlaces = new Hashtable<>();

        for(String wp: warehousePlacesConfig.split(",")){ warehousePlaces.put(wp,0);}
        Set<String> keys = warehousePlaces.keySet();
        Integer sum = 0;
        for(String key:keys){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lagp-lgpla[1]"))).sendKeys(key + Keys.RETURN);
            int count = 0;
            for (int i = 1; i < 10; i++) {
                String val = driver.findElement(By.id("wa-verme[" + i + "]")).getAttribute("value").trim();
                if (val.matches("-?\\d+")) { count += Integer.parseInt(val);}
            }
            sum += count;
            warehousePlaces.replace(key,count);
        }
        System.out.println(warehousePlaces);
        System.out.println(sum);
        driver.close();

        FileOutputStream fos = new FileOutputStream("tmp.xml");
        XMLEncoder e = new XMLEncoder(fos);
        e.writeObject(warehousePlaces);
        e.close();
        }
    }
