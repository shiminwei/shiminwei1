package com.ahcd.dbutil;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NestedRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6936167090697334930L;
	private Throwable cause = null;

	public NestedRuntimeException() {
	}

	public NestedRuntimeException(String msg) {
		super(msg);
	}

	public NestedRuntimeException(Throwable cause) {
		this.cause = cause;
	}

	public NestedRuntimeException(String msg, Throwable cause) {
		super(msg);
		this.cause = cause;
	}

	public Throwable getCause() {
		return this.cause;
	}

	public String toString() {
		if (this.cause == null) {
			return super.toString();
		}
		return super.toString() + "\nCaused By: " + this.cause.toString();
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (this.cause != null) {
			System.err.println("\nCaused By: ");
			this.cause.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (this.cause != null) {
			ps.println("\nCaused By: ");
			this.cause.printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		super.printStackTrace(pw);
		if (this.cause != null) {
			pw.println("\nCaused By: ");
			this.cause.printStackTrace(pw);
		}
	}
}