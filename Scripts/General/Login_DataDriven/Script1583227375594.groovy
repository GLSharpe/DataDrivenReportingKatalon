import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

String[] results = var_files_to_write

int outcome = 0

WebUI.waitForElementVisible(findTestObject('LoginPage/txtEmail'), 5)

WebUI.setText(findTestObject('LoginPage/txtEmail'), var_email)

WebUI.setText(findTestObject('LoginPage/txtPassword'), var_password)

WebUI.click(findTestObject('LoginPage/btnSignIn'))

if (WebUI.waitForElementVisible(findTestObject('AccountHomePage/btnSignOut'), 5)) {
	if(WebUI.getText(findTestObject('AccountHomePage/txtUserName'))==var_username){
		outcome = 0
		
		WebUI.click(findTestObject('AccountHomePage/btnSignOut'))
		WebUI.waitForElementVisible(findTestObject('LoginPage/txtEmail'), 5)
	}
	else{
		outcome = 2
		
		WebUI.click(findTestObject('AccountHomePage/btnSignOut'))
		WebUI.waitForElementVisible(findTestObject('LoginPage/txtEmail'), 5)
	}
} else {
    outcome = 1
	
	WebUI.clearText(findTestObject('LoginPage/txtEmail'))
	WebUI.clearText(findTestObject('LoginPage/txtPassword'))
	
}

WebUI.comment('Writing To Correct File')

String fileName = results[outcome]

File file = new File('Data Files/Results_'+var_report_type+'/'+ fileName + '.txt')

BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))

writer.append(var_email)

writer.newLine()

writer.close()

WebUI.comment('Ensures Katalon logs the failing tests as failures')

WebUI.verifyEqual(0, outcome)

