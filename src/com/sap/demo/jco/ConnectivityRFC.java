package com.sap.demo.jco;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;

/**
 * Servlet implementation class ConnectivityRFC
 */
@WebServlet("/ConnectivityRFC")
public class ConnectivityRFC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectivityRFC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter responseWriter = response.getWriter();
        try
        {
            // access the RFC Destination "JCoDemoSystem"
            JCoDestination destination=JCoDestinationManager.getDestination("JE2");

            // make an invocation of STFC_CONNECTION in the backend;
            JCoRepository repo=destination.getRepository();
            JCoFunction stfcConnection=repo.getFunction("Z_TEST_DATE");

            JCoParameterList imports=stfcConnection.getImportParameterList();
            //imports.setValue("REQUTEXT", "SAP HANA Cloud connectivity runs with JCo");
            stfcConnection.execute(destination);
            JCoParameterList exports=stfcConnection.getExportParameterList();
            String echotext=exports.getString("DATE");
            //String resptext=exports.getString("RESPTEXT");
            response.addHeader("Content-type", "text/html");
            responseWriter.println("<html><body>");
            responseWriter.println("<h1>Executed Z_TEST_DATE in system JCoDemoSystem</h1>");
            responseWriter.println("<p>Export parameter DATE of STFC_CONNECTION:<br>");
            responseWriter.println(echotext);
            responseWriter.println("<p>Export parameter RESPTEXT of Z_TEST_DATE:<br>");
            //responseWriter.println(resptext);
            responseWriter.println("</body></html>");
        }
        catch (AbapException ae)
        {
            //just for completeness: As this function module does not have an exception
            //in its signature, this exception cannot occur. However,you should always
            //take care of AbapExceptions
        }
        catch (JCoException e)
        {
            response.addHeader("Content-type", "text/html");
            responseWriter.println("<html><body>");
            responseWriter.println("<h1>Exception occurred while executing STFC_CONNECTION in system JCoDemoSystem</h1>");
            responseWriter.println("<pre>");
            e.printStackTrace(responseWriter);
            responseWriter.println("</pre>");
            responseWriter.println("</body></html>");
        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
