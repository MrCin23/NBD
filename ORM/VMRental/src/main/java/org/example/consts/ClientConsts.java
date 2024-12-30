package org.example.consts;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientConsts {
    private ClientConsts() {}
    public static final String TABLE_STRING = "client";
    public static final CqlIdentifier TABLE = CqlIdentifier.fromCql("client");
    public static final String UUID_STRING = "clientID";
    public static final CqlIdentifier UUID = CqlIdentifier.fromCql("clientID");
    public static final String NAME_STRING = "firstName";
    public static final CqlIdentifier NAME = CqlIdentifier.fromCql("firstName");
    public static final String SURNAME_STRING = "surname";
    public static final CqlIdentifier SURNAME = CqlIdentifier.fromCql("surname");
    public static final String EMAIL_STRING = "emailAddress";
    public static final CqlIdentifier EMAIL = CqlIdentifier.fromCql("emailAddress");
    public static final String TYPE_STRING = "clientType";
    public static final CqlIdentifier TYPE = CqlIdentifier.fromCql("clientType");
}
