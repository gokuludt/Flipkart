package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import pages.pageElements.Master;

/**
 * @author Gokul
 * This class is used as common library functions
 *
 */
public class ReusableLibrary implements Master {

	public WebDriver driver;
	public Properties prop;
	public FileInputStream fin;
	
	public ReusableLibrary(WebDriver driver) {
		this.driver=driver;
	}

	public void enterText(By field, String value) {
		try {
			WebElement element = driver.findElement(field);
			element.clear();
			element.sendKeys(value);
			//updateTestLog(true, value + " is entered in the webpage");
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, value + " is not entered in the webpage");

		}
	}

	public void clickElement(By field, String desc) {
		try {
			WebElement element = driver.findElement(field);
			element.click();
			updateTestLog(true, desc + " is clicked in the webpage");
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, desc + " is not clicked in the webpage");
		}
	}

	public void switchToDefault() {
		try {
			driver.switchTo().defaultContent();
			updateTestLog(true, "default Content is loaded in the webpage");
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, "default Content is not loaded in the webpage");
		}
	}

	public void comboSelectByValue(By field, String value, String desc) {
		try {
			Select element = new Select(driver.findElement(field));
			element.selectByVisibleText(value);
			Reporter.log(value + " is selected in  " + desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveToElement(By strobj) {
		try {
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(strobj);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, "Element is not focused in the webpage");
		}
	}

	public void getApplication(String url) {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			updateTestLog(true, "Application url opened in the browser");
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, "Application url: " + url + " is not opened in the browser");
		}
	}
	
	public void login(String userName, String password) {
		try {
			enterText(USERNAME, userName);
			enterText(PASSWORD, password);
			clickElement(LOGIN, "Login");
			//updateTestLog(true, "Able to login into the Application");
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, "Not able to login into the Application by the Email Or MobileNo: " +USERNAME);
		}
	}

	public boolean IsObjectPresent(By strobj) {
		try {
			List<WebElement> element = driver.findElements(strobj);
			if (element.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			updateTestLog(true, "Object is not present in the WebPage: " + strobj);
			return false;
		}
	}

	public void takeScreenshot(String fileName) {
		File filePath = new File(System.getProperty("user.dir") + "/src/main/resources/screenshots");

		if (!filePath.exists()) {
			filePath.mkdir();
		}

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(filePath + "/" + fileName + ".png"));
			Reporter.log(fileName + " Screenshot placed at " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getTextValues(By strobj) {
		List<String> values = new ArrayList<String>();
		try {
			List<WebElement> list = driver.findElements(strobj);
			for (WebElement element : list) {
				String value = element.getText().trim();
				if (value.equals(""))
					value = element.getAttribute("value");
				values.add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			updateTestLog(false, "List Value is not present in the WebPage: " + strobj);
		}
		return values;
	}

	public void waitForAjax(int timeoutInSeconds) {
		System.out.println("Checking active ajax calls by calling jquery.active");
		try {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
				for (int i = 0; i < timeoutInSeconds; i++) {
					Object numberOfAjaxConnections = jsDriver.executeScript("return window.jQuery != undefined && jQuery.active === 0");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						System.out.println("Number of active jquery ajax calls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
				System.out.println("Web driver: " + driver + " cannot execute javascript");
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public void waitForObject(int time, By strobj) {
		try {
			System.out.println("Wait for Object Started");
			WebDriverWait currWait = new WebDriverWait(driver, time);
			currWait.until(ExpectedConditions.invisibilityOfElementLocated(strobj));
			System.out.println("Wait for Object completed");
		} catch (Exception e) {
			System.out.println("Wait for Object not completed");
			e.getLocalizedMessage();
		}
	}

	public void updateTestLog(boolean bVal, String desc) {
		Reporter.log(desc);
		if (!bVal) {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MMM-dd HH-mm-ss");// dd/MM/yyyy
			Date now = new Date();
			String fileName = sdfDate.format(now);
			takeScreenshot(fileName);
		}
		Assert.assertTrue(bVal, desc);
	}

	public void waitForWindowAndSwitch(String title) {
		boolean exit = true;
		boolean found = false;
		String windowHandleOld = driver.getWindowHandle();
		int counter = 0;
		try {
			ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			for (String windowHandle : newTab) {
				try {
					driver.switchTo().window(windowHandle);
					String newWindowTitle = driver.getTitle();
					if (newWindowTitle.contains(title)) {
						found = true;
						break;
					} else
						continue;
				} catch (NoSuchWindowException se) {

					if (counter < 30) {
						hardDelay(1000);
					} else {
						exit = false;
					}
					counter++;
				}
			}

		} catch (Exception se) {

		}
		if (!found) {
			driver.switchTo().window(windowHandleOld);
		}
	}
	public void hardDelay(int waitTime)
	{
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This function used to open the property file(environment.properties) to access the
	 * properties mentioned in it. throws Exception
	 */
	public void openPropFile() throws IOException {
		String propertiesFileName = System.getProperty("user.dir") + "//src//main//java//environment.properties";
		prop = new Properties();
		fin = new FileInputStream(propertiesFileName);
		prop.load(fin);

	}
	
}