package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result addWarehouse(Warehouse warehouse){
        if (warehouseRepository.existsByName(warehouse.getName()))
            return new Result("Already exist with such name", false);

        warehouseRepository.save(warehouse);
        return new Result("Warehouse successfully added", true);
    }

    public List<Warehouse> getWarehouses(){
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouse(Integer id){
        if (!warehouseRepository.existsById(id))
            return new Warehouse();
        return warehouseRepository.getById(id);
    }

    public Result editWarehouse(Integer id, Warehouse warehouse){
        if (!warehouseRepository.existsById(id))
            return new Result("No such Warehouse with this id", false);
        Warehouse editingWarehouse = warehouseRepository.getById(id);
        editingWarehouse.setName(warehouse.getName());
        warehouseRepository.save(editingWarehouse);
        return new Result("Successfully edited",  true);
    }

    public Result deleteWarehouse(Integer id){
        if (!warehouseRepository.existsById(id))
            return new Result("No such Warehouse with this id", false);
        warehouseRepository.deleteById(id);
        return new Result("Successfully edited",  true);
    }
}
