package nl.prutsor.pruttel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class api {
    private static final Logger LOGGER = LogManager.getLogger();

    public static String api_base = "https://prutsor.nl/pruttel/v2";

    public static String get(String endpoint)
    {
        try {
            String url = api_base + endpoint;

            HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        }
        catch (URISyntaxException e)
        {
            LOGGER.error(e);
        }
        catch (IOException e)
        {
            LOGGER.error(e);
        }
        catch (InterruptedException e)
        {
            LOGGER.error(e);
        }

        return null;
    }

    public class Application
    {
        public String id;
        public String name;
        public String last_online;

        public String datafields;
        public String data;

        public String privacy;
        public String username;
        public String manager;
        public String user_avatar;
    }
}