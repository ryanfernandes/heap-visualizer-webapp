/*
 * Java Heap Visualizer
 *
 * Copyright 2011, Ryan Fernandes (https://code.google.com/u/@VBFTRFJWDxFDXgJ4/)
 * Licensed under The MIT License.
 * see license.txt
 *
 */
package ryan.jvmmonitor;

import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;

/**
 *
 * @author Ryan Fernandes
 */
public class MemoryComponent {
	private MemoryUsage usage = null;
	private MemoryType type = null;
	private String name = null;

	MemoryComponent(String name, MemoryType memoryType, MemoryUsage usage) {
		this.name = name;
		this.usage = usage;
		this.type = memoryType;
	}

	public String getName() {
		return name;
	}

	public MemoryType getType() {
		return type;
	}

	public MemoryUsage getUsage() {
		return usage;
	}

}
