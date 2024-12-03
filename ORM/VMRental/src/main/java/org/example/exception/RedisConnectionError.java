package org.example.exception;

public class RedisConnectionError extends Exception{
    public RedisConnectionError(String message, Throwable cause) {
        super(message, cause);
    }
}
