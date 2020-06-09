package pages.pageElements;

import org.openqa.selenium.By;

/**
 * @author Gokul
 * This interface is used a object repository 
 *
 */
public interface Master {

	By LOGO = By.xpath("//img[@title='Flipkart']");
	By USERNAME = By.xpath("//label/span[.='Enter Email/Mobile number']//ancestor::div/input");
	By PASSWORD = By.xpath("//label/span[.='Enter Password']//ancestor::div/input");	
	By LOGIN = By.xpath("//button/span[.='Login']");

	By LOGIN_ERROR = By.xpath("//div[contains(.,'You are not registered with us. Please sign up.')]");

	By SEARCH_TEXT = By.xpath("//input[contains(@title,'Search for products')]");
	By SEARCH_BTN = By.xpath("//button[@class='vh79eN' and @type='submit']");
	By SEARCH_CONFIRM = By.xpath("//span[@class='_2yAnYN']");
	By SEARCH_RECONFIRM = By.xpath("//span[@class='_3Ui9zr']");

	By PRODUCT_SORT = By.xpath("//div[.='Price -- High to Low']");
	By PRODUCT_FILTER = By.xpath("//div[.='Availability']");
	By PRODUCT_FILTER_BYSTOCK = By.xpath("//div[.='Exclude Out of Stock']//input[@type='checkbox']/parent::label");
	By NEED_HELP = By.xpath("//span[.='Need help?']");

	By PRODUCT_NAME = By.xpath("//div[@class='_3wU53n']");
	By PRODUCT_PRICE = By.xpath("//div[contains(@class,'_1vC4OE')]");
	
	By PRODUCT_BYNOW = By.xpath("//button[contains(.,'BUY NOW')]");
	By PRODUCT_CART_DETAILS = By.xpath("//span[.='Price details']");
	By PRODUCT_DELIVER = By.xpath("//button[.='Deliver Here']");
	
	By ORDER_CONFIRMATION = By.xpath("//span[contains(.,'Order confirmation email will be sent to')]");
	By ORDER_CONTINUE = By.xpath("//span[@id='to-payment']/button[.='CONTINUE']");
	By ORDER_CREDIT = By.xpath("//input[@type='radio' and @id='CREDIT']");

	
}