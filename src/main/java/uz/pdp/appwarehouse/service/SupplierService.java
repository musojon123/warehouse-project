package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Supplier;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.SupplierRepository;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Result addSupplier(Supplier supplier){
        if (supplierRepository.existsByPhoneNumber(supplier.getPhoneNumber()))
            return new Result("Bunday raqamdagi supplier mavjud", false);
        supplierRepository.save(supplier);
        return new Result("Supplier successfully saved", true);
    }

    public List<Supplier> getSuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier getSupplier(Integer id){
        if (!supplierRepository.existsById(id))
            return new Supplier();
        return supplierRepository.getById(id);
    }

    public Result editSupplier(Integer id, Supplier supplier){
        if (!supplierRepository.existsById(id))
            return new Result("No such Supplier with this id", false);
        Supplier editingSupplier = supplierRepository.getById(id);
        editingSupplier.setName(supplier.getName());
        editingSupplier.setPhoneNumber(supplier.getPhoneNumber());
        supplierRepository.save(editingSupplier);
        return new Result("Successfully edited",  true);
    }

    public Result deleteSupplier(Integer id){
        if (!supplierRepository.existsById(id))
            return new Result("No such Supplier with this id", false);
        supplierRepository.deleteById(id);
        return new Result("Successfully edited",  true);
    }

}
