package app.ccb.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
public class GsonParserImpl implements GsonParser {
    static Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String exportDataToJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T parseDataFromJson(String str, Class<T> klass) {
        return gson.fromJson(str, klass);
    }
}
