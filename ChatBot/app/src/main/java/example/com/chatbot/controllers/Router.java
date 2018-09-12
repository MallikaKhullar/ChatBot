package example.com.chatbot.controllers;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class Router {

    public static String base = " https://www.personalityforge.com/api";

    public static String sendMessage(){
        return base + "/chat/?apiKey=%s&message=%s&chatBotID=%s&externalID=%s";
    }
}
