import dev.mvr.schedule.Bot;
import dev.mvr.schedule.RequestUtil;
import dev.mvr.schedule.Utils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests {
    @Test
    public void testRegex(){

        assertTrue(Utils.testGroup("ХХБ-001-О-01"));
    }

    @Test
    public void testRegex2(){
        Bot bot = new Bot();
        assertFalse(Utils.testGroup("ЁЖЗ-999-Щ-99"));
    }

}
