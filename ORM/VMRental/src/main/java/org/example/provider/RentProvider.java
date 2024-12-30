package org.example.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import org.example.codec.LocalDateTimeCodec;
import org.example.consts.RentConsts;
import org.example.model.Rent;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class RentProvider {
    private final CqlSession session;

    private final EntityHelper<Rent> helper;

    public RentProvider(MapperContext ctx, EntityHelper<Rent> helper) {
        this.session = ctx.getSession();
        this.helper = helper;
    }

    public void create(Rent rent) {
        LocalDateTimeCodec codec = new LocalDateTimeCodec();
        Insert byClient = QueryBuilder.insertInto(RentConsts.TABLE_CLIENTS)
                .value(RentConsts.CLIENT_UUID, literal(rent.getClient().getClientID()))
                .value(RentConsts.UUID, literal(rent.getRentID()))
                .value(RentConsts.BEGIN_TIME, literal(rent.getBeginTime(), codec))
                .value(RentConsts.END_TIME, literal(rent.getEndTime(), codec))
                .value(RentConsts.VM_UUID, literal(rent.getVMachine().getUuid()))
                .value(RentConsts.RENT_COST, literal(rent.getRentCost()))
                .ifNotExists();
        Insert byVMachine = QueryBuilder.insertInto(RentConsts.TABLE_VMACHINES)
                .value(RentConsts.VM_UUID, literal(rent.getVMachine().getUuid()))
                .value(RentConsts.UUID, literal(rent.getRentID()))
                .value(RentConsts.END_TIME, literal(rent.getEndTime(), codec))
                .value(RentConsts.BEGIN_TIME, literal(rent.getBeginTime(), codec))
                .value(RentConsts.CLIENT_UUID, literal(rent.getClient().getClientID()))
                .value(RentConsts.RENT_COST, literal(rent.getRentCost()))
                .ifNotExists();
        session.execute(byClient.build());
        session.execute(byVMachine.build());

//to nie dziala
//        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
//                .addStatement(byClient.build())
//                .addStatement(byVMachine.build())
//                .build();
//        session.execute(batch);
    }
}
