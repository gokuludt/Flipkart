package framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import framework.BrowserTypes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

/**
 * @author Gokul This class is used as Driver Factory
 *
 */
public class DriverFactory {

	public WebDriver driver;

	public void initializeDriver() {
		BrowserTypes browserType = null;
		browserType = BrowserTypes.valueOf(getProperty("Browser"));
		switch (browserType) {
		case chrome:
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/main/resources/browserDrivers/chromedriver.exe");
			driver = new ChromeDriver();
			Reporter.log("Chrome Browser launched");
			break;
		case firefox:
			System.setProperty("webdriver.firefox.marionette",
					System.getProperty("user.dir") + "/src/main/resources/browserDrivers/geckodriver.exe");
			driver = new FirefoxDriver();
			Reporter.log("Firefox Browser launched");
			break;
		default:
			throw new RuntimeException("Browser Name not found");
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void quitDriver() {
		driver.quit();
	}

	public String getProperty(String name) {
		Properties prop = new Properties();
		InputStream input = null;
		String propertyName = null;
		try {

			input = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config/input.properties");

			// load a properties file
			prop.load(input);

			// get the property value and
			propertyName = prop.getProperty(name);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return propertyName;
	}

}