/*
 * Java Heap Visualizer
 *
 * Copyright 2011, Ryan Fernandes (https://code.google.com/u/@VBFTRFJWDxFDXgJ4/)
 * Licensed under The MIT License.
 * see license.txt
 *
 */
package ryan.jvmmonitor;

import ryan.jvmmonitor.MetadataTO;
import java.lang.management.MemoryUsage;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Ryan Fernandes
 */
public class JSONRenderer {

	private static ObjectMapper jsonMapper = new ObjectMapper();

	public static String render(MemoryDataPacket data) {
		try {
			return jsonMapper.writeValueAsString(data);
		} catch (Exception ex) {

			//jsonmapper failed
			ex.printStackTrace();
			return "";

		}
	}

	public static String render(Map<String, MemoryUsage> usage) {


		try {
			return jsonMapper.writeValueAsString(usage);
		} catch (Exception ex) {
			//jsonmapper failed
			ex.printStackTrace();
			return "";

		}
	}

	public static String render(MetadataTO dto) {


		try {
			return jsonMapper.writeValueAsString(dto);
		} catch (Exception ex) {
			//jsonmapper failed
			ex.printStackTrace();
			return "";

		}
	}

	public static void main(String... a) {
		System.out.println(JSONRenderer.render(JVMData.getAllMemoryUsage()));
	}
}
