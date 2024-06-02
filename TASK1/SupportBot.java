import java.util.Scanner;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.HashMap;

public class SupportBot {
    private Set<String> negativeRes;
    private Set<String> exitCommands;
    private Map<Pattern, Runnable> supportResponses;
    private String name;

    public SupportBot() {
        // Initialize negative responses and exit commands
        this.negativeRes = new HashSet<>();
        this.negativeRes.add("no");
        this.negativeRes.add("nope");
        this.negativeRes.add("nay");
        this.negativeRes.add("not a chance");
        this.negativeRes.add("sorry");
        this.negativeRes.add("not really");
        this.negativeRes.add("no need");

        this.exitCommands = new HashSet<>();
        this.exitCommands.add("quit");
        this.exitCommands.add("pause");
        this.exitCommands.add("exit");
        this.exitCommands.add("goodbye");
        this.exitCommands.add("bye");
        this.exitCommands.add("thanks");
        this.exitCommands.add("farewell");

        // Define regex patterns and corresponding functions for support responses
        this.supportResponses = new HashMap<>();
        this.supportResponses.put(Pattern.compile(".\\bproduct\\b."), this::askAboutProduct);
        this.supportResponses.put(Pattern.compile(".technical.*support."), this::technicalSupport);
        this.supportResponses.put(Pattern.compile(".(return.*policy|exchange.*policy|refund.*policy|product.*return|can.*return|how.*return|policy.*return)."), this::askAboutReturnPolicy);
        this.supportResponses.put(Pattern.compile(".\\bhow.*help\\b."), this::generalQuery);
    }

    public void greet() {
        // Welcome message and get user's name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Welcome to our customer support.");
        while (true) {
            System.out.print("What's your name?\n");
            this.name = scanner.nextLine().trim();
            // Check if name contains only characters
            if (this.name.matches("[a-zA-Z]+")) {
                break;
            } else {
                System.out.println("\033[91mPlease enter a valid name\033[0m"); // Error message in red
            }
        }
        // Ask how the bot can assist the user
        System.out.printf("Hi %s, need any help regarding our service (yes/no)?\n", this.name);
        String willHelp = scanner.nextLine().trim().toLowerCase();
        // Check if user declines assistance
        if (this.negativeRes.contains(willHelp)) {
            System.out.println("Alright, have a great day!");
        } else {
            // Start the chat session
            this.chat(scanner);
        }
    }

    public void chat(Scanner scanner) {
        while (true) {
            // Get user's query
            System.out.print("Please tell me your query: ");
            String reply = scanner.nextLine().trim().toLowerCase();
            // Check if user wants to exit
            if (makeExit(reply)) {
                System.out.println("Thanks for reaching out. Have a great day!");
                break;
            }
            // Get and print response
            String response = getResponse(reply);
            System.out.println(response);
        }
    }

    public boolean makeExit(String reply) {
        // Check if the reply contains any exit command
        return this.exitCommands.contains(reply);
    }

    public String getResponse(String reply) {
        // Check user's query against support responses
        for (Map.Entry<Pattern, Runnable> entry : this.supportResponses.entrySet()) {
            Pattern pattern = entry.getKey();
            Matcher matcher = pattern.matcher(reply);
            if (matcher.find()) {
                // Call corresponding function for matched pattern
                entry.getValue().run();
                return "";
            }
        }
        // If no match found, provide a default response
        return noMatchIntent();
    }

    public void askAboutProduct() {
        String[] responses = {
            "Our product is top-notch and has excellent reviews!",
            "You can find all product details on our website."
        };
        System.out.println(responses[new Random().nextInt(responses.length)]);
    }

    public void technicalSupport() {
        String[] responses = {
            "Please visit our technical support page for detailed assistance.",
            "You can also call our tech support helpline for immediate help."
        };
        System.out.println(responses[new Random().nextInt(responses.length)]);
    }

    public void askAboutReturnPolicy() {
        String[] responses = {
            "We have a 30-day return policy.",
            "Please ensure the product is in its original condition when returning."
        };
        System.out.println(responses[new Random().nextInt(responses.length)]);
    }

    public void generalQuery() {
        String[] responses = {
            "How can I assist you further?",
            "Is there anything else you'd like to know?"
        };
        System.out.println(responses[new Random().nextInt(responses.length)]);
    }

    public String noMatchIntent() {
        String[] responses = {
            "I'm sorry, I didn't quite understand that. Can you please rephrase?",
            "My apologies, can you provide more details?"
        };
        return responses[new Random().nextInt(responses.length)];
    }

    public static void main(String[] args) {
        // Create an instance of SupportBot and start the greeting process
        SupportBot bot = new SupportBot();
        bot.greet();
    }
}