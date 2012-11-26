/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.utils;

import java.io.File;
import java.io.FileOutputStream;


/**
 *
 * @author gsivashanmugam
 */
public class LogFile {
    public static void writeToFile(String fileName, String fileContent)throws Exception{
        File logFile = new File(fileName);
        if(!logFile.getParentFile().exists()){
            logFile.getParentFile().mkdirs();
        }
        
        FileOutputStream outStream = new FileOutputStream(logFile);
        outStream.write(fileContent.getBytes());
        outStream.flush();
        outStream.close();
    }
}
