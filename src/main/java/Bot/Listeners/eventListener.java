package Bot.Listeners;

import API.GoogleSearchAPI.GoogleSearch;
import API.GoogleSearchAPI.InlineImageRequest;
import API.GoogleSearchAPI.InlineImageResult;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Random;

public class eventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String request = event.getMessage().getContentRaw().toLowerCase();
        String userName = event.getAuthor().getName();
        TextChannel textChannel = event.getChannel().asTextChannel();

        //      GENERAL COMMANDS
        switch (request){
            case ";;hello":
            case ";;hi":
                textChannel.sendMessage("Hello! " + userName).queue();
                break;
            case ";;help":
                this.help(textChannel);
                break;
            default:
                break;
        }

        //      API COMMANDS
        if (request.startsWith(";;image")){
            this.postImage(request.substring(7), textChannel);
        }
    }

    /*
            EVENT-ACTIONS BEING CALLED UPON BY EVENTS
     */
    public void help(TextChannel textChannel){
        StringBuilder helpDisplay = new StringBuilder();

        // In discord copy-format
        helpDisplay.append("```");

        helpDisplay.append("**InfoBot Use**\n\n");
        helpDisplay.append("Commands           Result\n\n");
        //  General Commands
        helpDisplay.append(";;hello            -> Say \"Hello! to InfoBot\"\n");
        helpDisplay.append(";;hi               -> Say \"Hi! to InfoBot\"\n");
        // General Commands [API]
        helpDisplay.append(";;image [text]     -> Display an image of \"[text]\"\n");

        // Slash Commands
        helpDisplay.append("/roles             -> Display all @roles in the server\n");
        helpDisplay.append("/say [text]        -> Repeat the \"[text]\" anonymously\n");
        helpDisplay.append("/job [job name]    -> Query \"[job name]\" Jobs on the web and display results\n");
        helpDisplay.append("     [Optional: page#, default: 0 ]\n\n");

        helpDisplay.append("That's all for now, more commands coming soon!");
        // Close discord copy-format
        helpDisplay.append("```");
        textChannel.sendMessage(helpDisplay.toString()).queue();
    }

    public void postImage(String imageRequest, TextChannel textChannel){
        GoogleSearch google = new GoogleSearch();

        InlineImageRequest ClientRequest = new InlineImageRequest(imageRequest);
        google.search(ClientRequest);
        InlineImageResult result = new InlineImageResult(google.getJson());

        try {
            int sizeOfQuery = result.getImageLinks().size();
            Random rand = new Random();

            int imageRandomID = rand.nextInt(sizeOfQuery);

            String link = result.getImageLinks().get(imageRandomID);
            textChannel.sendMessage(link).queue();
        } catch (NullPointerException e){
            textChannel.sendMessage("Sorry image not found in database" +
                    "\n Image may be placed into the database at a later time").queue();
        }
    }


}
