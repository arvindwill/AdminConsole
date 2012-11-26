/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.glenwood.glaceemr.utils;

/**
 * * @author gsivashanmugam
 */
public class LogLevel{

    private final String name;
    private final int value;
    
    public static final LogLevel EMRGENCY = new LogLevel("EMRGENCY", 7);
    public static final LogLevel ALERT = new LogLevel("ALERT", 6);
    public static final LogLevel CRITICAL = new LogLevel("CRITICAL", 5);
    public static final LogLevel ERROR = new LogLevel("ERROR", 4);
    public static final LogLevel WARNING = new LogLevel("WARNING", 3);
    public static final LogLevel NOTICE = new LogLevel("NOTICE", 2);
    public static final LogLevel INFO = new LogLevel("INFO", 1);
    public static final LogLevel DEBUG = new LogLevel("DEBUG", 0);
    
    protected LogLevel(String name,int value){
        this.name = name;
        this.value = value;
    }
    
    public final int intValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
}

