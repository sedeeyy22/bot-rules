import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import io.github.cdimascio.dotenv.Dotenv;
import java.awt.Color;

public class Main extends ListenerAdapter {

    public static void main(String[] args) {
        // تحميل التوكن من ملف .env
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.watching("القوانين ⚖️"));
        builder.addEventListeners(new Main());
        
        // تسجيل الأمر في ديسكورد
        builder.build().updateCommands().addCommands(
            Commands.slash("rules", "عرض قوانين السيرفر الرسمية")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("rules")) {
            // تصميم رسالة القوانين
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("📜 قوانين السيرفر");
            embed.setDescription("يرجى الالتزام بالقوانين لتجنب العقوبات:");
            embed.addField("1. الاحترام", "يمنع السب أو الإهانة بأي شكل.", false);
            embed.addField("2. الإعلانات", "يمنع نشر الروابط الخارجية بدون إذن.", false);
            embed.addField("3. المنشن", "يمنع إزعاج الإدارة بمنشن بدون سبب.", false);
            embed.setColor(Color.BLUE);
            embed.setFooter("إدارة السيرفر", event.getGuild().getIconUrl());

            // الرد على المستخدم
            event.replyEmbeds(embed.build()).queue();
        }
    }
}
