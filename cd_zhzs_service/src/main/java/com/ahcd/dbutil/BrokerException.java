package com.ahcd.dbutil;

public class BrokerException extends NestedRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6412761575783588907L;

	public BrokerException(Throwable root) {
		super(root);
	}

	public BrokerException(String string, Throwable root) {
		super(string, root);
	}

	public BrokerException(String s) {
		super(s);
	}
}