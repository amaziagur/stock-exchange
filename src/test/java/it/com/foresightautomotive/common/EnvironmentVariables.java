package it.com.foresightautomotive.common;

public class EnvironmentVariables {
    public static final String MONGO_HOST = (System.getenv("MONGO_HOST") == null) ? "localhost" : System.getenv("MONGO_HOST");
}
