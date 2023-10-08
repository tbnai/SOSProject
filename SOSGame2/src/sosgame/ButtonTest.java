package sosgame;

import static org.junit.Assert.*;
import org.junit.Test;

public class ButtonTest {
    @Test
    public void testGetRow() {
        Button button = new Button(2, 3);
        assertEquals(2, button.getRow());
    }

    @Test
    public void testGetCol() {
        Button button = new Button(2, 3);
        assertEquals(3, button.getCol());
    }

}
