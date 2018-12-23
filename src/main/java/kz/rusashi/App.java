package kz.rusashi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Hello world!
 *
 */
public class App {
	
	private static String API_KEY = "trnsl.1.1.20181222T095812Z.8a25da5a36177389.bf25268d7c300fff2b4bd5b4b064ec9c1634b9da";
	private static String LANGUAGES = "en-de";
	private static String TO_TRANSLATE = "What a wonderful day!";
	
    public static void main( String[] args ) throws URISyntaxException, IOException, InterruptedException, ParseException {
        HttpClient httpClient = HttpClient.newHttpClient();
        System.out.println("http version is " + httpClient.version());
        
        String to_translate = tokenizeInputPhrase(TO_TRANSLATE);
        String callURI = buildURI(to_translate, API_KEY, LANGUAGES);
        
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(callURI)).GET().build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        
        JSONParser parser = new JSONParser();
        JSONObject payload = (JSONObject) parser.parse(response.body());
        JSONArray translations = (JSONArray) payload.get("text");
        String translation = (String) translations.get(0);
        System.out.println("Translation is " + translation);                
    }
    
    
    private static String tokenizeInputPhrase(String inputPhrase) {
        return inputPhrase.replaceAll(" ", "+");
    }
    
    private static String buildURI(String translationString, String APIKey, String targetLanguage) {
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?"
        	+ "key=" + APIKey
            + "&text=" + translationString
            + "&lang=" + targetLanguage
            + "&format=plain";
    }
}
