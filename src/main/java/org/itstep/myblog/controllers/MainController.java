package org.itstep.myblog.controllers;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.entities.User;
import org.itstep.myblog.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        model.addAttribute ("auth", auth.getName ().equals ("admin"));
        if (!auth.getName ().equals ("anonymousUser")) {
            model.addAttribute ("name", auth.getName ());
        }
        model.addAttribute ("users", usersRepository.findAll ());
        model.addAttribute("categoryMenu", categoryRepository.getByMenu ("menu"));
        model.addAttribute("categoryChildrensMenu", categoryRepository.getByMenu ("childrensMenu"));
        model.addAttribute("images", imageRepository.findAll());
        return "main";
    }
    @GetMapping("/updatePassword")
    public String register(HttpSession session, Model model) {
        String error = (String) session.getAttribute ("error_register");
        String name = (String) session.getAttribute ("name_register");
        session.removeAttribute ("error_register");
        session.removeAttribute ("name_register");
        model.addAttribute ("error", error);
        model.addAttribute ("name", name);
        return "updatePassword";
    }
    @PostMapping("/updatePasswordPost")
    public String registerHandle(String oldPass, String newPass,String newPass2, HttpSession session, Model model) {
        String new_pass = newPass;
        String new_pass2 = newPass2;
        String old_pass = oldPass;
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        if (auth.getName ().equals ("admin")){
            User u = usersRepository.getByName ("admin");
            if(!passwordEncoder.matches (old_pass,u.getPass ())){
                String error = (String)session.getAttribute("error");
                model.addAttribute ("error", error);
                return "redirect:/updatePassword";
            }
            if (new_pass.equals (new_pass2)){
                u.setPass (passwordEncoder.encode (new_pass));
                usersRepository.save (u);
            }
        }
        return "redirect:/updatePassword";
    }
    @GetMapping("/login")
    public String login(String error, Model model) {
        model.addAttribute ("errors", error);
        return "login";
    }
    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        model.addAttribute("categoryes", categoryRepository.findAll());
        return "addCategory";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("categoryes", categoryRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("yes",categoryRepository.findAll().size ()>0);
        return "addProduct";
    }

    @GetMapping("/addImages")
    public String addImages(HttpSession session, Model model) {
        String error = (String)session.getAttribute("error");
        session.removeAttribute("error");
        model.addAttribute("error",error);
        model.addAttribute("images", imageRepository.findAll());
        return "addImages";
    }
}
