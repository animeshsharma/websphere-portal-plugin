package maven.websphere.plugin.utils;

import static maven.websphere.plugin.utils.Constants.SLASH;

import java.io.IOException;
import java.net.SocketException;

import maven.websphere.plugin.model.WAS;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.wagon.PathUtils;

/**
 * Utility class to deal with FTP realted tasks such as opening a connection and
 * deleting a file/directory from FTP server.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class FtpWrapper {
	private FTPClient ftp;

	private boolean passiveMode = true;

	private int connectionTimeout = 60000;

	private String host;

	private String username;

	private String password;

	public FtpWrapper(WAS was) {
		host = PathUtils.host(was.getFtpURL());
		username = was.getFtpUsername();
		password = was.getFtpPassword();
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	protected void openConnection() throws SocketException, IOException,
			MojoExecutionException {
		ftp = new FTPClient();
		ftp.setDefaultTimeout(getConnectionTimeout());
		ftp.setDataTimeout(getConnectionTimeout());
		ftp.connect(host);

		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new MojoExecutionException("FTP server refused connection.");
		}

		if (!ftp.login(username, password)) {
			throw new MojoExecutionException("Authentication failed on FTP");
		}
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.setListHiddenFiles(true);
		if (isPassiveMode()) {
			ftp.enterLocalPassiveMode();
		}
	}

	public void setPermission(String file, String chmod)
			throws MojoExecutionException {
		try {
			openConnection();
			ftp.sendSiteCommand(String.format("chmod %s %s", chmod, file));
		} catch (IOException e) {
			throw new MojoExecutionException("Fail to set permission:" + file,
					e);
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				throw new MojoExecutionException(
						"Fail to close ftp connection.", e);
			}
		}
	}
	
	public void delete(String file) throws MojoExecutionException {
		try {
			openConnection();
			ftp.deleteFile(file);
		} catch (IOException e) {
			throw new MojoExecutionException("Fail to delete file:" + file, e);
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				throw new MojoExecutionException(
						"Fail to close ftp connection.", e);
			}
		}
	}

	public void removeDirectory(String dir) throws MojoExecutionException {
		try {
			openConnection();

			FTPFile[] ffs = ftp.listFiles(dir);
			if (null != ffs) {
				for (FTPFile ff : ffs) {
					ftp.deleteFile(dir + SLASH + ff.getName());
				}
			}

			ftp.removeDirectory(dir);
		} catch (IOException e) {
			throw new MojoExecutionException("Fail to delete file:" + dir, e);
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				throw new MojoExecutionException(
						"Fail to close ftp connection.", e);
			}
		}
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}
}
