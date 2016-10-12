package com.vgs.ws.log;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * @author Santosh.Kumar 
 * Date : Oct 28, 2016
 */
public abstract class ConfigurationLoader {

	/**
	 * @param applicationRelativePath
	 * @return
	 * @throws MalformedURLException
	 */
	public abstract URL getResource(String applicationRelativePath)
			throws MalformedURLException;

	/**
	 * @param applicationRelativePath
	 * @return
	 * @throws IOException
	 */
	public abstract InputStream getResourceAsStream(
			String applicationRelativePath) throws IOException;

	/**
	 * @param applicationRelativePath
	 * @return
	 */
	public abstract File getFile(String applicationRelativePath);

	/**
	 * @return appName
	 */
	public abstract String getAppName();

	/**
	 * @param path
	 * @return
	 */
	public abstract Set<?> getResourcePaths(String path);

	/**
	 * @param applicationRelativePath
	 * @return
	 * @throws IOException
	 */
	public InputSource getInputSource(String applicationRelativePath)
			throws IOException {
		InputSource result = new InputSource(
				getResourceAsStream(applicationRelativePath));
		result.setSystemId(applicationRelativePath);
		return result;
	}

	static ConfigurationLoader instance;

	/**
	 * @return ConfigurationLoader
	 */
	public static ConfigurationLoader getInstance() {
		return instance;
	}

	public static void init(ConfigurationLoader loader) {
		instance = loader;
	}
}
