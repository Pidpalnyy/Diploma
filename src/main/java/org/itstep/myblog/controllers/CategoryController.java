package org.itstep.myblog.controllers;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.entities.Category;
import org.itstep.myblog.repository.CategoryRepository;
import org.itstep.myblog.repository.ProductRepository;
import org.itstep.myblog.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final String BASE_IMG_PATH = "images";

    @PostMapping("/api/category/addCategory")
    public String addCategory(
            String name,
            String menu,
            HttpSession session) {
        System.out.println (name + " " +menu);
        try {
            List<Category> categoryList = categoryRepository.findAll ();
            boolean t = false;
            for (Category c : categoryList){
                if (c.getName ().equals (name)) t = true;
            }
            System.out.println (t);
            if (t) throw new Exception ("Category already exists");

            Category ctry = new Category ();
            ctry.setName (name);
            ctry.setMenu (menu);
            categoryRepository.save (ctry);
//            return "redirect:/add";
        } catch (Exception e) {

            session.setAttribute ("error_register", e.getMessage ());
            session.setAttribute ("name_register", name);
//            return "redirect:/add";
        }
        return "redirect:/addCategory";
    }

    @PostMapping("/api/category/delCategory")
    public String delCategory(Long id,HttpSession session){

        categoryRepository.deleteById (id);


        return "redirect:/";
    }



}
