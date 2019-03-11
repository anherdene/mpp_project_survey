package edu.mum.mpp.coursework.mppsurvey.controller;

import edu.mum.mpp.coursework.mppsurvey.model.UploadFileResponse;
import edu.mum.mpp.coursework.mppsurvey.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    /**
     * upload surveyname.cvs file
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info(fileName);
        if(file.getContentType()=="text/csv"){
            return new UploadFileResponse(fileName, 0,
                    file.getContentType(), file.getSize());
        }
        // save survey
        int questionCount = adminService.saveToSurvey(file);
        return new UploadFileResponse(fileName, questionCount,
                file.getContentType(), file.getSize());
    }


    /**
     *
     * @param surveyId
     * @param reportType
     * @return
     */
    @GetMapping("/generateReport")
    public ResponseEntity GetMapping(@RequestParam Long surveyId,@RequestParam Long reportType) {
        return ResponseEntity.ok(null);
    }
}