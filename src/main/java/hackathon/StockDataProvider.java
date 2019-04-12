package hackathon;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StockDataProvider {

    public static final String BASE_IEX_URL = "https://api.iextrading.com/1.0";

    private HttpClient client;
    private ObjectMapper mapper;

    public StockDataProvider(){
        // TODO: Change this back
        //client = HttpClientBuilder.create().build();
        client = HttpClientBuilder.create().build();
        mapper = new ObjectMapper();
    }

    public StockData[] getStockData(String[] stockSymbols){
        String concatSymbols = Arrays.asList(stockSymbols).stream().collect(Collectors.joining(","));
        String uri = BASE_IEX_URL + "/tops?symbols=" + concatSymbols;
        HttpGet getData = new HttpGet(uri);
        getData.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        String jsonResponse;

        try {
            HttpResponse response = client.execute(getData);
            jsonResponse = readResponseFromInputStream(response.getEntity().getContent());
        } catch (IOException e){
            System.out.println(String.format("An IOException occurred when attempting to get IEX stock data from " +
                    "uri: %s", uri));
            e.printStackTrace();
            return null;
        }

        try {
            return mapper.readValue(jsonResponse, StockData[].class);
        } catch (IOException e) {
            System.out.println("An IOException occurred while deserializing IEX response to StockData object(s)");
            e.printStackTrace();
            return null;
        }
    }

    public List<StockData> getStockData(List<String> stockSymbols){
        return Arrays.asList(getStockData((String[])stockSymbols.toArray()));
    }

    private static String readResponseFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString("UTF-8");
    }
}