package weaver.agent.server.controller.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.controller.CrudController;
import weaver.agent.server.service.interfase.ProviderService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author w
 * @date 2019-12-01 22:40
 */
@RequestMapping("/agent")
@Controller
public class ProviderController extends CrudController<BaseBean, ProviderService> {
    @RequestMapping(value = "/addEntity/{ip}",method = RequestMethod.GET)
    @ResponseBody
    public void addEntry(@PathVariable(value = "ip")String ip, HttpSession httpSession) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ip",ip);
        httpSession.setAttribute("ip",ip);
        //httpSession.setAttribute("startTime",df.format(new Date()));
        logger.debug("连接"+ip);
        service.addEntry(map);
    }

    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public void updateStatus(HttpSession httpSession){
        logger.debug("====================定时更新session状态ip: "+httpSession.getAttribute("ip")+"=====================");
    }

    @RequestMapping(value = "/setSqlDel")
    @ResponseBody
    public void setSqlDel(@RequestBody String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = mapper.readValue(jsonString, Map.class);
        logger.debug("SqlDelete语句");
        logger.debug("key========"+readValue.get("key"));
        logger.debug("sqlDel========"+readValue.get("sqlDel"));
        logger.debug("sqlExistDel========"+Boolean.parseBoolean(readValue.get("sqlExistDel").toString()));
        logger.debug("sdStack========"+readValue.get("sdStack"));
        service.setSqlDel(readValue);
    }

    @RequestMapping(value = "/setFileDel")
    @ResponseBody
    public void setFileDel(@RequestBody String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = mapper.readValue(jsonString, Map.class);
        if(readValue.get("sfStack").toString().contains("com.caucho.server.resin.Resin.main")){
            logger.debug(readValue.get("sfStack").toString());
            logger.debug(readValue.get("sfStackID").toString());
            logger.debug(readValue.get("sfStackName").toString());
            logger.debug(readValue.get("sfName").toString());
            logger.debug("启动时Resin main文件删除，不记录");
            return;
        }
        /*if(readValue.get("sfStack").toString().contains("com.caucho.env.thread2.ResinThread2")){
            logger.debug("启动时Resin task文件删除，不记录");
            return;
        }*/
        logger.debug("/-------文件删除--------");
        logger.debug("ip========"+readValue.get("ip"));
        logger.debug(readValue.get("sfStack").toString());
        logger.debug(readValue.get("sfStackID").toString());
        logger.debug(readValue.get("sfStackName").toString());
        logger.debug(readValue.get("sfName").toString());
        readValue.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        logger.debug("-------文件删除--------/");
        service.setfileDel(readValue);
    }
}
