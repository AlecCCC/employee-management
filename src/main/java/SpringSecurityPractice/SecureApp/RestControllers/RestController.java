package SpringSecurityPractice.SecureApp.RestControllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController


@RequestMapping("/security-practice")
public class RestController {

    @GetMapping("/hello")
    public String helloEndpoint(@AuthenticationPrincipal UserDetails user, @RequestParam(required = false) String name) {

        if (user == null) {
            return "Hello Guest" ;
        }

        return "Hello " + user.getUsername();
    }

}
