package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;

    @PostMapping
    public Result addOutputProduct(@RequestBody OutputProductDTO outputProductDTO){
        return outputProductService.addOutputProduct(outputProductDTO);
    }

    @GetMapping
    public List<OutputProduct> getOutputProducts(){
        return outputProductService.getOutputProducts();
    }

    @GetMapping("/{id}")
    public OutputProduct getOutputProduct(@PathVariable Integer id){
        return outputProductService.getOutputProduct(id);
    }

    @PutMapping("/{id}")
    public Result editOutputProduct(@PathVariable Integer id, @RequestBody OutputProductDTO outputProductDTO){
        return outputProductService.editOutputProduct(id, outputProductDTO);
    }

    @DeleteMapping("/{id}")
    public Result deleteOutputProduct(@PathVariable Integer id){
        return outputProductService.deleteOutputProduct(id);
    }


}
