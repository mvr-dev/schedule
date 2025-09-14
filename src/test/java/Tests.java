import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mvr.schedule.Bot;
import dev.mvr.schedule.RequestUtil;
import dev.mvr.schedule.Utils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests {



    @Test
    public void testRequest(){
        System.out.println(RequestUtil.getOmstuGroupJson("ПРД-231"));
    }

    @Test
    public void testMapper() throws JsonProcessingException {
        System.out.println(Utils.getOmstuGroup("ПРД-231"));
    }

}
