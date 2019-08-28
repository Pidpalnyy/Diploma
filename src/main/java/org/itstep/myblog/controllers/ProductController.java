package org.itstep.myblog.controllers;

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
import org.itstep.myblog.services.ImageFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageFileService imageFileCreate;

    @PostMapping("/api/product/addProduct")
    public String addProduct(
            @RequestParam("page") MultipartFile file,
            String category,
            String name,
            String text,
            Double quantity,
            String selectQuantity,
            Double price,
            String selectPrice)
            throws IOException {
        Product product = new Product ();
        if (name.length () != 0) {
            product.setName (name);
        }
        if (price != null) {
            product.setPrice (price);
        }
        if (selectQuantity != null) {
            product.setSelectQuantity (selectQuantity);
        }
        if (selectQuantity != null) {
            product.setSelectPrice (selectPrice);
        }
        if (quantity != null) {
            product.setQuantity (quantity);
        }
        if (text.length () != 0) {
            product.setText (text);
        }
        if (file.isEmpty ()) {
            System.out.println (file);
        }
        else {
            product.setPage (imageFileCreate.fileCreate (file));
        }
        Category c = categoryRepository.getByName (category);
        product.setCategory (c);
        productRepository.save (product);
        return "redirect:/addProduct";
    }
    @PostMapping ("/api/product/productPost")
    @ResponseBody
    public GetProductsResponse postProduct(String categoryName)  {
        if (categoryName==null || categoryName.trim ().equals ("")){
            return new GetProductsResponse("error","No all params",null) ;
        }
        Category c = categoryRepository.getByName (categoryName);
        List<Product> products = c.getProducts ();
        List<ProductDTO> dtoList = products.stream ().map (ProductDTO::new).collect (Collectors.toList ());
        List<ProductDTOfoJson> dtOforJsonList = dtoList.stream ().map (ProductDTOfoJson::new).collect (Collectors.toList ());
        return new GetProductsResponse ("ok", "", dtOforJsonList);
    }
    @PostMapping ("/api/product/deleteProduct")
    @ResponseBody
    public StatusResponse deleteProduct(Long id) {
        if (id == null || id == 0) {
            return new StatusResponse ("error", "No all params");
        }
        Product product = productRepository.getOne (id);
        String path = product.getPage ();
        imageFileCreate.delImageFile(path);
        productRepository.deleteById (id);
        return new StatusResponse ("ok", "");
    }
}
