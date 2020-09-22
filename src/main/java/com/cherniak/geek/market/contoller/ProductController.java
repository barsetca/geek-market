package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
//    public String findAllProducts(Model model) {
//        List<Product> products = productService.findAll();
//        model.addAttribute("products", products);
//        return "products";
    public String findPageOfProducts(Model model, @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer size) {

        Long count = productService.getCount();
        model.addAttribute("count", count);
        model.addAttribute("page", page < 1 ? "" : page);
        model.addAttribute("size", page < 1 ? "" : size);

        if (page < 1) {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
        } else {
            Page<Product> products = productService.findPage(page - 1, size);
            model.addAttribute("products", products);
        }
        return "products";
    }

    @GetMapping("/{param}")
    public String secondRequest(Model model, @PathVariable String param) {
        List<Product> products;
        switch (param) {
            case "min":
                products = productService.findAllMinCost();
                break;
            case "max":
                products = productService.findAllMaxCost();
                break;
            case "minmax":
                products = productService.findAllMinMaxCost();
                break;
            default:
                throw new ResourceNotFoundException("Unknown PathVariable");
        }
        model.addAttribute("products", products);
        return "products";
    }

//    @GetMapping
//    public String findPageOfProducts(Model model, @RequestParam Integer page, @RequestParam Integer size) {
//
//        if (page == null || page < 1) {
//            return "redirect:/products";
//        } else {
//            Page<Product> products = productService.findPage(page - 1, size);
//            model.addAttribute("products", products);
//            return "products";
//        }
//    }

}


