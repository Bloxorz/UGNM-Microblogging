package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Date;

/**
 * Created by Marv on 12.11.2014.
 */
public abstract class AbstractResource {

    Connection conn;


    public AbstractResource(Connection conn) {
        this.conn = conn;
    }

    public Connection setConnection(Connection newConn) {
        Connection oldConn = conn;
        conn = newConn;
        return oldConn;
    }

    protected Gson getDefaultGson() {
        JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        return new GsonBuilder()
                .registerTypeAdapter(Date.class, dateJsonSerializer)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
