package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.domain.Credential;
import com.udacity.jwdnd.course1.cloudstorage.domain.Note;
import com.udacity.jwdnd.course1.cloudstorage.domain.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserMapper userMapper;

    public CredentialController(CredentialService credentialService, UserMapper userMapper) {
        this.credentialService = credentialService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getCredentials(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = this.userMapper.getUser(username);

        List<Credential> credentialList = credentialService.getAllCredentials(user);
        model.addAttribute("credentialList", credentialList);
        model.addAttribute("noteUpload", new Note());
        model.addAttribute("credentialUpload", new Credential());

        return "home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("result", "Credential deleted successfully.");
        return "redirect:/home";
    }

    @PostMapping
    public String handleCredentialUpload(Credential credentialUpload, Authentication authentication, Model model, RedirectAttributes redirectAttributes){

        String username = authentication.getName();
        Integer userId = userMapper.getUser(username).getUserId();

        credentialUpload.setUserId(userId);

        if (credentialUpload.getCredentialid() != null){
            credentialService.updateCredential(credentialUpload);
            redirectAttributes.addFlashAttribute("result", "Credential updated successfully.");
        }
        else {
            credentialService.addCredential(credentialUpload);
            redirectAttributes.addFlashAttribute("result", "Credential created successfully.");
            model.addAttribute("credentialList", credentialUpload);
        }

        model.addAttribute("credential", credentialUpload);

        return "redirect:/home";

    }
}
