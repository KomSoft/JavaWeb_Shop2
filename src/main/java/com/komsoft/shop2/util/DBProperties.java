package com.komsoft.shop2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DBProperties {

    private final String prefix;
    private final String prefix2 = ".datasource.";
    private String url = null;
    private String userName = null;
    private String password = null;
    private String driverName = null;
    Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public DBProperties(String prefix) {
        this.prefix = prefix + prefix2;
    }

    public boolean isReady() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            url = bundle.getString(this.prefix + "url");
            userName = bundle.getString(this.prefix + "username");
            password = bundle.getString(this.prefix + "userpassword");
            driverName = bundle.getString(this.prefix + "drivername");
//            logger.info("BaseBundleName={},  driverName={},  url={},  userName={}", bundle.getBaseBundleName(), driverName, url, userName);
            return !url.isEmpty() && !userName.isEmpty() && !password.isEmpty() && !driverName.isEmpty();
        } catch (NullPointerException | MissingResourceException | ClassCastException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverName() {
        return driverName;
    }

}
