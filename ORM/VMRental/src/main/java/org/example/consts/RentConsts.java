package org.example.consts;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class RentConsts {
    private RentConsts() {}
    public static final CqlIdentifier TABLE_CLIENTS = CqlIdentifier.fromCql("rents_by_client");
    public static final CqlIdentifier TABLE_VMACHINES = CqlIdentifier.fromCql("rents_by_vmachine");
    public static final String UUID_STRING ="uuid";
    public static final CqlIdentifier UUID = CqlIdentifier.fromCql("uuid");
    public static final String CLIENT_UUID_STRING ="clientID";
    public static final CqlIdentifier CLIENT_UUID = CqlIdentifier.fromCql("clientID");
    public static final String VM_UUID_STRING ="vmID";
    public static final CqlIdentifier VM_UUID = CqlIdentifier.fromCql("vmID");
    public static final String BEGIN_TIME_STRING ="beginTime";
    public static final CqlIdentifier BEGIN_TIME = CqlIdentifier.fromCql("beginTime");
    public static final String END_TIME_STRING ="endTime";
    public static final CqlIdentifier END_TIME = CqlIdentifier.fromCql("endTime");
    public static final String RENT_COST_STRING ="rentCost";
    public static final CqlIdentifier RENT_COST = CqlIdentifier.fromCql("rentCost");
}
