package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Category;
import uz.pdp.appwarehouse.payload.CategoryDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Result addCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        if (categoryDTO.getParentCategoryId() !=null){
            Optional<Category> optionalParent = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (optionalParent.isEmpty())
                return new Result("Bunday parent category mavjud emas!", false);
            category.setParentCategory(optionalParent.get());
        }
        categoryRepository.save(category);
        return new Result("Successfully saved!", true);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer id){
        if (!categoryRepository.existsById(id))
            return new Category();
        return categoryRepository.getById(id);
    }

    public Result editCategory(CategoryDTO categoryDTO, Integer id){
        if (!categoryRepository.existsById(id))
            return new Result("No such category with this id", false, id);

        Category category = new Category();
        category.setName(categoryDTO.getName());
        if (categoryDTO.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (optionalCategory.isEmpty())
                return new Result("Bunday parent category mavjud emas", false, categoryDTO.getParentCategoryId());
            category.setParentCategory(categoryRepository.getById(categoryDTO.getParentCategoryId()));
        }
        categoryRepository.save(category);
        return new Result("Category successfully edited", true, id);
    }

    public Result deleteCategory(Integer id){
        if (categoryRepository.existsById(id))
            return new Result("No such category with this id", false, id);
        categoryRepository.deleteById(id);
        return new Result("Category successfully deleted", true, id);
    }
}
