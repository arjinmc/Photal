package com.arjinmc.photal.exception;

/**
 * Photal Config Exception
 * Created by Eminem Lo on 2018/4/19.
 * email: arjinmc@hotmail.com
 */
public class ConfigException extends Exception{

    public ConfigException(){
        super("Photal config is not initialized correctly");
    }
}
