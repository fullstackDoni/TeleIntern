package kz.internjava.TeleIntern.bot;

import kz.internjava.TeleIntern.dto.CategoryDTO;
import kz.internjava.TeleIntern.model.Category;
import kz.internjava.TeleIntern.service.BotService;
import kz.internjava.TeleIntern.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String token = "YOUR_TELEGRAM_BOT_TOKEN";
    private final String botUsername = "YOUR_TELEGRAM_BOT_USERNAME";

    @Autowired
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
    public void processViewTreeCommand(Long chatId) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        String tree = buildTree(categories, null, 0);

    }

    private String buildTree(List<CategoryDTO> categories, Long parentId, int level) {
        StringBuilder treeBuilder = new StringBuilder();

        for (CategoryDTO category : categories) {
            if ((parentId == null && category.getParentId() == null) || (parentId != null && parentId.equals(category.getParentId()))) {
                for (int i = 0; i < level; i++) {
                    treeBuilder.append("  ");
                }

                treeBuilder.append("- ").append(category.getName()).append("\n");

                treeBuilder.append(buildTree(categories, category.getId(), level + 1));
            }
        }

        return treeBuilder.toString();
    }

}

