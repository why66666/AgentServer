package weaver.agent.server.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.controller.CrudController;
import weaver.agent.server.service.interfase.ProviderService;

import java.util.List;
import java.util.Map;

@RequestMapping("/checkitem")
@Controller
public class ViewController extends CrudController<BaseBean, ProviderService> {
    @RequestMapping("/sflist.html")
    public String testlist(Model model){
        List<Map> mapList = service.getList();
        model.addAttribute("list",mapList);
        return "sflist";
    }
    @RequestMapping(value = "/sflist/fdlist/{ip}",method = RequestMethod.GET)
    public String fdlist(@PathVariable(value = "ip")String ip,Model model){
        model.addAttribute("fdmap",service.getFDMap(ip));
        return "fdlist";
    }

}
