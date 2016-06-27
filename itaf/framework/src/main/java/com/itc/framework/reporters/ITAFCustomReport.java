package com.itc.framework.reporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import com.itc.framework.helpers.ItafVersion;
import com.itc.framework.helpers.TestDetail;
import com.itc.framework.loggers.Log;

/**
 * This Customer reporter assumes that there is only one suite 
 * @author ITC Infotech
 *
 */
public class ITAFCustomReport implements IReporter {

	// Input streamer to hold the HTML content to be flushed to the file at the end
    protected PrintWriter m_writer;

    // List of Suites
    List<ISuite> m_suites = Lists.newArrayList();
    
    // Get results of all suites. Assumption for iTAF is that there is only one suite
    protected List<SuiteResult> m_suiteResults = Lists.newArrayList();

    // Reusable m_buffer
    private StringBuilder m_buffer = new StringBuilder();

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
            String outputDirectory) {
        try {
            m_writer = createWriter(outputDirectory);
        } catch (IOException e) {
            Log.error("Unable to create output file", e);
            return;
        }
        for (ISuite suite : suites) {
        	m_suites.add(suite);
            m_suiteResults.add(new SuiteResult(suite));
        }

        writeDocumentStart();
        writeHead();
        writeBody();
        writeDocumentEnd();

        m_writer.close();
    }

    protected PrintWriter createWriter(String outdir) throws IOException {
        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(new FileWriter(new File(
                outdir, "iTAF_Execution_Report.html"))));
    }

    protected void writeDocumentStart() {
        m_writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        m_writer.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    }

    protected void writeHead() {

		m_writer.println("<head>");
		m_writer.println("<title>iTAF Execution Report</title>");
		writeStylesheet();
		writeScript();		
		m_writer.println("</head>");
    }

    protected void writeStylesheet() {
		m_writer.println("<style type=\"text/css\">");
		m_writer.println("body {font-family: \"Lucida Sans Unicode\";}");
		m_writer.println("table {margin: 10px auto 30px auto;border: 1px; border-collapse: collapse; bordercolor: \"grey\";font-size: 12px;}");
		m_writer.println("td,th {border:1px solid #009;padding:.25em .5em}");
		m_writer.println(".head th {background-color: #000000; color: white; border:1px solid #009;padding:.25em .5em}");
		m_writer.println(".result th {vertical-align:bottom}");
		m_writer.println(".param th {padding-left:1em;padding-right:1em}");
		m_writer.println(".param td {padding-left:.5em;padding-right:2em}");
		m_writer.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		m_writer.println(".numi,.numi_attn,.numi_skip {text-align:right}");
		m_writer.println(".total td {font-weight:bold}");
		m_writer.println(".passedodd td {background-color: #C4FF89}");
		m_writer.println(".passedeven td {background-color: #C4FF89}");
		m_writer.println(".skippedodd td,.numi_skip {background-color: #D8D8CF}");
		m_writer.println(".skippedeven td,.stripe .numi_skip {background-color: #D8D8CF}");
		m_writer.println(".failedodd td,.numi_attn {background-color: #FF8566}");
		m_writer.println(".failedeven td,.stripe .numi_attn {background-color: #FF8566}");
		m_writer.println(".stacktrace {white-space:pre;font-family:monospace}");
		m_writer.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		m_writer.println("</style>");
    }
    
    protected void writeScript(){
    	m_writer.println("<script>");
		m_writer.println("hide=false;");
		m_writer.println("function toggleTable(x)");
		m_writer.println("{");
		m_writer.println("var lTable = document.getElementById(x);");
		m_writer.println("lTable.style.display = (lTable.style.display == \"table\") ? \"none\" : \"table\";");
		m_writer.println("}");
		m_writer.println("</script>");
    	
    }

    protected void writeBody() {
        m_writer.print("<body>");
        writeBodyHeader();
        writeRunParameters();
        writeSuiteSummary();
        writeScenarioSummary();
        writeScenarioDetails();
        m_writer.print("</div></div></div></body>");
    }

    private void writeBodyHeader() {
    	
    	m_writer.println("<div id=\"header\"");
    	m_writer.println("style=\"background-color: #000000; width: 100%; position: absolute; height: 90px; top: 0px; left: 0px\">");
    	m_writer.println("<h1 style=\"align: left; margin-left: 10px; color: white; font-size: 25px;\">iTAF Execution Report</h1>");
    	m_writer.println("<h3 style=\"align: right; margin-left: 10px; color: white; font-size: 10px;\"> " + ItafVersion.printVersion() + "</h3>");
    	m_writer.println("<div id=\"body\" style=\"width: 100%; position: relative; \">");
    	m_writer.println("<div id=\"run information\">");
    	m_writer.println("<h2 style=\"align: left; margin-left: 10px; color: black; font-size: 15px;\">Execution Start Time:" 
    					+ ITAFSuiteListener.getSuiteStartTime()  + "</h2>");
    	m_writer.println("</div>");
		
	}

	/**
     * Get all run parameter for a suite and print it
     */
    private void writeRunParameters() {
    	
    	m_writer.println("<div id=\"run details\">");

		for (ISuite suite : m_suites) {

			XmlSuite xmlSuite = suite.getXmlSuite();
			Map<String, String> allParameters = xmlSuite.getAllParameters();
			writeTableStart("OPTIONS", null);
			String Header = "";
			String row = "";
			int noOfColumns = 0;
			
			for (Map.Entry<String, String> e : allParameters.entrySet()) {

				/**
				 * Password should not be printed in Report/Log file for
				 * ProdSanity runs which runs everyday on Production env.
				 */
				if (!e.getKey().contains("password")) {
					Header += "<th>" + e.getKey() + "</th>";
					row += "<td>" + e.getValue() + "</td>";
					noOfColumns++;
				}
			}
			
			Header += "<th>Threads</th>";
			row += "<td>" + xmlSuite.getThreadCount() + "</td>";
			noOfColumns++;

			m_writer.print("<tr><th class=\"head\" colspan=\"" + noOfColumns + "\">Run Parameters</th></tr>");
			m_writer.print("<tr>");
			m_writer.println(Header);
			m_writer.println("</tr>");
			m_writer.print("<tr bgcolor=\"#F0F8FF\">");
			m_writer.println(row);
			m_writer.println("</tr>");

		}
		m_writer.println("</table>");
		m_writer.println("<p></p>");
		
	}

	protected void writeDocumentEnd() {
        m_writer.print("</html>");
    }

    protected void writeSuiteSummary() {
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();

        int totalPassedTests = 0;
        int totalSkippedTests = 0;
        int totalFailedTests = 0;

        int testIndex = 0;
        for (SuiteResult suiteResult : m_suiteResults) {
            this.writeTableStart(Utils.escapeHtml(suiteResult.getSuiteName()), null);
            m_writer.print("<tr><th class=\"head\" colspan=\"7\">");
            m_writer.print(Utils.escapeHtml(suiteResult.getSuiteName()));
            m_writer.print("</th></tr>");
            m_writer.print("<tr>");
            m_writer.print("<th>Test</th>");
            m_writer.print("<th># Passed</th>");
            m_writer.print("<th># Skipped</th>");
            m_writer.print("<th># Failed</th>");
            m_writer.print("<th>Time (ms)</th>");
            m_writer.print("<th>Included Groups</th>");
            m_writer.print("<th>Excluded Groups</th>");
            m_writer.print("</tr>");
            
            for (TestResult testResult : suiteResult.getTestResults()) {
                int passedTests = testResult.getPassedTestCount();
                int skippedTests = testResult.getSkippedTestCount();
                int failedTests = testResult.getFailedTestCount();
                long duration = testResult.getDuration();

                m_writer.print("<tr");
                if ((testIndex % 2) == 1) {
                    m_writer.print(" class=\"stripe\"");
                }
                m_writer.print(">");

                m_buffer.setLength(0);
                writeTableData(m_buffer.append("<a href=\"#t").append(testIndex)
                        .append("\">")
                        .append(Utils.escapeHtml(testResult.getTestName()))
                        .append("</a>").toString());
                writeTableData(integerFormat.format(passedTests), "numi");
                writeTableData(integerFormat.format(skippedTests),
                        (skippedTests > 0 ? "numi_skip" : "numi"));
                writeTableData(integerFormat.format(failedTests),
                        (failedTests > 0 ? "numi_attn" : "numi"));                
                writeTableData(formatDuration( duration), "numi");
                writeTableData(testResult.getIncludedGroups());
                writeTableData(testResult.getExcludedGroups());

                m_writer.print("</tr>");

                totalPassedTests += passedTests;
                totalSkippedTests += skippedTests;
                totalFailedTests += failedTests;

                testIndex++;
            }
        }

        // Print totals if there was more than one test
        if (testIndex > 1) {
            m_writer.print("<tr>");
            m_writer.print("<th>Total</th>");
            writeTableHeader(integerFormat.format(totalPassedTests), "numi");
            writeTableHeader(integerFormat.format(totalSkippedTests),
                    (totalSkippedTests > 0 ? "numi_skip" : "numi"));
            writeTableHeader(integerFormat.format(totalFailedTests),
                    (totalFailedTests > 0 ? "numi_attn" : "numi"));
            writeTableHeader(formatDuration( 
            		ITAFSuiteListener.getSuiteEndTime().getTime() - ITAFSuiteListener.getSuiteStartTime().getTime()),
					"numi");
            m_writer.print("<th colspan=\"2\"></th>");
            m_writer.print("</tr>");
        }

        m_writer.print("</table>");
    }

    /**
     * Writes a summary of all the test scenarios.
     */
    protected void writeScenarioSummary() {
        
        this.writeTableStart("Method Summary", null);
        m_writer.print("<thead>");
        m_writer.print("<tr><th id=\"summary\" class=\"head\" colspan=\"6\"> Run Details</th></tr>");
        m_writer.print("<tr>");
        m_writer.println("<th>Test Case ID</th>");
        m_writer.println("<th>Test Description</th>");
        m_writer.println("<th>Test Method</th>");
        m_writer.println("<th>Start</th>");
        m_writer.println("<th>Time<br>elapsed</th>");
        m_writer.println("<th>Detail</th></tr>");

        int testIndex = 0;
        int scenarioIndex = 0;
        for (SuiteResult suiteResult : m_suiteResults) {
        	
            for (TestResult testResult : suiteResult.getTestResults()) {
                m_writer.print("<tbody id=\"t");
                m_writer.print(testIndex);
                m_writer.print("\">");

                String testName = Utils.escapeHtml(suiteResult.getSuiteName()) + "::" + Utils.escapeHtml(testResult.getTestName());

                scenarioIndex += writeScenarioSummary(suiteResult.getSuiteName(),
                		testName + " &#8212; failed (configuration methods)",
                        testResult.getFailedConfigurationResults(), "failed",
                        scenarioIndex ,
                        suiteResult.getLogAndReportPath());
                scenarioIndex += writeScenarioSummary(suiteResult.getSuiteName(),
                		testName + " &#8212; failed", testResult.getFailedTestResults(),
                        "failed", 
                        scenarioIndex ,
                        suiteResult.getLogAndReportPath());
                scenarioIndex += writeScenarioSummary(suiteResult.getSuiteName(),
                		testName + " &#8212; skipped (configuration methods)",
                        testResult.getSkippedConfigurationResults(), "skipped",
                        scenarioIndex ,
                        suiteResult.getLogAndReportPath());
                scenarioIndex += writeScenarioSummary(suiteResult.getSuiteName(),
                		testName + " &#8212; skipped",
                        testResult.getSkippedTestResults(), "skipped",
                        scenarioIndex ,
                        suiteResult.getLogAndReportPath());
                scenarioIndex += writeScenarioSummary(suiteResult.getSuiteName(),
                		testName + " &#8212; passed", testResult.getPassedTestResults(),
                        "passed", 
                        scenarioIndex ,
                        suiteResult.getLogAndReportPath());

                m_writer.print("</tbody>");

                testIndex++;
            }
        }

        m_writer.print("</table>");
    }

    /**
     * Writes the scenario summary for the results of a given state for a single
     * test.
     */
    private int writeScenarioSummary(String suiteName , String description,
            List<ClassResult> classResults, String cssClassPrefix,
            int startingScenarioIndex, String dirPathToLogs) {
        int scenarioCount = 0;
        if (!classResults.isEmpty()) {
            m_writer.print("<tr><th colspan=\"6\">");
            m_writer.print(description);
            m_writer.print("</th></tr>");

            int scenarioIndex = startingScenarioIndex;
            int classIndex = 0;
            for (ClassResult classResult : classResults) {
                String cssClass = cssClassPrefix
                        + ((classIndex % 2) == 0 ? "even" : "odd");

                m_buffer.setLength(0);

                for (MethodResult methodResult : classResult.getMethodResults()) {
                    List<ITestResult> results = methodResult.getResults();
                    int resultsCount = results.size();
                    assert resultsCount > 0;

                    ITestResult firstResult = results.iterator().next();
                    String methodName = Utils.escapeHtml(firstResult
                            .getMethod().getMethodName());
                    long start = firstResult.getStartMillis();
                    long duration = firstResult.getEndMillis() - start;

                    m_buffer.append("<tr class=\"")
                    		.append(cssClass)
                    		.append("\">");
                    
                    TestDetail testMthdDetail = null;
        			String testCaseID = "NA";
        			String testDescription = "NA";
        			
                    testMthdDetail = firstResult.getMethod()
    						.getConstructorOrMethod().getMethod()
    						.getAnnotation(TestDetail.class);
    				
    				if (  null!= testMthdDetail && ( 
    						null != testMthdDetail.testCaseID()
    						|| !testMthdDetail.testCaseID().isEmpty() ))
    					testCaseID = testMthdDetail.testCaseID();
    				
    				if ( null!= testMthdDetail && ( 
    						null != testMthdDetail.testCaseName()
    						|| !testMthdDetail.testCaseName().isEmpty() ))
    					testDescription = testMthdDetail.testCaseName();

    				// Format the Test method execution start time
    				SimpleDateFormat dateFormatter = new SimpleDateFormat(" MMM d 'at' hh:mm a");
    				Date date = new Date(start);
    				String formattedDate = dateFormatter.format(date);
    				
    				// Set the information for details column -> Screenshot path and log path
    				String pathToScreenShot = "NOT AVAILABLE";
    				String pathToHTMLSourceFile = "NOT AVAILABLE";
    				String pathToLogFile = "NOT AVAILABLE";
    				String detailTableID = null;
    				
    				if( null != testMthdDetail) {
    					pathToScreenShot = suiteName + "/"
    						+ firstResult.getMethod().getMethodName() + "_"
    						+ firstResult.getMethod().getXmlTest().getParameter("customer") + ".jpeg";
    					
    					pathToHTMLSourceFile = suiteName + "/"
        						+ firstResult.getMethod().getMethodName() + "_"
        						+ firstResult.getMethod().getXmlTest().getParameter("customer") + ".html";
    				
    					pathToLogFile = dirPathToLogs + "/"
    							+ firstResult.getMethod().getMethodName() + "_"
    							+ firstResult.getMethod().getXmlTest().getParameter("customer") + ".log"; 	
    					
    					detailTableID = firstResult.getName() + "_" + firstResult.getMethod().getXmlTest().getParameter("customer");
    				}

    				
                    // Write the timing information with the first scenario per
                    // method
                    m_buffer.append("<td>").append(testCaseID).append("</td>")
                    		.append("<td>").append(testDescription).append("</td>")
                    		.append("<td><a href=\"#m").append(scenarioIndex).append("\">").append(methodName).append("</a></td>")
                    		.append("<td rowspan=\"").append(resultsCount).append("\">").append(formattedDate).append("</td>")
                    		.append("<td rowspan=\"").append(resultsCount).append("\">")
                            .append(formatDuration(duration)).append("</td>") ;
                    
                    if( null == testMthdDetail) 
                    	m_buffer.append("<td><a>NOT AVAILABLE</a></td></tr>");
					else
						m_buffer.append("<td><a id=\"loginLink\" onclick=toggleTable(\""
						+ detailTableID 
						+ "\") href=\"#" + firstResult.getMethod().getMethodName()
						+ "\">+</a>" 
						+ "<table id=\"" + detailTableID
						+ "\"align=\"center\" style=\"display:none\">"
						+ "<tr align=\"center\">" 
						+ "<th>Screen Shot</th> <th>Detail Log</th> <th>HTML Source</th>" 
						+ "</tr>" 
						+ "<tr align=\"center\">"
						+ "<td><a href=\"" 
						+ pathToScreenShot
						+ "\"><img width=\"150\" height=\"100\" src=\""
						+ pathToScreenShot 
						+ "\"></a></td>" 
						+ "<td><a href=\""
						+ pathToLogFile 
						+ "\">Detail log</a></td>"
						+ "<td><a href=\"" 
						+ pathToHTMLSourceFile
						+ "\">HTML Source</a></td>" + "</tr>"
						+ "</table>" + "</td>" + "</tr>")
                            .append("</tr>");
                    
                    scenarioIndex++;

                    // Write the remaining scenarios for the method
                    for (int i = 1; i < resultsCount; i++) {
                        m_buffer.append("<tr class=\"").append(cssClass)
                                .append("\">").append("<td><a href=\"#m")
                                .append(scenarioIndex).append("\">")
                                .append(methodName).append("</a></td></tr>");
                        scenarioIndex++;
                    }

                }

                // Write the test results for the class
                m_writer.print(m_buffer);

                classIndex++;
            }
            scenarioCount = scenarioIndex - startingScenarioIndex;
        }
        return scenarioCount;
    }

    /**
     * Writes the details for all test scenarios.
     */
    protected void writeScenarioDetails() {
        int scenarioIndex = 0;
        for (SuiteResult suiteResult : m_suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                m_writer.print("<h3>");
                m_writer.print("TEST : " + Utils.escapeHtml(testResult.getTestName()) +
                		 "  Test Method Run Details");
                m_writer.print("</h3>");

                scenarioIndex += writeScenarioDetails(
                        testResult.getFailedConfigurationResults(),
                        scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getFailedTestResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getSkippedConfigurationResults(),
                        scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getSkippedTestResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(
                        testResult.getPassedTestResults(), scenarioIndex);
            }
        }
    }

    /**
     * Writes the scenario details for the results of a given state for a single
     * test.
     */
    private int writeScenarioDetails(List<ClassResult> classResults,
            int startingScenarioIndex) {
        int scenarioIndex = startingScenarioIndex;
        for (ClassResult classResult : classResults) {
            String className = classResult.getClassName();
            for (MethodResult methodResult : classResult.getMethodResults()) {
                List<ITestResult> results = methodResult.getResults();
                assert !results.isEmpty();

                String label = Utils
                        .escapeHtml(className
                                + "#"
                                + results.iterator().next().getMethod()
                                        .getMethodName());
                for (ITestResult result : results) {
                    writeScenario(scenarioIndex, label, result);
                    scenarioIndex++;
                }
            }
        }

        return scenarioIndex - startingScenarioIndex;
    }

    /**
     * Writes the details for an individual test scenario.
     */
    private void writeScenario(int scenarioIndex, String label,
            ITestResult result) {
        m_writer.print("<h4 id=\"m");
        m_writer.print(scenarioIndex);
        m_writer.print("\">");
        m_writer.print(label);
        m_writer.print("</h4>");

        writeTableStart("Result Summarry", null);

        // Write test parameters (if any)
        Object[] parameters = result.getParameters();
        int parameterCount = (parameters == null ? 0 : parameters.length);
        if (parameterCount > 0) {
            m_writer.print("<tr class=\"param\">");
            for (int i = 1; i <= parameterCount; i++) {
                m_writer.print("<th>Parameter #");
                m_writer.print(i);
                m_writer.print("</th>");
            }
            m_writer.print("</tr><tr class=\"param stripe\">");
            for (Object parameter : parameters) {
                m_writer.print("<td>");
               m_writer.print(Utils.escapeHtml(Utils.toString(parameter, null)));
                m_writer.print("</td>");
            }
            m_writer.print("</tr>");
        }

        // Write reporter messages (if any)
        List<String> reporterMessages = Reporter.getOutput(result);
        if (!reporterMessages.isEmpty()) {
            m_writer.print("<tr><th");
            if (parameterCount > 1) {
                m_writer.print(" colspan=\"");
                m_writer.print(parameterCount);
                m_writer.print("\"");
            }
            m_writer.print(">Messages</th></tr>");

            m_writer.print("<tr><td");
            if (parameterCount > 1) {
                m_writer.print(" colspan=\"");
                m_writer.print(parameterCount);
                m_writer.print("\"");
            }
            m_writer.print(">");
            writeReporterMessages(reporterMessages);
            m_writer.print("</td></tr>");
        }

        // Write exception (if any)
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            m_writer.print("<tr><th");
            if (parameterCount > 1) {
                m_writer.print(" colspan=\"");
                m_writer.print(parameterCount);
                m_writer.print("\"");
            }
            m_writer.print(">");
            m_writer.print((result.getStatus() == ITestResult.SUCCESS ? "Expected Exception"
                    : "Exception"));
            m_writer.print("</th></tr>");

            m_writer.print("<tr><td");
            if (parameterCount > 1) {
                m_writer.print(" colspan=\"");
                m_writer.print(parameterCount);
                m_writer.print("\"");
            }
            m_writer.print(">");
            writeStackTrace(throwable);
            m_writer.print("</td></tr>");
        }

        m_writer.print("</table>");
        m_writer.print("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");
    }

    protected void writeReporterMessages(List<String> reporterMessages) {
        m_writer.print("<div class=\"messages\">");
        Iterator<String> iterator = reporterMessages.iterator();
        assert iterator.hasNext();
        m_writer.print(Utils.escapeHtml(iterator.next()));
        while (iterator.hasNext()) {
            m_writer.print("<br/>");
            m_writer.print(Utils.escapeHtml(iterator.next()));
        }
        m_writer.print("</div>");
    }

    protected void writeStackTrace(Throwable throwable) {
        m_writer.print("<div class=\"stacktrace\">");
        m_writer.print(Utils.stackTrace(throwable, true)[0]);
        m_writer.print("</div>");
    }

    /**
     * Writes the TABLE element with specified css and id
     * @param cssclass
     * 				the space-delimited CSS classes or null if there are no
     *            classes to apply
     * @param id
     */
    protected void writeTableStart(String cssclass, String id) {
    	m_writer.println("<table cellspacing=\"0\" cellpadding=\"0\""
				+ (cssclass != null ? " class=\"" + cssclass + "\""
						: " style=\"padding-bottom:2em\"")
				+ (id != null ? " id=\"" + id + "\"" : "") + ">");
	}
    
    /**
     * Writes a TH element with the specified contents and CSS class names.
     * 
     * @param html
     *            the HTML contents
     * @param cssClasses
     *            the space-delimited CSS classes or null if there are no
     *            classes to apply
     */
    protected void writeTableHeader(String html, String cssClasses) {
        writeTag("th", html, cssClasses);
    }

    /**
     * Writes a TD element with the specified contents.
     * 
     * @param html
     *            the HTML contents
     */
    protected void writeTableData(String html) {
        writeTableData(html, null);
    }

    /**
     * Writes a TD element with the specified contents and CSS class names.
     * 
     * @param html
     *            the HTML contents
     * @param cssClasses
     *            the space-delimited CSS classes or null if there are no
     *            classes to apply
     */
    protected void writeTableData(String html, String cssClasses) {
        writeTag("td", html, cssClasses);
    }

    /**
     * Writes an arbitrary HTML element with the specified contents and CSS
     * class names.
     * 
     * @param tag
     *            the tag name
     * @param html
     *            the HTML contents
     * @param cssClasses
     *            the space-delimited CSS classes or null if there are no
     *            classes to apply
     */
    protected void writeTag(String tag, String html, String cssClasses) {
        m_writer.print("<");
        m_writer.print(tag);
        if (cssClasses != null) {
            m_writer.print(" class=\"");
            m_writer.print(cssClasses);
            m_writer.print("\"");
        }
        m_writer.print(">");
        m_writer.print(html);
        m_writer.print("</");
        m_writer.print(tag);
        m_writer.print(">");
    }
    
    /**
     * Format the execution duration in more human readable format
     * @param executionDuration
     * 					Time it took for execution
     * @return
     */
	protected static String formatDuration(long executionDuration) {

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = executionDuration / daysInMilli;
		executionDuration = executionDuration % daysInMilli;

		long elapsedHours = executionDuration / hoursInMilli;
		executionDuration = executionDuration % hoursInMilli;

		long elapsedMinutes = executionDuration / minutesInMilli;
		executionDuration = executionDuration % minutesInMilli;

		long elapsedSeconds = executionDuration / secondsInMilli;

		
		String elapsedTimeString = (elapsedDays > 0 ? elapsedDays + " days, " : "" ) + 
				(elapsedHours > 0 ? elapsedHours + " hrs, " : "" ) +
	            (elapsedMinutes > 0 ? elapsedMinutes + " mins, " : "" ) +
	            (elapsedSeconds > 0 ? elapsedSeconds + " sec" : "0 sec")	;

		return elapsedTimeString;

	}

    /**
     * Groups {@link TestResult}s by suite.
     */
    protected static class SuiteResult {
        private final String suiteName;
        private final List<TestResult> testResults = Lists.newArrayList();
        private final String dirPathToLogs;

        public SuiteResult(ISuite suite) {
            suiteName = suite.getName();
            
            XmlSuite xmlSuite = suite.getXmlSuite();
    		Map<String, String> allParameters = xmlSuite.getAllParameters();
     		
    		if( null == allParameters.get("logAndReportFilePath") || 
    				allParameters.get("logAndReportFilePath").isEmpty()  )
    			dirPathToLogs = "../log";
    		else
    			dirPathToLogs = "../../log/" + allParameters.get("logAndReportFilePath");
    		
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                testResults.add(new TestResult(suiteResult.getTestContext()));
            }
        }

        public String getSuiteName() {
            return suiteName;
        }

        /**
         * @return the test results (possibly empty)
         */
        public List<TestResult> getTestResults() {
            return testResults;
        }
        
        /**
         * @return Return the the Directory path to Log and Reports for the suite
         */
        public String getLogAndReportPath(){
        	return dirPathToLogs;
        }
    }

    /**
     * Groups {@link ClassResult}s by test, type (configuration or test), and
     * status.
     */
    protected static class TestResult {
        /**
         * Orders test results by class name and then by method name (in
         * lexicographic order).
         */
        protected static final Comparator<ITestResult> RESULT_COMPARATOR = new Comparator<ITestResult>() {
            public int compare(ITestResult o1, ITestResult o2) {
                int result = o1.getTestClass().getName()
                        .compareTo(o2.getTestClass().getName());
                if (result == 0) {
                    result = o1.getMethod().getMethodName()
                            .compareTo(o2.getMethod().getMethodName());
                }
                return result;
            }
        };

        private final String testName;
        private final List<ClassResult> failedConfigurationResults;
        private final List<ClassResult> failedTestResults;
        private final List<ClassResult> skippedConfigurationResults;
        private final List<ClassResult> skippedTestResults;
        private final List<ClassResult> passedTestResults;
        private final int failedTestCount;
        private final int skippedTestCount;
        private final int passedTestCount;
        private final long duration;
        private final String includedGroups;
        private final String excludedGroups;

        public TestResult(ITestContext context) {
            testName = context.getName();

            Set<ITestResult> failedConfigurations = context
                    .getFailedConfigurations().getAllResults();
            Set<ITestResult> failedTests = context.getFailedTests()
                    .getAllResults();
            Set<ITestResult> skippedConfigurations = context
                    .getSkippedConfigurations().getAllResults();
            Set<ITestResult> skippedTests = context.getSkippedTests()
                    .getAllResults();
            Set<ITestResult> passedTests = context.getPassedTests()
                    .getAllResults();

            failedConfigurationResults = groupResults(failedConfigurations);
            failedTestResults = groupResults(failedTests);
            skippedConfigurationResults = groupResults(skippedConfigurations);
            skippedTestResults = groupResults(skippedTests);
            passedTestResults = groupResults(passedTests);

            failedTestCount = failedTests.size();
            skippedTestCount = skippedTests.size();
            passedTestCount = passedTests.size();

            duration = context.getEndDate().getTime()
                    - context.getStartDate().getTime();

            includedGroups = formatGroups(context.getIncludedGroups());
            excludedGroups = formatGroups(context.getExcludedGroups());
        }

        /**
         * Groups test results by method and then by class.
         */
        protected List<ClassResult> groupResults(Set<ITestResult> results) {
            List<ClassResult> classResults = Lists.newArrayList();
            if (!results.isEmpty()) {
                List<MethodResult> resultsPerClass = Lists.newArrayList();
                List<ITestResult> resultsPerMethod = Lists.newArrayList();

                List<ITestResult> resultsList = Lists.newArrayList(results);
                Collections.sort(resultsList, RESULT_COMPARATOR);
                Iterator<ITestResult> resultsIterator = resultsList.iterator();
                assert resultsIterator.hasNext();

                ITestResult result = resultsIterator.next();
                resultsPerMethod.add(result);

                String previousClassName = result.getTestClass().getName();
                String previousMethodName = result.getMethod().getMethodName();
                while (resultsIterator.hasNext()) {
                    result = resultsIterator.next();

                    String className = result.getTestClass().getName();
                    if (!previousClassName.equals(className)) {
                        // Different class implies different method
                        assert !resultsPerMethod.isEmpty();
                        resultsPerClass.add(new MethodResult(resultsPerMethod));
                        resultsPerMethod = Lists.newArrayList();

                        assert !resultsPerClass.isEmpty();
                        classResults.add(new ClassResult(previousClassName,
                                resultsPerClass));
                        resultsPerClass = Lists.newArrayList();

                        previousClassName = className;
                        previousMethodName = result.getMethod().getMethodName();
                    } else {
                        String methodName = result.getMethod().getMethodName();
                        if (!previousMethodName.equals(methodName)) {
                            assert !resultsPerMethod.isEmpty();
                            resultsPerClass.add(new MethodResult(resultsPerMethod));
                            resultsPerMethod = Lists.newArrayList();

                            previousMethodName = methodName;
                        }
                    }
                    resultsPerMethod.add(result);
                }
                assert !resultsPerMethod.isEmpty();
                resultsPerClass.add(new MethodResult(resultsPerMethod));
                assert !resultsPerClass.isEmpty();
                classResults.add(new ClassResult(previousClassName,
                        resultsPerClass));
            }
            return classResults;
        }

        public String getTestName() {
            return testName;
        }

        /**
         * @return the results for failed configurations (possibly empty)
         */
        public List<ClassResult> getFailedConfigurationResults() {
            return failedConfigurationResults;
        }

        /**
         * @return the results for failed tests (possibly empty)
         */
        public List<ClassResult> getFailedTestResults() {
            return failedTestResults;
        }

        /**
         * @return the results for skipped configurations (possibly empty)
         */
        public List<ClassResult> getSkippedConfigurationResults() {
            return skippedConfigurationResults;
        }

        /**
         * @return the results for skipped tests (possibly empty)
         */
        public List<ClassResult> getSkippedTestResults() {
            return skippedTestResults;
        }

        /**
         * @return the results for passed tests (possibly empty)
         */
        public List<ClassResult> getPassedTestResults() {
            return passedTestResults;
        }

        public int getFailedTestCount() {
            return failedTestCount;
        }

        public int getSkippedTestCount() {
            return skippedTestCount;
        }

        public int getPassedTestCount() {
            return passedTestCount;
        }

        public long getDuration() {
            return duration;
        }

        public String getIncludedGroups() {
            return includedGroups;
        }

        public String getExcludedGroups() {
            return excludedGroups;
        }

        /**
         * Formats an array of groups for display.
         */
        protected String formatGroups(String[] groups) {
            if (groups.length == 0) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            builder.append(groups[0]);
            for (int i = 1; i < groups.length; i++) {
                builder.append(", ").append(groups[i]);
            }
            return builder.toString();
        }
    }

    /**
     * Groups {@link MethodResult}s by class.
     */
    protected static class ClassResult {
        private final String className;
        private final List<MethodResult> methodResults;

        /**
         * @param className
         *            the class name
         * @param methodResults
         *            the non-null, non-empty {@link MethodResult} list
         */
        public ClassResult(String className, List<MethodResult> methodResults) {
            this.className = className;
            this.methodResults = methodResults;
        }

        public String getClassName() {
            return className;
        }

        /**
         * @return the non-null, non-empty {@link MethodResult} list
         */
        public List<MethodResult> getMethodResults() {
            return methodResults;
        }
    }

    /**
     * Groups test results by method.
     */
    protected static class MethodResult {
        private final List<ITestResult> results;

        /**
         * @param results
         *            the non-null, non-empty result list
         */
        public MethodResult(List<ITestResult> results) {
            this.results = results;
        }

        /**
         * @return the non-null, non-empty result list
         */
        public List<ITestResult> getResults() {
            return results;
        }
    }
}

