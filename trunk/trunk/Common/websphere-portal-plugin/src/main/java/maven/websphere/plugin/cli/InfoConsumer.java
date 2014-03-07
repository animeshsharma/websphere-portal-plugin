package maven.websphere.plugin.cli;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * Utility class to capture info logs.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class InfoConsumer implements StreamConsumer {
	private final Log log;

	public InfoConsumer(Log log) {
		this.log = log;
	}

	public void consumeLine(String line) {
		if (log.isInfoEnabled()) {
			log.info(line);
		}
	}
	
	
}
