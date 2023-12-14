package kz.internjava.TeleIntern.bot;

import kz.internjava.TeleIntern.model.Category;
import kz.internjava.TeleIntern.service.CategoryService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String token = "YOUR_TELEGRAM_BOT_TOKEN";
    private final String botUsername = "YOUR_TELEGRAM_BOT_USERNAME";
    private final CategoryService categoryService;

    public TelegramBot(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/createCategory")) {
                String[] args = messageText.split(" ");
                if (args.length >= 2) {
                    Category category = categoryService.createCategory(args[1], null);
                    sendMessage(chatId, "Category created with id: " + category.getId());
                } else {
                    sendMessage(chatId, "Usage: /createCategory <category_name>");
                }
            }
        }
    }

    private void sendMessage(long chatId, String s) {
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}

