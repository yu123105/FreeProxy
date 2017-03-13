
package com.fish.proxy.bean.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Properties;

/**
 * Zookeeper系统配置参数，主要用于Spring参数加载使用
 */
public class ZooKeeperPropertiesFactoryBean implements FactoryBean<Properties>, EnvironmentAware {

	protected static Logger logger = LoggerFactory.getLogger(ZooKeeperPropertiesFactoryBean.class);

	private static final String CONFIG_ROOT = "/fish/config";

	private static final String PATH_SEPARATOR = "/";

	private final Properties deployProperties = new Properties();

	private boolean isInit = false;

	private Object lock = new Object();

	private String zooKeeperUrl;

	private String appName;

	private Environment environment;

	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Properties getObject() throws Exception {
		if (!isInit) {
			synchronized (lock) {
				if (!isInit) {
					Properties properties = getAppConfig();
					deployProperties.putAll(properties);

					//如果本地有配置，则本地优先
					String userHome = System.getProperty("user.home");
					String filePath = String.format("%s/conf/%s.properties", userHome, appName);
					File file = new File(filePath);
					if (file.exists()) {
						logger.info("Local Override Properties!Found app proerties file=[{}]", filePath);
						Properties localProperties = new Properties();
						localProperties.load(new FileReader(file));
						deployProperties.putAll(localProperties);
					} else {
						logger.info("Not Found app proerties file=[{}],Local Override Disable.....", filePath);
					}

					if (environment instanceof ConfigurableEnvironment) {
						((ConfigurableEnvironment) environment).getPropertySources().addFirst(
								new PropertiesPropertySource(appName, deployProperties));
					}
					isInit = true;
					logger.info("======final env properties==========\r\n{}", deployProperties);
				}
			}
		}
		return deployProperties;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setZooKeeperUrl(String zooKeeperUrl) {
		this.zooKeeperUrl = zooKeeperUrl;
	}

	private Properties getAppConfig() throws Exception {
		int baseSleepTimeMs = 2000;
		int maxRetries = 5;
		CuratorFramework zkClient = CuratorFrameworkFactory.newClient(zooKeeperUrl, new ExponentialBackoffRetry(
				baseSleepTimeMs, maxRetries));
		zkClient.start();
		try {
			Properties properties = new Properties();
			String path = CONFIG_ROOT + PATH_SEPARATOR + appName;
			logger.info("Read App Config Properties From ZookKeeper[{}] path[{}]", zooKeeperUrl, path);
			byte[] configData = zkClient.getData().forPath(path);
			String appDeployConfig = new String(configData, "UTF-8");
			logger.info("======zookeeper properties==========\r\n{}", appDeployConfig);
			StringReader reader = new StringReader(appDeployConfig);
			properties.load(reader);
			reader.close();
//			JSONObject configJson = JSONObject.parseObject(appDeployConfig);
//			Set<Map.Entry<String, Object>> entries = configJson.entrySet();
//			Iterator<Map.Entry<String, Object>> it = entries.iterator();
//			while (it.hasNext()) {
//				Map.Entry<String, Object> entry = it.next();
//				properties.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
//			}
			return properties;
		} finally {
			zkClient.close();
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
