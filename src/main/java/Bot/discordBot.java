package Bot;

import Bot.Listeners.commandListener;
import Bot.Listeners.eventListener;
import Bot.Listeners.Music.musicListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class discordBot {

    private final ShardManager shardManager;
    private final Dotenv config;

    public discordBot() throws LoginException {
        this.config = Dotenv.load();
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder
                .createDefault(config.get("TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.competing("Life"));
/**  THIS IS HOW YOU USE A USER CACHE FOR EVENTS IF NEEDED
//        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
//        builder.setChunkingFilter(ChunkingFilter.ALL);
//        builder.enableCache(CacheFlag.ONLINE_STATUS);
 **/
        //  Enable events
        builder.enableIntents(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGES);

        // build the shard manager
        this.shardManager = builder.build();

        // add listeners
        this.shardManager.addEventListener(
                            new eventListener(),
                            new commandListener(),
                            new musicListener());
        }

    public Dotenv getConfig(){
        return this.config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try{
            discordBot funBot = new discordBot();
        } catch (Exception e){
            System.out.println("provided bot token is invalid");
        }
    }

}
