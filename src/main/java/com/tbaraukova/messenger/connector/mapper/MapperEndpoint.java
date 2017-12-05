package com.tbaraukova.messenger.connector.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import com.tbaraukova.messenger.connector.mapper.domain.MappingPair;
import io.javalin.Javalin;

public class MapperEndpoint {
    public static final RethinkDB r = RethinkDB.r;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String ID = ":id";
    public static final String API_MESSENGER_MAPPING = "/api/messenger/mapping/";

    public static void main(String[] args) {
        Connection conn = r.connection().hostname("localhost").port(28015).connect();
        try {
            r.dbCreate("messenger").run(conn);
            r.db("messenger").tableCreate("mapping").run(conn);
        } catch (ReqlOpFailedError error) {
            System.out.println(error.getMessage());
            r.dbDrop("messenger").run(conn);
            r.dbCreate("messenger").run(conn);
            r.db("messenger").tableCreate("mapping").run(conn);
        }
        Javalin app = Javalin.start(7000);
        app.post(API_MESSENGER_MAPPING, ctx -> {
            MappingPair mappingPair = ctx.bodyAsClass(MappingPair.class);
            Object run;
            if (((Cursor) r.db("messenger").table("mapping")
                    .filter(r -> r.g("id").eq(mappingPair.getId())).run(conn)).hasNext()) {
                run = r.db("messenger").table("mapping").replace(mappingPair).run(conn);
            } else {
                run = r.db("messenger").table("mapping").insert(mappingPair).run(conn);
            }
            System.out.println(run.toString());
        });
        app.get(API_MESSENGER_MAPPING, ctx -> {
            Cursor mapping = (r.db("messenger").table("mapping").run(conn));
            ctx.result(OBJECT_MAPPER.writeValueAsString(mapping.toList()));
        });
        app.delete(API_MESSENGER_MAPPING + ID, ctx -> {
            Cursor mapping = (r.db("messenger").table("mapping")
                    .filter(r -> r.g("id").eq(ctx.param(ID))).delete().run(conn));
            ctx.result(OBJECT_MAPPER.writeValueAsString(mapping.toList()));
        });
        app.get(API_MESSENGER_MAPPING + ID, ctx -> {
            Cursor mapping = (r.db("messenger").table("mapping")
                    .filter(r -> r.g("id").eq(ctx.param(ID))).run(conn));
            ctx.result(OBJECT_MAPPER.writeValueAsString(mapping.toList()));
        });
    }

}
