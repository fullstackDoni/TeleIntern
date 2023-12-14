package kz.internjava.TeleIntern.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot() {
        super();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotToken() {
        return "6956635514:AAEpEXiAzW7Tzk2TKI_l2QRORg0GbXYWiXY";
    }

    @Override
    public String getBotUsername() {

        return "TeleInternBot";
    }
}

