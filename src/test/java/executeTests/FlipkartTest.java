package executeTests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

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

	public WebDriver driver;
	DriverFactory driverFact = new DriverFactory();
	String sheetName = driverFact.getProperty("TestDataSheetName");

	@BeforeTest()
	public void beforeTest() {
		driverFact.initializeDriver();
		driver = driverFact.getDriver();
	}

	@TestCaseId("TC_001")
	@Title("To login with Flipkart and search by Product and Buy it")
	@Description("<p>Step 1: Navigate to Flipkart Web application </br> Step 2:Login with valid Credentials</br>"
			+ "Step 3: Search for the Product</p>"
			+ "Step 4: Navigate to Product details page</p>"
			+ "Step 5: Buy the Product and confirm payment")
	@Test()
	public void searchProduct(final ITestContext testContext) {
		MasterPage mp = new MasterPage(driver);
		mp.launchApplication();
		mp.loginIntoApplication();
		mp.searchByProductName("TC_001", "Product");
		mp.sortProductResults();
		String slectedProduct = mp.getProductResults();
		mp.buyProduct(slectedProduct);
	}

	@AfterTest
	public void afterTest() {
		driverFact.quitDriver();
	}

}
