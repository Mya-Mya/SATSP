package repository;

import java.io.File;

public class CurrentPath {
    static public final boolean isDebugg=true;
    static public String getCurrentPath(){
        if(isDebugg){
            return System.getProperty("user.dir");
        }else {
            String jarPath=System.getProperty("java.class.path");
            String dirPath=jarPath.substring(0,jarPath.lastIndexOf(File.separator)+1);
            return dirPath;
        }
    }
}
