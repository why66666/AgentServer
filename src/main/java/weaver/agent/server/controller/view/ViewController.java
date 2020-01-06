package weaver.agent.server.controller.view;

import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.controller.CrudController;
import weaver.agent.server.listener.SessionListener;
import weaver.agent.server.service.interfase.ProviderService;

import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
public class ViewController extends CrudController<BaseBean, ProviderService> {
    @RequestMapping("/index.html")
    public String index(Model model){
        model.addAttribute("sessionCount", SessionListener.userCount);
        return "index";
    }
    @RequestMapping("/testlist.html")
    public String testlist(Model model){
        List<Map> mapList = service.getList();
        model.addAttribute("list",mapList);
        return "testlist";
    }
    @RequestMapping(value = "/testlist/fdcontent/{ip}",method = RequestMethod.GET)
    public String fdcontent(@PathVariable(value = "ip")String ip,Model model){
        model.addAttribute("fdMap",service.getFDMap(ip));
        return "fdlist";
    }
    @RequestMapping(value = "/fdlist/{ip}",method = RequestMethod.GET)
    public String fdlist(@PathVariable(value = "ip")String ip,Model model){
        model.addAttribute("fdmap",service.getFDMap(ip));
        return "fdlist";
    }

}
