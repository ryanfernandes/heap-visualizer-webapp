/*
 * Java Heap Visualizer
 *
 * Copyright 2011, Ryan Fernandes (https://code.google.com/u/@VBFTRFJWDxFDXgJ4/)
 * Licensed under The MIT License.
 * see license.txt
 *
 */
package ryan.jvmmonitor;

import java.lang.management.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/**
 *
 * @author Ryan Fernandes
 */
public class JVMData {
	

	public static  MemoryUsage getHeapUsage() {
		return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
	}

	public static MemoryUsage getNonHeapUsage() {
		return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
	}

	public static Map<String,MemoryUsage> getMemoryPoolUsage() {
		Map<String,MemoryUsage> usage = new HashMap<String,MemoryUsage>();

		Iterator iter = ManagementFactory.getMemoryPoolMXBeans().iterator();

		while (iter.hasNext()) {
			MemoryPoolMXBean item = (MemoryPoolMXBean) iter.next();
			usage.put(((item.getType()==MemoryType.HEAP)?"H":"N")+item.getName(), item.getUsage());
		}
		
		return usage;
	}
	
	public static MemoryDataPacket getAllMemoryUsage() {
		MemoryDataPacket datapacket = new MemoryDataPacket();
		datapacket.setHeap(new MemoryComponent("HEAP",MemoryType.HEAP,getHeapUsage()));
		datapacket.setNonHeap(new MemoryComponent("NON-HEAP",MemoryType.NON_HEAP,getNonHeapUsage()));
		
		for (Entry<String,MemoryUsage> e : getMemoryPoolUsage().entrySet()) {
			String key = e.getKey();
			if (key.startsWith("H")) {
				
				datapacket.addHeapComponent( new MemoryComponent(	key.substring(1),
																	MemoryType.HEAP,
																	e.getValue()) );	
			}
			else {
				datapacket.addNonHeapComponent( new MemoryComponent(key.substring(1),
																	MemoryType.NON_HEAP,
																	e.getValue()) );	
				
			}
		}
		
		return datapacket;
	}
}



