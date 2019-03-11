package edu.mum.mpp.coursework.mppsurvey.controller;

import edu.mum.mpp.coursework.mppsurvey.entity.AppUser;
import edu.mum.mpp.coursework.mppsurvey.entity.UserQuestionAnswer;
import edu.mum.mpp.coursework.mppsurvey.exception.ResourceNotFoundException;
import edu.mum.mpp.coursework.mppsurvey.model.UserIdentityAvailability;
import edu.mum.mpp.coursework.mppsurvey.model.UserProfile;
import edu.mum.mpp.coursework.mppsurvey.model.UserSummary;
import edu.mum.mpp.coursework.mppsurvey.repository.QuestionRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserQuestionAnswerRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserQuestionRatingRepository;
import edu.mum.mpp.coursework.mppsurvey.repository.UserRepository;
import edu.mum.mpp.coursework.mppsurvey.security.CurrentUser;
import edu.mum.mpp.coursework.mppsurvey.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    private UserQuestionRatingRepository userQuestionRatingRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getAuthorities().stream().map(e->((GrantedAuthority) e).getAuthority()).collect(Collectors.toList()));
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long surveyCount = userQuestionAnswerRepository.countByUser(user);
        long rating = userQuestionRatingRepository.countByUser(user);

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getCreatedAt(), surveyCount, rating);

        return userProfile;
    }
}
