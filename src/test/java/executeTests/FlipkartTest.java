package executeTests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import framework.DriverFactory;
import pages.pageFunctions.MasterPage;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.annotations.Title;

/**
 * @author Gokul this Class is used to execute Test cases in www.flipkart.com
 *
 */
public class FlipkartTest {

	DriverFactory driverFact = new DriverFactory();
	String sheetName = driverFact.getProperty("TestDataSheetName");

	@DataProvider(name = "testcount", parallel = true)
	public Object[][] getTestCount() {
		return new Object[][] { new Object[] { "TC_001-1" }, new Object[] { "TC_001-2" } };
	}

	@TestCaseId("TC_001")
	@Title("To login with Flipkart and search by Product and Buy it")
	@Description("<p>Step 1: Navigate to Flipkart Web application </br> Step 2:Login with valid Credentials</br>"
			+ "Step 3: Search for the Product</p>" + "Step 4: Navigate to Product details page</p>"
			+ "Step 5: Buy the Product and confirm payment")
	@Test(dataProvider = "testcount")
	public void sortProduct(String testCaseName) {
		WebDriver driver;
		driverFact.initializeDriver();
		driver = driverFact.getDriver();
		try {
			MasterPage mp = new MasterPage(driver);
			mp.launchApplication();
			mp.searchByProductName(testCaseName, "Product");
			mp.sortProductResults();
		} finally {
			driver.quit();
		}
	}

	@TestCaseId("TC_002")
	@Title("To login with Flipkart and search by Product and Buy it")
	@Description("<p>Step 1: Navigate to Flipkart Web application </br> Step 2:Login with valid Credentials</br>"
			+ "Step 3: Search for the Product</p>" + "Step 4: Navigate to Product details page</p>"
			+ "Step 5: Buy the Product and confirm payment")
	@Test()
	public void getProductResults() {
		WebDriver driver;
		driverFact.initializeDriver();
		driver = driverFact.getDriver();
		try {
			MasterPage mp = new MasterPage(driver);
			mp.launchApplication();
			mp.searchByProductName("TC_002", "Product");
			mp.sortProductResults();
			mp.getProductResults();
		} finally {
			driver.quit();
		}
	}
}
