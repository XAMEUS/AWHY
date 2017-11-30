package org.awhy.utils;

public class Debugger {
	private static boolean enabled = true;

	public static void disable() {
		Debugger.enabled = false;
	}

	public static void enable() {
		Debugger.enabled = true;
	}

	public static boolean isEnabled() {
		return Debugger.enabled;
	}

	public static void log(Object o) {
		System.out.println(o.toString());
	}
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static void println(String s) {
		System.out.println(s);
	}

	public static void setStatus(boolean b) {
		Debugger.enabled = b;
		System.out.println("[DEBUG]: " + Debugger.enabled);
	}
}
