# Data Driven Reporting Katalon
Sample Katalon Project with data driven reporting framework

## High Level Overview:

The way the script works is by having different text files for each possible outcome for a data driven test. The idea is you determine the outcome in the script and then output the name of the test case into the text file relating to the outcome. We then go back through the files, read and count the results contained within them and output those results into an HTML format.

The demo was written on a [sample automation site](http://automationpractice.com/index.php), automating the login in feature, passing through different values.

This goes through how to set a new instance of a data driven test that usees the reporting feature.

To use the tool follow the steps below:

1) Create a folder in the Data Files directory of the project entitled “Results_<Name of Test>”

2) Inside that folder, create a file called “Results.html” (you can just create a .txt file then rename it to .html), a .txt file called Passed and .txt files entitled any other result you may have. In many cases the only results could be Passed and Failed but in others you may want to have different reasons for failure to be flagged up and you would then need separate files for them.

3) Create a new test suite add the module ClearEntriesFromDataFiles in the Modules/DataDriven directory.

4) In the Variable binding section there will be 2 variables to set:
- var_files_to_clear should have Type set to Script Variable, add the value as List and then add one entry to the list for every .txt file you generated, ensuring you use the exact same names. The results file is cleared automatically and should not be entered in the list. We will use this list a few times, always keep this list in the same order with Passed as the first value.
- var_report_type should have Type set to Script Variable, add the value as String and it should be set as the same value you set <Name of Test> in step 1.

5) You then need to create a test step that opens the browser and navigates you to the area of the website that the test data needs to iterate through, e.g. navigate to a page with the required searchbox.

6) Create a data driven test step that will iterate through the given data set. You will also need to create the variable var_report_type again for this test step and set it to the same value as in Step 4. Open the step in script view and include the following import statements:

```
import java.io.FileWriter as FileWriter
import java.io.BufferedWriter as BufferedWriter
import java.io.File as File
```
7) Then declare an string array called results. This should contain the same values as you put in var_files_to_clear in step 4 in the same order. This can either be declared in the script or as a variable at test suite level.

8) Then declare an integer called outcome and set it to a value (Katalon doesn’t like unassigned variables). The value isn’t too important as it will be rest later.

9) You then need to write an appropriate test that will gather the information you need. You should then set the value of “outcome” to a different integer depending on the outcome. You should also make sure you don’t use any verify statements in the test case as it will fail the test case before we’re able to log the result.

10) We then use the value of outcome as an index a value in the string array ‘results’ we set at the top of the page of the file you want to add the value to in. We then use the following code to add the iterated value to the appropriate file then add a new line. In this example ‘var_email’ is the data driven value we’re passing in and we’re then adding that to the appropriate ‘results’ file. Ensure the correct value is passed though as that value will appear in the outputted report and they are submitted in a new line:

```
String fileName = results[outcome]

File file = new File('Data Files/Results_'+var_report_type+'/'+ fileName + '.txt')

BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))

writer.append(var_email)

writer.newLine()

writer.close()

WebUI.comment('Ensures Katalon logs the failing tests as failures')

```

11) Then add a step at the bottom that verifies the outcome its 0 which will be the index of the values that pass as Passed should always be the first value in the list. This step is not essential but it will trigger Katalon to fail that test step so you can use that as a quick metric of how a test step went.

12) Back in the test suite, add the step GenerateReport from the Modules/DataDriven folder. There are 2 variables to assign:
- var_files_to_count which should be set in exactly the same way with exactly the same values as the var_files_to_clear variable in Step 4. 
- var_report_type which, again, should be exactly the same as value in Step 4

13) When the test suite is run, the report should be generated as the Results.htlm file you created in the directory. It may take a minute to generate the report.

14) Only the values in the Passed file will come up as green and all the others will display as failed in red with the results grouped by the reason they failed and then the percentage breakdown at the bottom.






