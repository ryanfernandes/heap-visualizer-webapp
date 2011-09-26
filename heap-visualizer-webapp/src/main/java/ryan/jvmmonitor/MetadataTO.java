/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ryan.jvmmonitor;

/**
 *
 * @author Ryan Fernandes
 */

public class MetadataTO {

	private String jvmData = null;

	private String osData = null;

	private String vmID = null;

	public String getJvmData() {
		return jvmData;
	}

	public void setJvmData(String jvmData) {
		this.jvmData = jvmData;
	}

	public String getOsData() {
		return osData;
	}

	public void setOsData(String osData) {
		this.osData = osData;
	}

	public String getVmID() {
		return vmID;
	}

	public void setVmID(String processID) {
		this.vmID = processID;
	}
}