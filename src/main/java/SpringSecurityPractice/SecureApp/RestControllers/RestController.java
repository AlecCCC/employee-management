package SpringSecurityPractice.SecureApp.RestControllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController


@RequestMapping("/security-practice")
public class RestController {

    @GetMapping("/hello")
    public String helloEndpoint(@RequestParam(required = false) String name) {

        if (name == null) {
            return "Hello Guest" ;
        }

        return "Hello " + name;
    }

}
