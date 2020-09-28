package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String findProducts(Model model,
                               @RequestParam(defaultValue = "1", name = "page") Integer page,
                               @RequestParam(defaultValue = "5", required = false) Integer size,
                               @RequestParam Map<String, String> params) {

        page = page < 1 ? 1 : page;
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> products = productService.findAll(productFilter.getSpec(), page - 1, size);
        model.addAttribute("products", products);
        model.addAttribute("filterDefinition", productFilter.getFilterDefinition());
        return "products";
    }

    @PostMapping("/update_product")
    public String save(@RequestParam Long id, @RequestParam String title, @RequestParam int cost) {
        productService.save(new Product(id, title, cost));
        return "redirect:/products";
    }
}




