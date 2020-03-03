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
import java.io.FileWriter as FileWriter
import java.io.BufferedWriter as BufferedWriter
import java.io.File as File
import java.util.Date as Date


String[] results = var_files_to_count

String[] resultsCount = new String[results.size()]

int totalResultsCount = 0

String resultsString = ""

File resultsFile = new File("Data Files/Results_"+var_report_type+"/Results.html")

BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFile, true))

resultsString +='<html> <head> <title> '+var_report_type+' Report </title> <style type="text/css"> .test-result-table { border: 1px solid black; width: 800px; } .test-result-table-header-cell { border-bottom: 1px solid black; background-color: silver; } .test-result-step-command-cell { border-bottom: 1px solid gray; } .test-result-step-description-cell { border-bottom: 1px solid gray; } .test-result-step-result-cell-ok { border-bottom: 1px solid gray; background-color: green; } .test-result-step-result-cell-failure { border-bottom: 1px solid gray; background-color: red; } .test-result-step-result-cell-total { border-bottom: 1px solid gray; background-color: white; } .test-result-describe-cell { background-color: tan; font-style: italic; } .test-cast-status-box-ok { border: 1px solid black; float: left; margin-right: 10px; width: 45px; height: 25px; background-color: green; } </style> </head> <body> <h1 class="test-results-header"> &lt;Client Name&gt; Test Report </h1>'


resultsString +='<h2>'+var_report_type+'</h2>'

Date date = new Date()

formattedDate = date.format("dd/MM/yyyy HH:mm:ss")

resultsString +='<h4>Report generated: '+ formattedDate+'</h4>'

resultsString +='<table class="test-result-table" cellspacing="0"> <thead> <tr> <td class="test-result-table-header-cell"> Test Case </td> <td class="test-result-table-header-cell"> Result </td> </tr> </thead> <tbody>'

for (int i = 0 ; i < results.size(); i++) {
	File file = new File("Data Files/Results_"+var_report_type+"/"+results[i]+".txt")
	
	BufferedReader reader = new BufferedReader(new FileReader(file))
	String s;
	int linecount = 0
	resultsString +=' <tr class="test-result-step-row test-result-comment-row"> <td class="test-result-describe-cell" colspan="3">'+results[i]+'</td> </tr>'
	while((s=reader.readLine())!=null)    
	{
	   if(i==0){
		   resultsString +=' <tr class="test-result-step-row test-result-step-row-altone"> <td class="test-result-step-command-cell">'+s+'</td> <td class="test-result-step-result-cell-ok"> Passed </td> </tr>'
	   }
	   else{
		   resultsString +=' <tr class="test-result-step-row test-result-step-row-alttwo"> <td class="test-result-step-command-cell">'+s+'</td> <td class="test-result-step-result-cell-failure"> Failed </td> </tr>'
	   }
	   linecount++  
	}
	resultsCount[i] = linecount
	totalResultsCount += linecount
	resultsString +=' <tr class="test-result-step-row test-result-step-row-alttwo"> <td class="test-result-step-command-cell"> <b>Total</b> </td> <td class="test-result-step-result-cell-total">'+linecount+'</td> </tr>'
	reader.close()
}

resultsString +='</tbody> </table> <br>'

resultsString +='<table class="test-result-table" cellspacing="0"> <thead> <tr> <td class="test-result-table-header-cell"> Result Type </td> <td class="test-result-table-header-cell"> Totals </td> <td class="test-result-table-header-cell"> Percentage </td> </tr> </thead> <tbody>'

for (int i = 0 ; i < resultsCount.size(); i++) {
	
	int count = Integer.parseInt(resultsCount[i])
	
	int percentage = (count*100)/totalResultsCount
	
	resultsString +=' <tr class="test-result-step-row test-result-step-row-alttwo"> <td class="test-result-step-command-cell"> '+results[i]+' </td> <td class="test-result-step-command-cell"> '+resultsCount[i]+'/'+totalResultsCount.toString()+' </td> <td class="test-result-step-result-cell-total">'+percentage.toString()+'%</td> </tr>'


}

if(results.size()>2){
	
	int totalFails = (totalResultsCount - Integer.parseInt(resultsCount[0]) )
	
	int failPercentage = (totalFails*100)/totalResultsCount
	
	resultsString +=' <tr class="test-result-step-row test-result-step-row-alttwo"> <td class="test-result-step-command-cell"><i> Total Failed Cases </i> </td> <td class="test-result-step-command-cell"> '+totalFails+'/'+totalResultsCount.toString()+' </td> <td class="test-result-step-result-cell-total">'+failPercentage.toString()+'%</td> </tr>'
}



resultsString +='</tbody> </table></body></html>'

writer.append(resultsString)

writer.close()