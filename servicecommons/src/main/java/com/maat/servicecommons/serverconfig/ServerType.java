package com.maat.servicecommons.serverconfig;

public enum ServerType {
    MONGO(true),
    ES(true),
    MYSQL(true),
    KAFKA(true),
    REDIS(true),
    POSTGRES(true),
    ATHENA(false),
    S3(false),
    API_SECRETS(false),
    RESTRICTED_API_ACCESS(false),
    PUSHER(false),
    PROPERTIES(false);

    private final boolean hostRequired;
    ServerType(boolean hostRequired) {
        this.hostRequired = hostRequired;
    }
    public boolean isHostRequired() {
        return hostRequired;
    }
}
