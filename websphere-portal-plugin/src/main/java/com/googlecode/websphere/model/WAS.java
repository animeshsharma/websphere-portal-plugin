package com.googlecode.websphere.model;

import static com.googlecode.websphere.utils.Constants.SLASH;

import org.apache.maven.settings.Server;

import com.googlecode.jxquery.XMLPopulater;
import com.googlecode.jxquery.jxQuery;

/**
 * Populates WAS Configuration parameters from settings.xml.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
public class WAS {
	@jxQuery(query = "/configuration/ftp_url")
	private String ftpURL;

	@jxQuery(query = "/configuration/was_host")
	private String host;

	@jxQuery(query = "/configuration/was_home")
	private String wasHome;

	@jxQuery(query = "/configuration/was_version")
	private String version = "8";

	@jxQuery(query = "/configuration/was_username")
	private String wasUsername;

	@jxQuery(query = "/configuration/was_password")
	private String wasPassword;

	@jxQuery(query = "/configuration/was_conntype")
	private String conntype;

	@jxQuery(query = "/configuration/was_port")
	private String port;

	@jxQuery(query = "/configuration/was_node")
	private String node;

	@jxQuery(query = "/configuration/was_profile")
	private String profile;

	@jxQuery(query = "/configuration/was_cell")
	private String cell;

	@jxQuery(query = "/configuration/wps_config_url")
	private String configURL;

	@jxQuery(query = "/configuration/wps_config_username")
	private String wpsUsername;

	@jxQuery(query = "/configuration/wps_config_password")
	private String wpsPassword;

	private String ftpUsername;

	private String ftpPassword;

	private String serverName;

	public WAS(Server server) {
		if (null != server) {
			try {
				XMLPopulater.eval(server.getConfiguration().toString(), this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ftpUsername = server.getUsername();
			ftpPassword = server.getPassword();
			serverName = server.getId();
		}
	}

	public String createTmpDirInUserhome() {
		return "/home/" + ftpUsername + SLASH + System.currentTimeMillis()
				+ SLASH;
	}

	public String getCell() {
		return cell;
	}

	public String getConfigURL() {
		return configURL;
	}

	public String getConntype() {
		return conntype;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public String getFtpURL() {
		return ftpURL;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public String getHost() {
		return host;
	}

	public String getNode() {
		return node;
	}

	public String getPort() {
		return port;
	}

	public String getProfile() {
		return profile;
	}

	public String getServerName() {
		return serverName;
	}

	public String getVersion() {
		return version;
	}

	public String getWasHome() {
		return wasHome;
	}

	public String getWasPassword() {
		return wasPassword;
	}

	public String getWasUsername() {
		return wasUsername;
	}

	public String getWpsPassword() {
		return wpsPassword;
	}

	public String getWpsUsername() {
		return wpsUsername;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public void setConfigURL(String configURL) {
		this.configURL = configURL;
	}

	public void setConntype(String conntype) {
		this.conntype = conntype;
	}

	public void setFtpURL(String ftpURL) {
		this.ftpURL = ftpURL;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setWasHome(String wasHome) {
		this.wasHome = wasHome;
	}

	public void setWasPassword(String wasPassword) {
		this.wasPassword = wasPassword;
	}

	public void setWasUsername(String wasUsername) {
		this.wasUsername = wasUsername;
	}

	public void setWpsPassword(String wpsPassword) {
		this.wpsPassword = wpsPassword;
	}

	public void setWpsUsername(String wpsUsername) {
		this.wpsUsername = wpsUsername;
	}
}
