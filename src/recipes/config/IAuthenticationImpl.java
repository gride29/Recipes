package recipes.config;

import org.springframework.security.core.Authentication;

public interface IAuthenticationImpl {

    Authentication getAuthentication();
}
