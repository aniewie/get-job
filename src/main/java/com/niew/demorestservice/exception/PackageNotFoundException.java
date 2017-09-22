package com.niew.demorestservice.exception;

public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(String id) {
        super("No package with id:" + id);
    }
}
