package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.appwarehouse.entity.OutputProduct;
import uz.pdp.appwarehouse.payload.OutputProductDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.OutputProductRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;

@Service
public class OutputProductService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputProductRepository outputProductRepository;

    public Result addOutputProduct(OutputProductDTO outputProductDTO){
        if (!productRepository.existsById(outputProductDTO.getProductId()))
            return new Result("Bunday product mavjud emas", false);
        if (!outputRepository.existsById(outputProductDTO.getOutputId()))
            return new Result("Bunday output mavjud emas", false);
        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setProduct(productRepository.getById(outputProductDTO.getProductId()));
        outputProduct.setAmount(outputProductDTO.getAmount());
        outputProduct.setPrice(outputProductDTO.getPrice());
        outputProduct.setOutput(outputRepository.getById(outputProductDTO.getOutputId()));

        outputProductRepository.save(outputProduct);
        return new Result("OutputProduct successfully added", true);
    }

    public List<OutputProduct> getOutputProducts(){
        return outputProductRepository.findAll();
    }

    public OutputProduct getOutputProduct( Integer id){
        if (!outputProductRepository.existsById(id))
            return new OutputProduct();
        return outputProductRepository.getById(id);
    }

    public Result editOutputProduct( Integer id, OutputProductDTO outputProductDTO){
        if (!outputProductRepository.existsById(id))
            return new Result("No such OutputProduct with this id", false);
        if (!productRepository.existsById(outputProductDTO.getProductId()))
            return new Result("Bunday product mavjud emas", false);
        if (!outputRepository.existsById(outputProductDTO.getOutputId()))
            return new Result("Bunday output mavjud emas", false);
        OutputProduct editingOutputProduct = outputProductRepository.getById(id);
        editingOutputProduct.setProduct(productRepository.getById(outputProductDTO.getProductId()));
        editingOutputProduct.setAmount(outputProductDTO.getAmount());
        editingOutputProduct.setPrice(outputProductDTO.getPrice());
        editingOutputProduct.setOutput(outputRepository.getById(outputProductDTO.getOutputId()));
        outputProductRepository.save(editingOutputProduct);
        return new Result("Successfully edited", true);
    }

    public Result deleteOutputProduct(Integer id){
        if (!outputProductRepository.existsById(id))
            return new Result("No such OutputProduct with this id", false);
        outputProductRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
