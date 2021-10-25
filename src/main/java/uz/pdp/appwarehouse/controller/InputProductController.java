package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputProductDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    @PostMapping
    public Result addInputProduct(@RequestBody InputProductDTO inputProductDTO){
        return inputProductService.addInputProduct(inputProductDTO);
    }

    @GetMapping
    public List<InputProduct> getInputProducts(){
        return inputProductService.getInputProducts();
    }

    @GetMapping("/{id}")
    public InputProduct getInputProduct(@PathVariable Integer id){
        return inputProductService.getInputProduct(id);
    }

    @PutMapping("/{id}")
    public Result editInputProduct(@PathVariable Integer id, @RequestBody InputProductDTO inputProductDTO){
        return inputProductService.editInputProduct(id, inputProductDTO );
    }

    @DeleteMapping("/{id}")
    public Result deleteInputProduct(@PathVariable Integer id){
        return inputProductService.deleteInputProduct(id);
    }
}
