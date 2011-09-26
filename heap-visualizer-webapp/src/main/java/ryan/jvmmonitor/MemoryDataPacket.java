/*
 * Java Heap Visualizer
 *
 * Copyright 2011, Ryan Fernandes (https://code.google.com/u/@VBFTRFJWDxFDXgJ4/)
 * Licensed under The MIT License.
 * see license.txt
 *
 */

package ryan.jvmmonitor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan Fernandes
 */
public class MemoryDataPacket {
	private MemoryComponent heap = null;
	private MemoryComponent nonHeap = null;
	private List<MemoryComponent> heapComponents = new ArrayList<MemoryComponent>();
	private List<MemoryComponent> nonHeapComponents = new ArrayList<MemoryComponent>();

	public MemoryComponent getHeap() {
		return heap;
	}

	public void setHeap(MemoryComponent heap) {
		this.heap = heap;
	}

	public MemoryComponent getNonHeap() {
		return nonHeap;
	}

	public void setNonHeap(MemoryComponent nonHeap) {
		this.nonHeap = nonHeap;
	}


	public void addHeapComponent(MemoryComponent m) {
		getHeapComponents().add(m);
	}
	
	public void addNonHeapComponent(MemoryComponent m) {
		getNonHeapComponents().add(m);
	}

	public List<MemoryComponent> getHeapComponents() {
		return heapComponents;
	}

	public List<MemoryComponent> getNonHeapComponents() {
		return nonHeapComponents;
	}
	
	
}
