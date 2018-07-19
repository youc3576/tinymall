import com.fasterxml.jackson.databind.ObjectMapper;
import com.menethil.tinymall.core.express.ExpressService;
import com.menethil.tinymall.core.express.dao.ExpressInfo;
import com.menethil.tinymall.wx.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExpressTest {

    @Test
    public void test() {
        ExpressService expressService = new ExpressService();
        String result = null;
        try {
            result = expressService.getOrderTracesByJson("YTO", "800669400640887922");
            ObjectMapper objMap = new ObjectMapper();
            ExpressInfo ei = objMap.readValue(result, ExpressInfo.class);
            ei.getTraces();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(result);
    }
}
