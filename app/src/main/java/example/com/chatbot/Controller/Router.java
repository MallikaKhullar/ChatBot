package example.com.chatbot.Controller;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class Router {

    public static String base = " http://www.personalityforge.com/api/";

    public static String sendMessage(){
        return base + "/chat/";
    }
}
