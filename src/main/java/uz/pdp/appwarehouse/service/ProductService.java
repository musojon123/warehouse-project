package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.entity.Product;
import uz.pdp.appwarehouse.payload.ProductDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentRepository;
import uz.pdp.appwarehouse.repository.CategoryRepository;
import uz.pdp.appwarehouse.repository.MeasurementRepository;
import uz.pdp.appwarehouse.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    MeasurementRepository measurementRepository;


    public Result addProduct(ProductDTO productDTO){
        boolean existsProduct = productRepository.existsByNameAndCategoryId(productDTO.getName(), productDTO.getCategoryId());
        if (existsProduct)
            return new Result("Bunday maxsulot mavjud", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya mavjud emas", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getCategoryId());
        if (!optionalAttachment.isPresent())
            return new Result("Bunday photo mavjud emas", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDTO.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday measurement mavjud emas", false);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCode("1"); //todo generation qilish kerak
        product.setCategory(categoryRepository.getById(productDTO.getCategoryId()));
        product.setPhoto(attachmentRepository.getById(productDTO.getPhotoId()));
        product.setMeasurement(measurementRepository.getById(productDTO.getMeasurementId()));
        //UUID generated code below we can change it later
        product.setCode(UUID.randomUUID().toString());
        productRepository.save(product);
        return new Result("Mahsulot saqlandi", true);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Integer id){
        if (!productRepository.existsById(id))
            return new Product();
        return productRepository.getById(id);
    }

    public Result editProduct(Integer id, ProductDTO productDTO){
        if (!productRepository.existsById(id))
            return new Result("No such product with this id", false);

        if (!categoryRepository.existsById(productDTO.getCategoryId()))
            return new Result("Bunday kategoriya mavjud emas", false);

        if (!attachmentRepository.existsById(productDTO.getPhotoId()))
            return new Result("Bunday photo mavjud emas", false);

        if (!measurementRepository.existsById(productDTO.getMeasurementId()))
            return new Result("Bunday measurement mavjud emas", false);

        Product editingProduct = productRepository.getById(id);
        editingProduct.setName(productDTO.getName());
        editingProduct.setMeasurement(measurementRepository.getById(productDTO.getMeasurementId()));
        editingProduct.setPhoto(attachmentRepository.getById(productDTO.getPhotoId()));
        editingProduct.setCategory(categoryRepository.getById(productDTO.getCategoryId()));
        //UUID generated code below we can change it later
        //editingProduct.setCode(UUID.randomUUID().toString());
        productRepository.save(editingProduct);
        return new Result("Successfully edited", true);
    }

    public Result deleteProduct(Integer id){
        if (!productRepository.existsById(id))
            return new Result("No such product with this id", false);
        productRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
