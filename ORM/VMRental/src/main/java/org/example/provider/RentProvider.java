package org.example.provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import org.example.DBConnection;
import org.example.codec.LocalDateTimeCodec;
import org.example.consts.RentConsts;
import org.example.consts.VMConsts;
import org.example.dao.ClientDao;
import org.example.dao.VMachineDao;
import org.example.mapper.ClientMapper;
import org.example.mapper.VMachineMapper;
import org.example.model.*;

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
    private final LocalDateTimeCodec codec = new LocalDateTimeCodec();
    public RentProvider(MapperContext ctx) {
        this.session = ctx.getSession();
    }

    public void create(Rent rent) {
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
        if(table.equals(RentConsts.TABLE_CLIENTS)) {
            return getRentsByClient(select);
        } else if (table.equals(RentConsts.TABLE_VMACHINES)) {
            return getRentsByVMachine(select);
        } else {
            throw new IllegalStateException("Given table does not exist");
        }
    }

    public List<Rent> findByClientId(UUID clientId) {
        Select select = QueryBuilder.selectFrom(RentConsts.TABLE_CLIENTS).all()
                .whereColumn(RentConsts.CLIENT_UUID).isEqualTo(literal(clientId));
                //.whereColumn(RentConsts.BEGIN_TIME).isGreaterThan(literal(Instant.now()));
        return getRentsByClient(select);
    }
    public List<Rent> findByVMachineId(UUID uuid) {
        Select select = QueryBuilder.selectFrom(RentConsts.TABLE_VMACHINES).all()
                .where(Relation.column(RentConsts.VM_UUID).isEqualTo(literal(uuid)));
        return getRentsByVMachine(select);
    }

    public void endRent(Rent rent) {
        rent.endRent(LocalDateTime.now());
        Update updateByClient = QueryBuilder.update(RentConsts.TABLE_CLIENTS)
                .setColumn(RentConsts.END_TIME, literal(rent.getEndTime(), codec))
                .setColumn(RentConsts.RENT_COST, literal(rent.getRentCost()))
                .where(Relation.column(RentConsts.CLIENT_UUID).isEqualTo(literal(rent.getClient().getClientID())))
//                .where(Relation.column(RentConsts.BEGIN_TIME).isEqualTo(literal(rent.getBeginTime(), codec)));
                .where(Relation.column(RentConsts.UUID).isEqualTo(literal(rent.getRentID())));
        Update updateByVM = QueryBuilder.update(RentConsts.TABLE_VMACHINES)
                .setColumn(RentConsts.END_TIME, literal(rent.getEndTime(), codec))
                .setColumn(RentConsts.RENT_COST, literal(rent.getRentCost()))
                .where(Relation.column(RentConsts.VM_UUID).isEqualTo(literal(rent.getVMachine().getUuid())))
                .where(Relation.column(RentConsts.UUID).isEqualTo(literal(rent.getRentID())));
//        Update vm = QueryBuilder.update(VMConsts.TABLE)
//                .setColumn(VMConsts.RENTED, literal(rent.getVMachine().isRented()))
//                .where(Relation.column(VMConsts.UUID).isEqualTo(literal(rent.getVMachine().getUuid())));
        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(updateByClient.build())
                .addStatement(updateByVM.build())
                .build();
        session.execute(batch);
    }

    ////////////////////////////////////
    // Helper functions
    ////////////////////////////////////
    private List<Rent> getRentsByClient(Select select) {
        ResultSet rs = session.execute(select.build());
        List<Rent> rents = new ArrayList<>();
        List<Row> rows = rs.all();
        for (Row row : rows) {
            rents.add(getRentsByClient(row));
        }
        return rents;
    }

    private List<Rent> getRentsByVMachine(Select select) {
        ResultSet rs = session.execute(select.build());
        List<Rent> rents = new ArrayList<>();
        List<Row> rows = rs.all();
        for (Row row : rows) {
            rents.add(getRentsByVMachine(row));
        }
        return rents;
    }

    private Rent getRentsByClient(Row row) {
        return new RentsByClient(
                row.getUuid(RentConsts.UUID),
                row.getUuid(RentConsts.CLIENT_UUID),
                row.getUuid(RentConsts.VM_UUID),
                localDateTimeFromField(row, RentConsts.BEGIN_TIME),
                localDateTimeFromField(row, RentConsts.END_TIME),
                row.getDouble(RentConsts.RENT_COST)
        );
    }

    private Rent getRentsByVMachine(Row row) {
        return new RentsByVMachine(
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
