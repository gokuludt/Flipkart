package pages.pageFunctions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import commons.ReusableLibrary;
import framework.BrowserTypes;
import framework.DriverFactory;
import framework.ExcelFactory;
import pages.pageElements.Master;

/**
 * @author Gokul This class is used as main page functionality
 *
 */
public class MasterPage extends ReusableLibrary implements Master {
	DriverFactory driverProperty = new DriverFactory();
	ExcelFactory excelSheet = new ExcelFactory();
	String sheetName = driverProperty.getProperty("TestCaseData");
	String url, userName, password, productName;

	public MasterPage(WebDriver driver) {
		super(driver);
	}

	public void initializeExcel(String sheetName) {
		excelSheet.loadExcelsheet(sheetName);

	}

	public void launchApplication() {
		url = driverProperty.getProperty("ApplicationUrl");
		getApplication(url);
		if (IsObjectPresent(LOGO)) {
			updateTestLog(true, "Home Page Loaded Successfully");
		}
	}

	public void loginIntoApplication() {
		userName = driverProperty.getProperty("EmailOrMobileNo");
		password = driverProperty.getProperty("Password");

		login(userName, password);
		if (IsObjectPresent(LOGIN_ERROR)) {
			updateTestLog(false, "You are not registered with us. Please sign up.");
		}
		if (IsObjectPresent(LOGO)) {
			updateTestLog(true, "Able to login into the Application");
		}
	}

	public void searchByProductName(String testId, String product) {
		initializeExcel("TestCaseData");
		productName = excelSheet.getData(testId, sheetName, product);
		if (IsObjectPresent(LOGIN_CLOSE)) {
			clickElement(LOGIN_CLOSE, "Login Close Button");
		}
		if (IsObjectPresent(SEARCH_TEXT)) {
			clickElement(SEARCH_TEXT, "Search Button");
			enterText(SEARCH_TEXT, productName);
			waitForAjax(1);
			clickElement(SEARCH_BTN, "Search Button");
			waitForAjax(7);
			waitForObject(10, SEARCH_CONFIRM);
			if (IsObjectPresent(SEARCH_CONFIRM)) {
				String searchTxt = driver.findElement(SEARCH_CONFIRM).getText();
				this.productName = productName.toLowerCase();
				if (searchTxt.contains(productName)) {
					updateTestLog(true, "Product Search is Successfull");
				} else if (IsObjectPresent(SEARCH_RECONFIRM)) {
					String searchReTxt = driver.findElement(SEARCH_RECONFIRM).getText();
					if (searchReTxt.contains(productName)) {
						updateTestLog(true, "Product Search '" + productName + "' is Successfull");
					} else {
						updateTestLog(false, "Product Search '" + productName + "' is not Successfull");
					}
				}
			} else {
				updateTestLog(false, "Product Search '" + productName + "' is not Successfull");
			}
		} else {
			updateTestLog(false, "Product Search '" + productName + "' is not Successfull");
		}
	}

	public void sortProductResults() {
		clickElement(PRODUCT_SORT, "High to Low Sort");
		/*
		 * clickElement(PRODUCT_FILTER, "Availability Filter"); waitForObject(5,
		 * PRODUCT_FILTER_BYSTOCK); moveToElement(NEED_HELP);
		 * moveToElement(PRODUCT_FILTER_BYSTOCK); clickElement(PRODUCT_FILTER_BYSTOCK,
		 * "Availability Filter"); waitForAjax(1);
		 */

	}

	public String getProductResults() {
		String selectedProduct = null;
		waitForAjax(3);
		if (IsObjectPresent(PRODUCT_NAME)) {
			List<String> productName = getTextValues(PRODUCT_NAME);
			List<String> productPrice = getTextValues(PRODUCT_PRICE);
			int count = 0;
			for (String strProductName : productName) {
				Reporter.log("Prodct Name: \"" + strProductName + "\" Product Price: " + productPrice.get(count));
				System.out.println("Prodct Name: \"" + strProductName + "\" Product Price: " + productPrice.get(count));

				count++;
			}
			/*
			 * int priceValue = 0; int productPriceIdx = 0; int productIdx = -1;
			 * List<WebElement> priceList = driver.findElements(PRODUCT_PRICE); for
			 * (WebElement element : priceList) { productIdx++; String value =
			 * element.getText().trim(); value = value.replace("?", ""); value =
			 * value.replace("₹", ""); value = value.replace(",", ""); int priceAmount =
			 * Integer.parseInt(value);
			 * 
			 * if (priceValue > priceAmount) { productPriceIdx++; } priceValue =
			 * priceAmount; if (productPriceIdx == 3) { element.click(); selectedProduct =
			 * productName.get(productIdx); updateTestLog(true,
			 * "Product view detail page is Successfully loaded"); break; } }
			 */
		} else {
			updateTestLog(false, "Product Result is not Successfull");
		}
		return selectedProduct;
	}

	public void buyProduct(String ProductName) {
		// waitForWindowAndSwitch(ProductName);
		waitForWindowAndSwitch("Online at Best Price On Flipkart");
		waitForObject(2, PRODUCT_BYNOW);
		clickElement(PRODUCT_BYNOW, "Buy Product");
		waitForObject(2, PRODUCT_CART_DETAILS);
		if (IsObjectPresent(PRODUCT_CART_DETAILS)) {
			clickElement(PRODUCT_DELIVER, "Deliver Here");
			waitForObject(2, ORDER_CONFIRMATION);
			clickElement(ORDER_CONTINUE, "Payment Continue");
			updateTestLog(true, "Product Cart detail page is Successfully loaded");
		} else {
			updateTestLog(false, "Product Cart detail page is not Successfully loaded");
		}

	}
}
