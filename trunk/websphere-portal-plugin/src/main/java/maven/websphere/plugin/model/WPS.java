package maven.websphere.plugin.model;

import org.apache.maven.settings.Server;

import com.googlecode.jxquery.jxQuery;

/**
 * Populates WPS Configuration parameters from settings.xml.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
public class WPS extends WAS {
	@jxQuery(query = "/configuration/wps_config_url")
	private String configURL;
	@jxQuery(query = "/configuration/wps_config_username")
	private String wpsUsername;
	@jxQuery(query = "/configuration/wps_config_password")
	private String wpsPassword;

	public WPS(Server server) {
		super(server);
	}

	public String getConfigURL() {
		return configURL;
	}

	public String getWpsPassword() {
		return wpsPassword;
	}

	public String getWpsUsername() {
		return wpsUsername;
	}

	public void setConfigURL(String configURL) {
		this.configURL = configURL;
	}

	public void setWpsPassword(String wpsPassword) {
		this.wpsPassword = wpsPassword;
	}

	public void setWpsUsername(String wpsUsername) {
		this.wpsUsername = wpsUsername;
	}

}
