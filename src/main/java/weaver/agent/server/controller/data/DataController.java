package weaver.agent.server.controller.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import weaver.agent.server.listener.SessionListener;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class DataController {
    @GetMapping(value = "/getOnlineAccount")
    @ResponseBody
    public int get(){
        return SessionListener.userCount.get();
    }
}
