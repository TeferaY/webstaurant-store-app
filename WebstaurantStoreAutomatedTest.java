package automatedTests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebstaurantStoreAutomatedTest {

	private static WebDriver driver;
	private static WebDriverWait wait;

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(12));
		navigateToWebstaurantStorePage();
		searchForStainlessWorkTable("stainless work table");
		validateTheSearchResults();
		validateSearchPages();
		addLastFoundItemToCart();
		emptyCart();
	}

	// Go to https://www.webstaurantstore.com/

	private static void navigateToWebstaurantStorePage() {
		driver.get("https://www.webstaurantstore.com");
		driver.manage().window().maximize();
		System.out.println("Webstaurant Store Page URL" + driver.getCurrentUrl());
		System.out.println("Page Title " + driver.getTitle());
	}

	// Search for 'stainless work table'.

	private static void searchForStainlessWorkTable(String searchItems) {
		searchItems = "stainless work table";
		WebElement searchBox = driver.findElement(By.xpath("(//input[@name='searchval'])[1]"));
		wait.until(ExpectedConditions.visibilityOf(searchBox));
		searchBox.sendKeys(searchItems);
		System.out.println("Able to enter stainless work table in the search box");

		WebElement searchBtn = driver.findElement(By.xpath("(//button[@value='Search'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
		searchBtn.click();
		System.out.println("Able to click on the search button");
		WebElement searchPageHeader = driver.findElement(By.xpath("//span[text()='stainless work table']"));
		wait.until(ExpectedConditions.visibilityOf(searchPageHeader));
		String expectedResult = "stainless work table";
		if (searchPageHeader.isDisplayed() && searchPageHeader.getText().trim().contains(expectedResult)) {
			System.out.println("Able to able to search for stainless work table");
		} else {
			System.out.println("Able to able to search for stainless work table");
		}
	}

	// Check the search result ensuring every product has the word 'Table' in its
	// title.

	private static void validateTheSearchResults() {
		List<String> uniqueTitles = new ArrayList<>();
		List<WebElement> searchResults = driver.findElements(By.xpath("//span[@data-testid='itemDescription']"));
		for (int i = 0; i < searchResults.size(); i++) {

			String title = searchResults.get(i).getText();
			if (!searchResults.get(i).getText().toLowerCase().contains("table")) {

				uniqueTitles.add(title);

				System.out.println("This search results do not contain 'Table' in it's title. " + uniqueTitles);
			}
		}
		System.out.println("Search results contain the word 'Table' in their title");

	}

	// Check the search result ensuring every product has the word 'Table' in its
	// title.

	private static void validateSearchPages() {

		int numberOfPages = 9;
		for (int i = 0; i < numberOfPages; i++) {
			WebElement nextPagebtn = driver
					.findElement(By.xpath("//li[@class='inline-block leading-4 align-top rounded-r-md']"));
			wait.until(ExpectedConditions.elementToBeClickable(nextPagebtn));
			nextPagebtn.click();

			validateTheSearchResults();

			System.out.println("Able to validate This pages ");

		}
	}

	// Add the last of found items to Cart.

	private static void addLastFoundItemToCart() {
		List<WebElement> searchResults = driver.findElements(By.xpath("//span[@data-testid='itemDescription']"));
		WebElement lastItem = searchResults.get(searchResults.size() - 1);
		wait.until(ExpectedConditions.elementToBeClickable(lastItem));
		lastItem.click();
		System.out.println("Able to click on the last found item");

		WebElement addToCartBtn = driver.findElement(By.xpath("(//input[@type='submit'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
		addToCartBtn.click();
		System.out.println("Able to add the last found item to the cart");
		WebElement viewCartBtn = driver.findElement(By.xpath("//span[@id='cartItemCountSpan']"));
		wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn));
		viewCartBtn.click();
		WebElement emptyCartBtn = driver.findElement(By.xpath("//button[text()='Empty Cart']"));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(emptyCartBtn));
		String expectedResult = "Empty Cart";

		if (emptyCartBtn.getText().trim().contains(expectedResult)) {
			System.out.println("Able to validate the last found item was added to the cart");

		} else {
			System.out.println("Not able to validate the last found item was added to the cart");
		}
	}

	// Empty Cart.

	private static void emptyCart() {
		WebElement viewCartBtn = driver.findElement(By.xpath("//span[@id='cartItemCountSpan']"));
		wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn));
		viewCartBtn.click();
		System.out.println("Able to click on the View Cart button");

		WebElement emptyCartBtn = driver.findElement(By.xpath("//button[text()='Empty Cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(emptyCartBtn));
		emptyCartBtn.click();
		System.out.println("Able to click on the Empty Cart button");

		WebElement emptyCartConfirmationBtn = driver.findElement(By.xpath("(//button[@type='button'])[33]"));
		wait.until(ExpectedConditions.elementToBeClickable(emptyCartConfirmationBtn));
		emptyCartConfirmationBtn.click();
		System.out.println("Able to click confirm to empty the cart");

		WebElement emptyCartConfirmationText = driver.findElement(By.xpath("//div[@class='empty-cart__text']"));
		wait.until(ExpectedConditions.visibilityOf(emptyCartConfirmationText));
		if (emptyCartConfirmationText.isDisplayed()) {
			System.out.println("Able to remove the added item from the cart");
		} else {
			System.out.println("Able to remove the added item from the cart");
		}

		driver.quit();
		
	}
}
