package softuni.springintro2.services;

import java.util.List;

public interface UserService {

    List<String> usersByEmailProvider();

    String removeInactiveUsers();
}
