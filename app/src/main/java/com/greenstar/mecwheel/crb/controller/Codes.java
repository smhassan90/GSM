package com.greenstar.mecwheel.crb.controller;

public interface Codes {
    public static final String ALL_OK = "200";
    public static final String ALREADY_EXISTS = "409";
    public static final String PREF_NAME = "Greenstar";
    /*
    StaffType 1 = HS Team App GSM Falcon
    StaffType 2 = Sales App
    StaffType 3 = DTC app
    StaffType 4 = Greenstar App (MEC Wheel)
     */
    final public static String STAFFTYPE = "4";
    final public static String TIMEOUT = "Timeout";
    final public static String SOMETHINGWENTWRONG = "2001";
    final public static String SOMETHINGWENTWRONG2 = "502";
    final public static String myFormat = "MM/dd/yy";
    final public static String currentUser = "Current User";
    final public static String everUser = "Ever User";
    final public static String neverUser = "Never User";
}
