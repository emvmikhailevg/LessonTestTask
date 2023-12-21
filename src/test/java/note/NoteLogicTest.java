package note;

import example.note.NoteLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoteLogicTest {

    private NoteLogic noteLogic;

    @Before
    public void setup() {
        noteLogic = new NoteLogic();
    }

    /**
     * Команда "/add" должна добавлять заметку
     */
    @Test
    public void addCommandTest() {
        String addCommand = noteLogic.handleMessage("/add something");
        String notesCommand = noteLogic.handleMessage("/notes");

        Assert.assertEquals("Note added!", addCommand);
        Assert.assertEquals("Your notes:\n" +
                " 1. something", notesCommand);
    }

    /**
     * Команда "/edit" должна изменять конкретную заметку
     */
    @Test
    public void editCommandTest() {
        noteLogic.handleMessage("/add something");
        String editCommand = noteLogic.handleMessage("/edit 1 something else");
        String notesCommand = noteLogic.handleMessage("/notes");

        Assert.assertEquals("Note edited!", editCommand);
        Assert.assertEquals("Your notes:\n " +
                "1. something else", notesCommand);
    }

    /**
     * Команда "/del" должна удалять конкретную заметку
     */
    @Test
    public void deleteCommandTest() {
        noteLogic.handleMessage("something");
        noteLogic.handleMessage("something else");
        String deleteCommand = noteLogic.handleMessage("/del 1");
        String notesCommand = noteLogic.handleMessage("/notes");

        Assert.assertEquals("Note deleted!", deleteCommand);
        Assert.assertEquals("Your notes:\n" +
                " 1. something else", notesCommand);
    }
}
