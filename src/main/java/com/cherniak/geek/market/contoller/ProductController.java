package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.specification.ProductSpecification;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                               @RequestParam Map<String, String> params

//                               @RequestParam(name = "title", required = false) String titlePart,
//                               @RequestParam(name = "min_cost", required = false) Integer minCost,
//                               @RequestParam(name = "max_cost", required = false) Integer maxCost
    ) {

        page = page < 1 ? 1 : page;
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> products = productService.findAll(productFilter.getSpec(), page-1, size);
        model.addAttribute("products", products);
        model.addAttribute("filterDefinition", productFilter.getFilterDefinition());

//        model.addAttribute("page", page < 1 ? "" : page);
//        model.addAttribute("size", page < 1 ? "" : size);

//        if (page < 1) {
//            List<Product> products = productService.findAll();
//            model.addAttribute("products", products);
//        } else {
//            Page<Product> products = productService.findPage(page - 1, size);
//            model.addAttribute("products", products);
//        }
        return "products";
    }

//    @GetMapping("/{param}")
//    public String minMaxRequest(Model model, @PathVariable String param) {
//        List<Product> products;
//        switch (param) {
//            case "min":
//                products = productService.findAllMinCost();
//                break;
//            case "max":
//                products = productService.findAllMaxCost();
//                break;
//            case "minmax":
//                products = productService.findAllMinMaxCost();
//                break;
//            default:
//                throw new ResourceNotFoundException("Unknown PathVariable");
//        }
//        model.addAttribute("products", products);
//        return "products";
//    }

//    @GetMapping("/filter")
//    public String filterRequest(Model model, @RequestParam Integer min,
//                                @RequestParam Integer max) {
//
//        if (min == null || min < 0) {
//            min = 0;
//        }
//        model.addAttribute("min", min);
//        model.addAttribute("max", max);
//
//        if (max == null || max < min) {
//            model.addAttribute("max", "");
//            max = Integer.MAX_VALUE;
//        }
//        List<Product> products = productService.filterCost(min, max);
//        model.addAttribute("products", products);
//
//        return "products";
//    }
}




