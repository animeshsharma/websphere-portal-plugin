package com.googlecode.websphere.cli;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * Utility class to capture error logs.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
public class ErrorConsumer implements StreamConsumer {
	private final Log log;

	public ErrorConsumer(Log log) {
		this.log = log;
	}

	public void consumeLine(String line) {
		if (log.isErrorEnabled()) {
			log.error(line);
		}
	}
}