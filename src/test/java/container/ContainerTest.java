package container;

import example.container.Container;
import example.container.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContainerTest {

    private Container container;

    @Before
    public void setup() {
        container = new Container();
    }

    /**
     * Тестирование добавления вещи в контейнер
     */
    @Test
    public void addMethodTest() {
        Assert.assertEquals(0, container.size());

        Item currentItem = new Item(1);
        container.add(currentItem);

        Assert.assertEquals(1, container.size());
        // чтобы удостовериться
        Assert.assertEquals(currentItem, container.get(0));
    }

    /**
     * Тестирование удаления вещи из контейнера
     */
    @Test
    public void removeMethodTest() {
        Item currentItem = new Item(1);

        container.add(currentItem);
        container.remove(currentItem);

        Assert.assertEquals(0, container.size());
        Assert.assertFalse(container.contains(currentItem));
    }

    /**
     * Тестирование получения вещи из контейнера по индексу
     */
    @Test
    public void getMethodTest() {
        Item currentItem = new Item(1);

        container.add(currentItem);

        Assert.assertEquals(currentItem, container.get(0));
    }

    /**
     * Тестирование получения размера контейнера
     */
    @Test
    public void sizeMethodTest() {
        Item currentItem = new Item(1);

        Assert.assertEquals(0, container.size());

        container.add(currentItem);

        Assert.assertEquals(1, container.size());
    }

    /**
     * Тестирование проверки наличия элемента в контейнере
     */
    @Test
    public void containsMethodTest() {
        Item currentItem = new Item(1);

        Assert.assertFalse(container.contains(currentItem));

        container.add(currentItem);

        Assert.assertTrue(container.contains(currentItem));
    }
}
