package documents.service.service;

import documents.dao.UserDao;
import documents.dto.user.UserDto;
import documents.jpa.exceprions.ConstraintsException;
import documents.jpa.exceprions.IdNotFoundException;
import documents.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDaoJpa;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(s).orElseThrow(IdNotFoundException::new);
    }

    public UserDto addNewUser(UserDto userDto){
        if (userDto.getPassword().length() < 6 || userDto.getPassword().length() > 20) {
            throw new ConstraintsException();
        }
        return userDaoJpa.addNewUser(userDto);
    }

    public UserDto getCurrentUser(){
        return userDaoJpa.getCurrentUser();
    }

    public UserDto modifyUser(UserDto userDto) {
        return userDaoJpa.modifyUser(userDto);
    }

}
