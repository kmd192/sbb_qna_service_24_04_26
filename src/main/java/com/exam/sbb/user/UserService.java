package com.exam.sbb.user;

import com.exam.sbb.DataNotFoundException;
import com.exam.sbb.SignupEmailDuplicatedExcetion;
import com.exam.sbb.SignupUsernameDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    // 스프링이 책임지고 PasswordEncoder 타입의 객체를 만들어야하는 상황
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));

        try{
            userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            if(userRepository.existsByUsername(username)){
                throw new SignupUsernameDuplicatedException("이미 사용중인 username입니다.");
            } else {
                throw new SignupEmailDuplicatedExcetion("이미 사용중인 email입니다.");
            }
        }

        return user;
    }

    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new DataNotFoundException("siteuser not found"));

        /*Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }*/
    }

}
