package org.itstep.myblog.controllers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.itstep.myblog.dto.GetProductsResponse;
import org.itstep.myblog.dto.ProductDTO;
import org.itstep.myblog.entities.Category;
import org.itstep.myblog.entities.Product;
import org.itstep.myblog.entities.User;
import org.itstep.myblog.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final String BASE_IMG_PATH = "src/main/resources/static/css/page";


    @GetMapping("")
    public String index(Model model) throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();

        model.addAttribute ("auth", !auth.getName ().equals ("anonymousUser"));
        if (!auth.getName ().equals ("anonymousUser")) {
            model.addAttribute ("name", auth.getName ());
        }

        model.addAttribute ("users", usersRepository.findAll ());
        model.addAttribute("categoryMenu", categoryRepository.getByMenu ("menu"));
        model.addAttribute("categoryChildrensMenu", categoryRepository.getByMenu ("childrensMenu"));
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("images", imageRepository.findAll());

//        List<Product> products = productRepository.findAll ();
//        List<ProductDTO> dtoList = products.stream ().map(p->new ProductDTO ()).collect(Collectors.toList());
//        System.out.println (dtoList);

//        List<Product> products = productRepository.findAll ();
//        for (Product p : products) System.out.println (p);


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
