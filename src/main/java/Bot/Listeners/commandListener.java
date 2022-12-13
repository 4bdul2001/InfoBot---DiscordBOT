package Bot.Listeners;

import API.GoogleJobsAPI.GoogleJobs;
import API.GoogleJobsAPI.ListingRequest;
import API.GoogleJobsAPI.ListingResult;
import API.YoutubeSearchAPI.VideoRequest;
import API.YoutubeSearchAPI.VideoResult;
import API.YoutubeSearchAPI.YoutubeSearch;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class commandListener extends ListenerAdapter {
    /*
            Currently only using Guild Commands as they are instant to update and
            also because im only using this bot for myself :)

            [GLOBAL COMMANDS] -> Implement if ever decide to market or release the bot
            to various different servers :)
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName().toLowerCase();
        switch(command){
            case "roles":
                this.roles(event);
                break;
            case "say":
                this.say(event);
                break;
            case "job":
                this.job(event);
                break;
            case "video":
                this.youtube_video(event);
                break;
            default:
                event.reply("not implemented").queue();
                break;
        }
    }

    // Guild Commands (max 100) -> instant update
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> Data = this.loadCommands();
        // LOAD IT UP
        event.getGuild().updateCommands().addCommands(Data).queue();
    }

    /**
     * @return a List with all the CommandData -> commands to load up
     */
    public List<CommandData> loadCommands(){
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash(
                "roles",
                "Displays all roles in the server"
        ));

        OptionData sayOption1 = new OptionData(OptionType.STRING, "phrase", "Phrase to repeat", true);
        commandData.add(Commands.slash(
                "say",
                "Repeats the projected message"
        ).addOptions(sayOption1));

        OptionData jobOption1 = new OptionData(OptionType.STRING, "job_name", "Job to query", true);
        OptionData jobOption2 = new OptionData(OptionType.STRING, "page_number", "Result Page to display", false);
        commandData.add(Commands.slash(
                "job",
                "Gives a txt file with job listings"
        ).addOptions(jobOption1, jobOption2));

        OptionData videoOption1 = new OptionData(OptionType.STRING, "video_name", "Video to Search", true);
        commandData.add(Commands.slash(
                "video",
                "Gives the link of the \"video\""
        ).addOptions(videoOption1));

        return commandData;
    }

        /*
           This is so that when you add the bot to a new server, it adds
           the commands there too not just on load
           Currently, there is no need to use this! but we have it here anyway
        */
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> Data = this.loadCommands();
        // LOAD IT UP
        event.getGuild().updateCommands().addCommands(Data).queue();
    }

    /*
            EVENT-ACTIONS BEING CALLED UPON BY EVENTS
     */
    public void roles(SlashCommandInteractionEvent event){
        String output = "";
        // takes longer than 3 seconds so must do this
        event.deferReply().queue();
        for(Role r: event.getGuild().getRoles()){
            output += r.getAsMention() + "\n";
        }
        // Must use a hook here because reply already used
        event.getHook().sendMessage(output).queue();
    }

    public void say(SlashCommandInteractionEvent event){
        OptionMapping message = event.getOption("phrase");
        String inputPhrase = message.getAsString();
        event.getChannel().sendMessage(inputPhrase).queue();
        event.reply("Message Sent!").setEphemeral(true).queue();
    }

    public void job(SlashCommandInteractionEvent event){
        event.deferReply().queue(); // takes longer than 3 seconds so must do this
        OptionMapping jobName = event.getOption("job_name");
        String inputJob = jobName.getAsString();

        GoogleJobs googleJobs = new GoogleJobs();
        ListingRequest request;
        if(event.getOption("page_number")!=null){
            OptionMapping pageInput = event.getOption("page_number");
            String pageNumber = pageInput.getAsString();
            request = new ListingRequest(inputJob, pageNumber);
        } else {
            request = new ListingRequest(inputJob);
        }
        googleJobs.search(request);
        ListingResult result = new ListingResult(googleJobs.getJson());
        result.getJobs();

        FileUpload fileUpload = FileUpload.fromData(
                new File(ListingResult.PATH),
                "Job Listings.txt"
        );

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("Here is a text file with your query!")
                .build();
        event.getHook().sendFiles(fileUpload).addEmbeds(embed).queue();
    }

    public void youtube_video(SlashCommandInteractionEvent event){
        event.deferReply().queue(); // takes longer than 3 seconds so must do this
        OptionMapping VideoName = event.getOption("video_name");
        String videoInput = VideoName.getAsString();

        YoutubeSearch youtubeSearch = new YoutubeSearch();
        VideoRequest request = new VideoRequest(videoInput);
        youtubeSearch.search(request);

        VideoResult result = new VideoResult(youtubeSearch.getJson());
        String link = result.getVideoLink();

        MessageEmbed embed = new EmbedBuilder()
                .setDescription("Here is a link for "+ videoInput +"!")
                .build();
        event.getHook().sendMessage(link).addEmbeds(embed).queue();
    }
}
