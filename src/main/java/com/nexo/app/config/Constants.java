package com.nexo.app.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String ANONYMOUS_USER = "anonymoususer";
    
    // Codigos de excepcion
    public static final String NO_ENCONTRADO="404";
    public static final String ERROR_INTERNO="500";

    private Constants() {
    }
}
