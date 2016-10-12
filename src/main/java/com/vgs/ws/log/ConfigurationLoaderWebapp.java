package com.vgs.ws.log;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Santosh.Kumar 
 * Date : Oct 28, 2016
 */
public class ConfigurationLoaderWebapp extends ConfigurationLoader{

    ServletContext context;
    File fallBackRoot;

    public ConfigurationLoaderWebapp(ServletContext context) {
        this.context = context;
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File(context.getRealPath(""),"../module.properties")));
            if("subwebapp".equals(p.getProperty("type")) || "subwebapp".equals(p.getProperty("moduleType"))){
                String f = context.getRealPath("");
                fallBackRoot = new File(f,"../../"+p.getProperty("dependant-webapp")+"/src");
            }
        } catch (IOException e) {
        }
    }

    public URL getResource(String applicationRelativePath) throws MalformedURLException {
        return context.getResource(applicationRelativePath);
    }

    public InputStream getResourceAsStream(String applicationRelativePath) {
        String path;
        // as the documentation of Context.getResource() says, the path must always start with /
        if (!applicationRelativePath.startsWith("/")) {
            path = "/" + applicationRelativePath;
        } else {
            path = applicationRelativePath;
        }
        return context.getResourceAsStream(path);
    }

    public File getFile(String applicationRelativePath) {
        if(applicationRelativePath.startsWith("WEB-INF")) {
            applicationRelativePath = "/"+applicationRelativePath;
        }
        File f = new File(context.getRealPath(applicationRelativePath));
        if(!f.exists() && fallBackRoot!=null){
            f = new File(fallBackRoot,applicationRelativePath);
        }
        return f;
    }

    public Set<String> getResourcePaths(String path) {
        Set<String> set = new HashSet<String>();
        File f = new File(context.getRealPath(path));
        fieldSetPaths(path, set, f);

        if(fallBackRoot!=null){
            f = new File(fallBackRoot,path);

            fieldSetPaths(path, set, f);
        }

        return set;
    }

    private void fieldSetPaths(String path, Set<String> set, File f) {
        if(f.isDirectory()) {
            set.addAll(asListAndAddPath(f.listFiles(), path));
        }
    }

    private Collection<String> asListAndAddPath(File[] fileName, String path){
        Collection<String> c = new ArrayList<String>(fileName.length);
        for (int i = 0; i < fileName.length; i++) {
            c.add(getFilePathAndName(path, fileName[i]));
        }
        return c;
    }

    private String getFilePathAndName(String path, File file){
        return (path.endsWith("/")?path:path+"/")+file.getName()+ (file.isDirectory()?"/":"");
    }
    public String getAppName(){
        return context.getServletContextName();
    }

    public String getAppContextPath(){
        String contextPath = context.getContextPath();
        if(contextPath.length() == 0) {
            return "";
        } else {
            // Only keep the last part of the context, without any /
            int slashIndex = contextPath.lastIndexOf('/');
            return contextPath.substring(slashIndex + 1);
        }
    }

    /**
     * Will enable access current webapp's name statically at services startup or later.
     * Will generate an exception if the current application is not a webapp or the configuration was
     * not loaded properly.
     */
    public static String getStaticAppName() {
        ConfigurationLoaderWebapp instance = (ConfigurationLoaderWebapp)ConfigurationLoader.getInstance();
        return instance.getAppName();
    }

    /**
     * Will enable access to current webapp's path name such as pim42api, pim42sync, pim42wap et al.
     * Will generate an exception if the current application is not a webapp or the configuration was
     * not loaded properly.
     */
    public static String getStaticAppContextPath() {
        ConfigurationLoaderWebapp instance = (ConfigurationLoaderWebapp)ConfigurationLoader.getInstance();
        return instance.getAppContextPath();
    }

}
