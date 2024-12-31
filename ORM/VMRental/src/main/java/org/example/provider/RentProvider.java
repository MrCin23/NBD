package org.example.provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.example.DBConnection;
import org.example.codec.LocalDateTimeCodec;
import org.example.consts.RentConsts;
import org.example.dao.ClientDao;
import org.example.dao.VMachineDao;
import org.example.mapper.ClientMapper;
import org.example.mapper.VMachineMapper;
import org.example.model.Rent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
//        to nie dziala
//        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
//                .addStatement(byClient.build())
//                .addStatement(byVMachine.build())
//                .build();
//        session.execute(batch);
    }
    public List<Rent> findAllByTable(CqlIdentifier table) {
        Select select = QueryBuilder.selectFrom(table)
                .all();
        return getRents(select);
    }

    public List<Rent> findByClientId(UUID clientId) {
        Select select = QueryBuilder.selectFrom(RentConsts.TABLE_CLIENTS).all()
                .where(Relation.column(RentConsts.CLIENT_UUID).isEqualTo(literal(clientId)));
                //.where(); // giga refaktor xddd
        //tutaj ma byc filtrowanie po kluczu partycjonujacym ktorym obecnie jest rentuuid xd
        return getRents(select);
    }
    public List<Rent> findByVMachineId(UUID uuid) {
        Select select = QueryBuilder.selectFrom(RentConsts.TABLE_VMACHINES)
                .all().where(Relation.column(RentConsts.VM_UUID).isEqualTo(literal(uuid)));
        return getRents(select);
    }

    private List<Rent> getRents(Select select) {
        ResultSet rs = session.execute(select.build());
        List<Rent> rents = new ArrayList<>();
        List<Row> rows = rs.all();
        for (Row row : rows) {
            rents.add(getRent(row));
        }
        return rents;
    }

    private Rent getRent(Row row) {
        return new Rent(
                row.getUuid(RentConsts.UUID),
                row.getUuid(RentConsts.CLIENT_UUID),
                row.getUuid(RentConsts.VM_UUID),
                localDateTimeFromField(row, RentConsts.BEGIN_TIME),
                localDateTimeFromField(row, RentConsts.END_TIME),
                row.getDouble(RentConsts.RENT_COST)
        );
    }

    private LocalDateTime localDateTimeFromField(Row row, CqlIdentifier field) {
        if(row.getInstant(field) == null) {
            return null;
        } else {
            return LocalDateTime.ofInstant(Objects.requireNonNull(row.getInstant(field)), ZoneOffset.UTC);
        }
    }
}
