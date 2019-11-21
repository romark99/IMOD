import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    private static String api = null;
    private static String botToken = null;

    public static void main(String[] args) {
        ApiContextInitializer.init();

        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/app.properties");
            property.load(fis);
            api = property.getProperty("appid");
            botToken = property.getProperty("bottoken");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sndMessage = new SendMessage();
        sndMessage.enableMarkdown(true);
        sndMessage.setChatId(message.getChatId().toString());
        sndMessage.setReplyToMessageId(message.getMessageId());
        sndMessage.setText(text);
        try {
            setButtons(sndMessage);
            execute(sndMessage);
        }
        catch ( TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (null != message && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "How can I help you?");
                    break;
                case "/setting":
                    sendMsg(message, "What do you want to configure?");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), api, model));
                    } catch (IOException e) {
                        sendMsg(message, "The city is not found.");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowsList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/setting"));

        keyboardRowsList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowsList);
    }

    public String getBotUsername() {
        return "RomarkImodBot";
    }

    public String getBotToken() {
        return botToken;
    }
}
