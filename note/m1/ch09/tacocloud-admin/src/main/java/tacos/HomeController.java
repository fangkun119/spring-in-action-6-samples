package tacos;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home(@RegisteredOAuth2AuthorizedClient("taco-admin-client")
  OAuth2AuthorizedClient authorizedClient) {
    String tokenValue = authorizedClient.getAccessToken().getTokenValue();
    System.out.println("--------_ACCESS TOKEN--------->  " + tokenValue);
    return "home";
  }
  
}
