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
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();
        model.addAttribute ("auth", !auth.getName ().equals ("anonymousUser"));
        if (!auth.getName ().equals ("anonymousUser")) {
            model.addAttribute ("name", auth.getName ());
        }
        model.addAttribute ("users", usersRepository.findAll ());
        model.addAttribute("categoryMenu", categoryRepository.getByMenu ("menu"));
        model.addAttribute("categoryChildrensMenu", categoryRepository.getByMenu ("childrensMenu"));
        model.addAttribute("images", imageRepository.findAll());
        return "main";
    }

    @GetMapping("/register")
    public String register(HttpSession session, Model model) {
        String error = (String) session.getAttribute ("error_register");
        String name = (String) session.getAttribute ("name_register");
        session.removeAttribute ("error_register");
        session.removeAttribute ("name_register");
        model.addAttribute ("error", error);
        model.addAttribute ("name", name);
        return "register";
    }

    @PostMapping("/register")
    public String registerHandle(String name, String pass, String pass2, HttpSession session) {
        try {
            User u = usersRepository.getByName (name);
            if (u != null) throw new Exception ("User already exists");
            if (!pass.equals (pass2)) throw new Exception ("Pass error");
            User user = new User ();
            user.setName (name);
            user.setPass (passwordEncoder.encode (pass));
            usersRepository.save (user);
            return "redirect:/login";
        } catch (Exception e) {
            session.setAttribute ("error_register", e.getMessage ());
            session.setAttribute ("name_register", name);
            return "redirect:/register";
        }
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
