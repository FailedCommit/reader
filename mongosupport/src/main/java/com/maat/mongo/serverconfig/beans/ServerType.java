package com.maat.mongo.serverconfig.beans;

//TODO: copied from config-service. This will be required in all the support libraries.
// Move to servicecommons library to avoid duplication


public enum ServerType {
    MONGO,
    ES,
    REDIS,
    KAFKA
}
