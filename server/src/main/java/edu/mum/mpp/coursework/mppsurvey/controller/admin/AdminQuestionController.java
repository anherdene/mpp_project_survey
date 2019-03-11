package edu.mum.mpp.coursework.mppsurvey.controller.admin;

import edu.mum.mpp.coursework.mppsurvey.entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/questions")
@PreAuthorize("hasRole('ADMIN')")
public class AdminQuestionController extends RestCrudController<Question> {
    private static final Logger logger = LoggerFactory.getLogger(AdminQuestionController.class);
}
