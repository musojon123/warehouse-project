package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Input;
import uz.pdp.appwarehouse.payload.InputDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.InputRepository;
import uz.pdp.appwarehouse.repository.SupplierRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public Result addInput(InputDTO inputDTO){
        if (!warehouseRepository.existsById(inputDTO.getWarehouseId()))
            return new Result("Bunday warehouse mavjud emas", false);
        if (!supplierRepository.existsById(inputDTO.getSupplierId()))
            return new Result("Bunday supplier mavjud emas", false);
        if (!currencyRepository.existsById(inputDTO.getCurrencyId()))
            return new Result("Bunday currency mavjud emas", false);

        Input input = new Input();
        input.setDate(inputDTO.getDate());
        input.setWarehouse(warehouseRepository.getById(inputDTO.getCurrencyId()));
        input.setSupplier(supplierRepository.getById(inputDTO.getSupplierId()));
        input.setCurrency(currencyRepository.getById(inputDTO.getSupplierId()));
        input.setFactureNumber(inputDTO.getFactureNumber());
        //UUID generated code below we can change it later
        input.setCode(UUID.randomUUID().toString());

        inputRepository.save(input);
        return new Result("Input successfully added", true);
    }

    public List<Input> getInputs(){
        return inputRepository.findAll();
    }

    public Input getInput(Integer id){
        if (!inputRepository.existsById(id))
            return new Input();
        return inputRepository.getById(id);
    }

    public Result editInput(Integer id, InputDTO inputDTO){
        if (!inputRepository.existsById(id))
            return new Result("No such Input with this id", false);
        if (!warehouseRepository.existsById(inputDTO.getWarehouseId()))
            return new Result("Bunday warehouse mavjud emas", false);
        if (!supplierRepository.existsById(inputDTO.getSupplierId()))
            return new Result("Bunday supplier mavjud emas", false);
        if (!currencyRepository.existsById(inputDTO.getCurrencyId()))
            return new Result("Bunday currency mavjud emas", false);

        Input editingInput = inputRepository.getById(id);
        editingInput.setDate(inputDTO.getDate());
        editingInput.setWarehouse(warehouseRepository.getById(inputDTO.getWarehouseId()));
        editingInput.setSupplier(supplierRepository.getById(inputDTO.getSupplierId()));
        editingInput.setCurrency(currencyRepository.getById(inputDTO.getCurrencyId()));
        editingInput.setFactureNumber(inputDTO.getFactureNumber());
        inputRepository.save(editingInput);
        return new Result("Successfully edited", true);
    }

    public Result deleteInput(Integer id){
        if (!inputRepository.existsById(id))
            return new Result("No such Input with this id", false);
        inputRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
