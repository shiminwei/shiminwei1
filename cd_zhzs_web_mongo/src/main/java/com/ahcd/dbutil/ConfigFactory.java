package com.ahcd.dbutil;

public class ConfigFactory{
	private static Config cfg = null;

	public static Config getConfig() {
		if (cfg == null) {
			cfg = new Config();
		}
		return cfg;
	}
}