package edu.mum.mpp.coursework.mppsurvey.service;

import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public ResponseEntity create(AppUser user){

//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(null);
    }
}
