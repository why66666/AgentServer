package weaver.agent.server.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author w
 * @date 2020-01-03 18:18
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String index(){
        return "forward:/admin/index.html";
    }
}
