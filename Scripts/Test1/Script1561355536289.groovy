import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.List

import org.junit.After
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

WebUI.openBrowser('test04.convoapps.com')

WebUI.setText(findTestObject('Convo App Login Page/input_Email'), 'admin@network1.com')

WebUI.setText(findTestObject('Convo App Login Page/input_Password'), 'Kitchen1')

WebUI.click(findTestObject('Convo App Login Page/button_Sign in'))

WebUI.click(findTestObject('Convo App Home Page/i_ProfileDropdown'))

WebUI.click(findTestObject('Convo App Home Page/Profile Dropdown Items/span_Notification_Preferences'))

Thread.sleep(3000)

WebDriver driver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(driver, 50)
try{
WebElement smartInput = driver.findElement(By.xpath("//div[@class='smartNoti']/input"))

if (smartInput.selected)
{
	driver.findElement(By.xpath("//div[@class='smartNoti']/label")).click()
	Thread.sleep(5000)
	assert (!smartInput.selected)
}

WebElement emailBtn = driver.findElement(By.xpath("//div[contains(@class,'chkEmail')]/button[contains(@class,'active')]"))

WebElement notBtn = driver.findElement(By.xpath("//div[contains(@class,'chkIPN')]/button[contains(@class,'active')]"))

if (emailBtn.text.equalsIgnoreCase("OFF"))
{
	emailBtn.click()
	Thread.sleep(1000)
	emailBtn = driver.findElement(By.xpath("//div[contains(@class,'chkEmail')]/button[contains(@class,'active')]"))
	assert (emailBtn.text.equalsIgnoreCase("ON"))
}

if (notBtn.text.equalsIgnoreCase("OFF"))
{
	notBtn.click()
	Thread.sleep(1000)
	notBtn = driver.findElement(By.xpath("//div[contains(@class,'chkIPN')]/button[contains(@class,'active')]"))
	assert (notBtn.text.equalsIgnoreCase("ON"))
}

int size = driver.findElements(By.xpath("//div[@class='cbkCustomizeSettings']/input[contains(@id,'E') or contains(@id,'N')]")).size()

for (int i=0; i<size; i++)
{
	WebElement input = driver.findElements(By.xpath("//div[@class='cbkCustomizeSettings']/input[contains(@id,'E') or contains(@id,'N')]")).get(i)	
	if (!input.selected)
	{
		WebElement label = driver.findElements(By.xpath("//div[@class='cbkCustomizeSettings']/input[contains(@id,'E') or contains(@id,'N')]/../label")).get(i)
		label.click()
		Thread.sleep(1000)
		assert (input.selected)
	}
}

driver.findElement(By.linkText("Feed and sharing")).click()

Thread.sleep(3000)

if (driver.findElements(By.xpath("//div[contains(@class,'tags')]//li")).size!=0)
{
	List <WebElement> items = driver.findElements(By.xpath("//div[contains(@class,'tags')]//a[contains(@class,'remove-button')]"))
	for (WebElement remove : items)
	{
		remove.click()
		Thread.sleep(1000)
	}
	assert (driver.findElements(By.xpath("//div[contains(@class,'tags')]//li")).size==0)
}
}
finally
{
	driver.quit();
}