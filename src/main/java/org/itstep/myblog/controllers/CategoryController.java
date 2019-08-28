package org.itstep.myblog.controllers;

import lombok.RequiredArgsConstructor;
import org.itstep.myblog.dto.*;
import org.itstep.myblog.entities.Category;
import org.itstep.myblog.entities.Product;
import org.itstep.myblog.repository.CategoryRepository;
import org.itstep.myblog.repository.ProductRepository;
import org.itstep.myblog.services.ImageFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageFileService imageFileService;

    @GetMapping("/api/category/getAllCategory")
    @ResponseBody
    public GetCategoryResponse getAllCategory()  {
        List<Category> categoryList = categoryRepository.findAll ();
        List<CategoryNameDTO> dtoList = categoryList.stream ().map (CategoryNameDTO::new).collect (Collectors.toList ());
        return new GetCategoryResponse ("ok","",dtoList);
    }
    @PostMapping("/api/category/addCategory")
    public String addCategory(
            String name,
            String menu,
            HttpSession session) {
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
        } catch (Exception e) {
            session.setAttribute ("error_register", e.getMessage ());
            session.setAttribute ("name_register", name);
        }
        return "redirect:/addCategory";
    }
    @PostMapping("/api/category/delCategory")
    @ResponseBody
    public StatusResponse deleteCategory(Long id) {
        if (id == null || id == 0) {
            return new StatusResponse ("error", "No all params");
        }
        System.out.println (id);
        Category category = categoryRepository.getById(id);
        List<Product> products = category.getProducts ();
        for (Product p : products){
            System.out.println (p.getName ());
            String path = p.getPage ();
            imageFileService.delImageFile(path);
            long ind = p.getId ();
            productRepository.deleteById (ind);
        }
        categoryRepository.deleteById (id);
        return new StatusResponse ("ok", "");
    }
}
