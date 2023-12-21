package bot;

import example.bot.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Фэйк реализация бота специально для тестов для сохранения сообщений,
 * которые отправил {@link example.bot.BotLogic}
 */
public class FakeBot implements Bot {

    /**
     * Сообщения, которые отправил бот
     */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Возвращение сообщений бота
     * @return сообщения бота
     */
    public List<String> getMessages() {
        return messages;
    }
}
