package test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test class for accessing encrypted  email from gmail
 *
 * @author dawit.legesse
 */

@RunWith(JUnit4.class)
public class VirtruBrowserTest {

  protected static WebDriver driver;
  private WebElement element;

  @Before
  public void setUp() throws Exception {

    driver = new ChromeDriver();

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @Test
  public void openDecryptedEmail() throws Exception {

    driver.get("http://gmail.com/");
    Thread.sleep(5000);

    element = driver.findElement(By.xpath("//*[@id=\'identifierId\']"));
    element.clear();
    element.sendKeys("L.dawit524101@gmail.com");

    element = driver.findElement(By.xpath("//*[@id=\'identifierNext\']/content/span"));
    element.click();
    Thread.sleep(5000);

    element = driver.findElement(By.xpath("//*[@id=\'password\']/div[1]/div/div[1]/input"));
    element.clear();
    element.sendKeys("Newoffice1");

    element = driver.findElement(By.xpath("//*[@id=\'passwordNext\']/content/span"));
    element.click();
    //open mail from inbox
    Thread.sleep(5000);

    openMailFromTheGmailInbox();
    element = driver.findElement(By.xpath("//*[@id=\':kn\']/div[1]/div[1]/div[2]/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr/td/a"));
    element.click();
    Thread.sleep(10000);  // Let the user actually see something!

    element = driver.findElement(By.cssSelector("#content > div > div > div > div.login-page > a:nth-child(1) > div > span"));
    element.click();
    element = driver.findElement(By.cssSelector("#content > div > div > div > div.login-page > a:nth-child(1)"));
    element.click();
    Thread.sleep(5000);

    element = driver.findElement(By.xpath("//*[@id=\'tdf-body\']/div"));
    Assert.assertEquals("HELLO, WORLD!", element.getText());

  }

  @After
  public void cleanUp() {
    driver.manage().deleteAllCookies();
  }

  @AfterClass
  public static void tearDown() {
    driver.quit();
  }

  /**
   * Method that clicks on tne first "me" primary sender.
   */
  private void openMailFromTheGmailInbox() {
    //open a mail from the gmail inbox.
    List<WebElement> emails = driver.findElements(By.xpath("//*[@class='yW']/span"));

    element = emails
        .stream()
        .filter(element -> element.getText().equals("me"))
        .findFirst()
        .orElse(null);

    if (element == null) {
      System.out.println("Cannot find email.");
    }

    element.click();
  }
}
