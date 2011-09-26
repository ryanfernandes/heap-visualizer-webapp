/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryan.jvmmonitor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ryan.jvmmonitor.JSONRenderer;
import ryan.jvmmonitor.MetadataTO;

/**
 *
 * @author Ryan Fernandes
 */
public class Metadata extends HttpServlet {

	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter pw = response.getWriter();

		try {
			OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
			RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
			StringBuilder osData = new StringBuilder();
			osData.append(os.getArch()).append(" ").append(os.getName()).append(" ").append(os.getVersion()).append(" (").append(os.getAvailableProcessors()).append(" processors)");

			StringBuilder runtimeData = new StringBuilder();
			runtimeData.append(runtime.getVmVendor()).append("(").append(runtime.getVmName()).append(" ").append(runtime.getVmVersion()).append(")");

			MetadataTO dTO = new MetadataTO();
			dTO.setJvmData(runtimeData.toString());
			dTO.setOsData(osData.toString());
			dTO.setVmID(runtime.getName());

			response.setContentType("text/json");
			pw.write(JSONRenderer.render(dTO));
		} finally {
			pw.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/** 
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Returns a short description of the servlet.
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
