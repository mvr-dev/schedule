import com.fasterxml.jackson.core.JsonProcessingException;
import dev.mvr.schedule.utils.RequestUtil;
import dev.mvr.schedule.utils.Utils;
import org.junit.Test;

import java.time.LocalDate;

import static dev.mvr.schedule.utils.Utils.getOmstuGroup;
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
    @Test
    public void testOmstu(){
        System.out.println(RequestUtil.getOmstuLessons("ПРД-231",LocalDate.now()));
    }
    @Test
    public void yyyy(){
        LocalDate begin = LocalDate.now();
        System.out.println(String.format("https://rasp.omgtu.ru/api/schedule/group/%d?start=%d.%02d.%02d&finish=%d.%02d.%02d",
                RequestUtil.getOmstuGroup("ПРД-231").getId(),
                begin.getYear(),
                begin.getMonthValue(),
                begin.getDayOfMonth(),
                begin.plusDays(7).getYear(),
                begin.plusDays(7).getMonthValue(),
                begin.plusDays(7).getDayOfMonth()
        ));
    }
}
