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
	@Title("To search by Product in Flipkart and sort")
	@Description("<p>Step 1: Navigate to Flipkart Web application </br>" + "Step 2: Search for the Product</p>"
			+ "Step 3: Sort to Product details page</p>")
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
	@Title("To search by Product in Flipkart and get the result")
	@Description("<p>Step 1: Navigate to Flipkart Web application </br>" + "Step 2: Search for the Product</p>"
			+ "Step 3: Navigate to Product details page</p>" + "Step 4: Get the Result set")
	@Test()
	public void getProductResults() {
		WebDriver driver;
		driverFact.initializeDriver();
		driver = driverFact.getDriver();
		try {
			MasterPage mp = new MasterPage(driver);
			mp.launchApplication();
			mp.searchByProductName("TC_002", "Product");
			mp.getProductResults();
		} finally {
			driver.quit();
		}
	}
}
