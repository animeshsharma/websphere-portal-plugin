package maven.websphere.plugin.model;

import static maven.websphere.plugin.utils.Constants.SLASH;
import jxquery.google.XMLPopulater;
import jxquery.google.jxQuery;

import org.apache.maven.settings.Server;

/**
 * Populates WAS Configuration parameters from settings.xml.
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br/>
 */
public class WAS {
	@jxQuery(query = "/configuration/ftp_url")
	private String ftpURL;

	@jxQuery(query = "/configuration/was_host")
	private String host;

	@jxQuery(query = "/configuration/was_home")
	private String wasHome;

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

	public String getWasHome() {
		return wasHome;
	}

	public String getWasPassword() {
		return wasPassword;
	}

	public String getWasUsername() {
		return wasUsername;
	}

	public void setCell(String cell) {
		this.cell = cell;
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

	public void setWasHome(String wasHome) {
		this.wasHome = wasHome;
	}

	public void setWasPassword(String wasPassword) {
		this.wasPassword = wasPassword;
	}

	public void setWasUsername(String wasUsername) {
		this.wasUsername = wasUsername;
	}
}
