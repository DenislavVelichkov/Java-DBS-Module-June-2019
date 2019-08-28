package app.ccb.util;

public interface GsonParser {
    String exportDataToJson(Object obj);
    <T> T parseDataFromJson(String str, Class<T> klass);
}
