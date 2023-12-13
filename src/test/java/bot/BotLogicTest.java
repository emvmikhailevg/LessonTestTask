package bot;

import example.bot.BotLogic;
import example.bot.State;
import example.bot.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BotLogicTest {

    private BotLogic botLogic;
    private User user;
    private final FakeBot fakeBot = new FakeBot();

    @Before
    public void setup() {
        botLogic = new BotLogic(fakeBot);
        user = new User(1L);
    }

    /**
     * Тестирование команды "/test", если пользователь ответил на вопросы верно. Состояние должно
     * поменяться на TEST, а бот должен отправить свой первый вопрос
     */
    @Test
    public void testCommandTestForRightAnswers() {
        botLogic.processCommand(user, "/test");

        // Проверка на верное состояние
        Assert.assertEquals(State.TEST, user.getState());
        // Проверка на первый вопрос
        Assert.assertEquals("Вычислите степень: 10^2", fakeBot.getMessages().get(0));

        botLogic.processCommand(user, "100");

        // Проверка на верный ответ пользователя
        Assert.assertEquals("Правильный ответ!", fakeBot.getMessages().get(1));
        // После поступает новый вопрос, его тоже надо проверить
        Assert.assertEquals("Сколько будет 2 + 2 * 2", fakeBot.getMessages().get(2));

        botLogic.processCommand(user, "6");

        // Проверка на верный ответ пользователя
        Assert.assertEquals("Правильный ответ!", fakeBot.getMessages().get(3));
        // После состояние должно обновиться
        Assert.assertEquals(State.INIT, user.getState());
        Assert.assertEquals("Тест завершен", fakeBot.getMessages().get(4));
    }

    /**
     * Тестирование команды "/test", если пользователь ответил на вопросы неверно
     */
    @Test
    public void testCommandTestForWrongAnswers() {
        botLogic.processCommand(user, "/test");

        Assert.assertEquals(State.TEST, user.getState());
        Assert.assertEquals("Вычислите степень: 10^2", fakeBot.getMessages().get(0));

        botLogic.processCommand(user, "12");

        Assert.assertEquals("Вы ошиблись, верный ответ: 100", fakeBot.getMessages().get(1));
        Assert.assertEquals(1, user.getWrongAnswerQuestions().size());

        Assert.assertEquals("Сколько будет 2 + 2 * 2", fakeBot.getMessages().get(2));

        botLogic.processCommand(user, "2003");

        Assert.assertEquals("Вы ошиблись, верный ответ: 6", fakeBot.getMessages().get(3));
        Assert.assertEquals(2, user.getWrongAnswerQuestions().size());
        Assert.assertEquals(State.INIT, user.getState());
        Assert.assertEquals("Тест завершен", fakeBot.getMessages().get(4));
    }

    /**
     * Тестирование команды "/notify" и отправки напоминаний
     */
    @Test
    public void notifyCommandTest() throws InterruptedException {
        botLogic.processCommand(user, "/notify");

        Assert.assertEquals(State.SET_NOTIFY_TEXT, user.getState());
        Assert.assertEquals("Введите текст напоминания", fakeBot.getMessages().get(0));

        botLogic.processCommand(user, "Первое напоминание");
        Assert.assertEquals(State.SET_NOTIFY_DELAY, user.getState());
        Assert.assertEquals("Через сколько секунд напомнить?", fakeBot.getMessages().get(1));

        // следует проверить, что пользователь введет именно число
        botLogic.processCommand(user, "3");
        // состояние должно стать INIT
        Assert.assertEquals(State.INIT, user.getState());
        Assert.assertEquals("Напоминание установлено", fakeBot.getMessages().get(2));

        Assert.assertEquals(3, fakeBot.getMessages().size());

        Thread.sleep(1000);

        // Из презентации ясно, что нужно написать эту строчку кода, но на ней вылетает
        // исключение. Можете объяснить, обязательно ли ее писать в таком случае?
        Assert.assertEquals("Сработало напоминание: 'Первое напоминание'", fakeBot.getMessages().get(3));
    }

    /**
     * Тестирование команды "/repeat", которая должна срабатывать, если пользователь
     * отвечал на вопросы неверно и хочет, чтоб бот начал повторять
     */
    @Test
    public void repeatCommandTest() {
        // если пользователь еще не ввел команду "/test", можно сразу проверить,
        // что вопросов для повторения действительно нет
        botLogic.processCommand(user, "/repeat");
        Assert.assertEquals("Нет вопросов для повторения", fakeBot.getMessages().get(0));

        botLogic.processCommand(user, "/test");
        // предположим пользователь ответил так, то есть неверно
        botLogic.processCommand(user, "12");
        botLogic.processCommand(user, "2003");

        // тогда при команде повторения
        botLogic.processCommand(user, "/repeat");
        // будет обновлено состояние и выведен данный вопрос
        Assert.assertEquals(State.REPEAT, user.getState());
        Assert.assertEquals("Вычислите степень: 10^2", fakeBot.getMessages().get(6));

        // ну и далее пользователь ответил верно
        botLogic.processCommand(user, "100");
        Assert.assertEquals("Правильный ответ!", fakeBot.getMessages().get(7));
        Assert.assertEquals("Сколько будет 2 + 2 * 2", fakeBot.getMessages().get(8));

        botLogic.processCommand(user, "6");
        Assert.assertEquals("Правильный ответ!", fakeBot.getMessages().get(9));
        Assert.assertEquals(State.INIT, user.getState());
        Assert.assertEquals("Тест завершен", fakeBot.getMessages().get(10));
    }
}
