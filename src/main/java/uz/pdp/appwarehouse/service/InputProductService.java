package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.InputProduct;
import uz.pdp.appwarehouse.payload.InputProductDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.InputProductRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;

@Service
public class InputProductService {
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;

    public Result addInputProduct(InputProductDTO inputProductDTO){
        if (!productRepository.existsById(inputProductDTO.getInputId()))
            return new Result("Bunday product mavjud emas", false);
        if (!inputRepository.existsById(inputProductDTO.getInputId()))
            return new Result("Bunday input mavjud emas", false);

        InputProduct inputProduct = new InputProduct();
        inputProduct.setProduct(productRepository.getById(inputProductDTO.getProductId()));
        inputProduct.setAmount(inputProductDTO.getAmount());
        inputProduct.setPrice(inputProductDTO.getPrice());
        inputProduct.setExpireDate(inputProductDTO.getExpireDate());
        inputProduct.setInput(inputRepository.getById(inputProductDTO.getProductId()));

        inputProductRepository.save(inputProduct);
        return new Result("InputProduct successfully added", false);
    }

    public List<InputProduct> getInputProducts(){
        return inputProductRepository.findAll();
    }

    public InputProduct getInputProduct(Integer id){
        if (!inputProductRepository.existsById(id))
            return new InputProduct();
        return inputProductRepository.getById(id);
    }

    public Result editInputProduct(Integer id, InputProductDTO inputProductDTO){
        if (!inputProductRepository.existsById(id))
            return new Result("No such InputProduct", false);
        InputProduct editingInputProduct = inputProductRepository.getById(id);
        editingInputProduct.setExpireDate(inputProductDTO.getExpireDate());
        editingInputProduct.setProduct(productRepository.getById(inputProductDTO.getProductId()));
        editingInputProduct.setAmount(inputProductDTO.getAmount());
        editingInputProduct.setPrice(inputProductDTO.getPrice());
        editingInputProduct.setInput(inputRepository.getById(inputProductDTO.getInputId()));
        inputProductRepository.save(editingInputProduct);
        return new Result("Successfully edited", true);
    }

    public Result deleteInputProduct(Integer id){
        if (!inputProductRepository.existsById(id))
            return new Result("No such InputProduct", false);
        inputProductRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
