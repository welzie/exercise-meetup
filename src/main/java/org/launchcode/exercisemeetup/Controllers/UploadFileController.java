package org.launchcode.exercisemeetup.Controllers;


import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.FileRepository;
import org.launchcode.exercisemeetup.Models.forms.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;

@RestController
public class UploadFileController extends AbstractController {

    @Autowired
    FileRepository fileRepository;

    /*
     * MultipartFile Upload
     */
    @PostMapping("/api/file/upload")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, @RequestParam String username, HttpSession httpSession) {
        User user = userDao.findByUsername(username);
        if (getUserFromSession(httpSession) != null) {
            String usernameCheck = getUserFromSession(httpSession).getUsername();
            if (username.equals(usernameCheck)) {

                try {
                    // save file to SQL
                    FileModel filemode = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                    filemode.setUser(getUserFromSession(httpSession));
                    fileRepository.save(filemode);
                    return "File uploaded successfully! -> filename = " + file.getOriginalFilename();
                } catch (Exception e) {
                    return "FAIL! Maybe You had uploaded the file before or the file's size > 500KB";

                }

            }

        }
        return "redirect:profile";
    }

    @GetMapping(value ="/api/file/upload", produces ={"application/json"})
    public @ResponseBody HashMap<String,Object> uploadfile (MultipartHttpServletRequest request,
        HttpServletResponse response) throws Exception {

        MultipartFile multipartFile = request.getFile("uploadfile");
        Long size = multipartFile.getSize();
        String contentType = multipartFile.getContentType();
        InputStream stream = multipartFile.getInputStream();
        byte[] bytes = multipartFile.getBytes();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fileoriginalsize", size);
        map.put("contenttype", contentType);
        map.put("base64", new String(Base64Utils.encode(bytes)));

        return map;
        }
}







