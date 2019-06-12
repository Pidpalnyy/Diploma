package org.itstep.myblog.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.itstep.myblog.dto.GetProductsResponse;
import org.itstep.myblog.dto.ProductDTO;
import org.itstep.myblog.dto.ProductDTOfoJson;
import org.itstep.myblog.dto.StatusResponse;
import org.itstep.myblog.entities.Category;
import org.itstep.myblog.entities.Product;
import org.itstep.myblog.repository.CategoryRepository;
import org.itstep.myblog.repository.ProductRepository;
import org.itstep.myblog.repository.UsersRepository;
import org.itstep.myblog.services.ImageFileCreate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final UsersRepository usersRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageFileCreate imageFileCreate;
    Gson gson = new Gson ();

    @PostMapping("/api/product/addProduct")
    public String addProduct(
            @RequestParam("page") MultipartFile file,
            String category,
            String name,
            String text,
            Double quantity,
            Double price,
            HttpSession session)
            throws IOException {
        Product product = new Product ();
        if (name.length () != 0) {
            product.setName (name);
        }
        if (price != null) {
            product.setPrice (price);
        }
        if (quantity != null) {
            product.setQuantity (quantity);
        }
        if (text.length () != 0) {
            product.setText (text);
        }
        if (file.isEmpty ()) {
            System.out.println (file);
        } else {
            product.setPage (imageFileCreate.fileCreate (file));
        }
        Category c = categoryRepository.getByName (category);
        product.setCategory (c);
        productRepository.save (product);
        return "redirect:/addProduct";
    }

    @PostMapping ("/api/product/productPost")
    public void postProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String categoryName = req.getParameter ("categoryName");
        System.out.println (categoryName);
        if (categoryName==null || categoryName.trim ().equals ("")){
            resp.getWriter ().write (gson.toJson (new StatusResponse ("error","No all params")));
            return;
        }
        Category c = categoryRepository.getByName (categoryName);
        List<Product> products = c.getProducts ();
        List<ProductDTO> dtoList = products.stream ().map (ProductDTO::new).collect (Collectors.toList ());
        List<ProductDTOfoJson> dtOforJsonList = dtoList.stream ().map (ProductDTOfoJson::new).collect (Collectors.toList ());
        GetProductsResponse respons = new GetProductsResponse ("ok", "", dtOforJsonList);
        resp.getWriter ().write (gson.toJson (respons));
    }
}
