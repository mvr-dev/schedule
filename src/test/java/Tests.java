import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mvr.schedule.utils.RequestUtil;
import dev.mvr.schedule.utils.Utils;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests {



//    @Test
//    public void testRequest(){
//        System.out.println(RequestUtil.getOmstuGroupJson("ПРД-231"));
//    }
//
//    @Test
//    public void testMapper() throws JsonProcessingException {
//        System.out.println(Utils.getOmstuGroup("ПРД-231"));
//    }
//    @Test
//    public void testOmsuRequest(){
//        System.out.println(RequestUtil.getOmsuGroups());
//    }
//    @Test
//    public void testOmsuSchedule(){
//        System.out.println(RequestUtil.getOmsuGroupSchedule("ММБ-301-О-02",true));
//    }
    @Test
    public void testGetToday(){
//        System.out.println(RequestUtil.getOmsuGroupSchedule("ММБ-301-О-02",false).get(Utils.getIndexOfTodayInOmsuScheduleList("ММБ-301-О-02")).getDay());
        var ind = Utils.getIndexOfDayInOmsuScheduleList("ММБ-301-О-02",LocalDate.of(2025,9,23));
        System.out.println(RequestUtil.getOmsuGroupSchedule("ММБ-301-О-02",false).get(ind));
    }
//    @Test
//    public void getSchedule() {
//        System.out.println(RequestUtil.getOmsuGroupSchedule("ММБ-301-О-02", true));
//    }
//    @Test
//    public void equalsToday(){
//        assertFalse(RequestUtil.getOmsuGroupSchedule("ММБ-301-О-02",false)
//                .get(Utils.getIndexOfDayInOmsuScheduleList("ММБ-301-О-02",LocalDate.now()))
//                .getDay().equals(LocalDate.now()));
//    }
}
