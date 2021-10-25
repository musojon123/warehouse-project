package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Output;
import uz.pdp.appwarehouse.payload.OutputDTO;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;
import uz.pdp.appwarehouse.repository.CurrencyRepository;
import uz.pdp.appwarehouse.repository.OutputRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ClientRepository clientRepository;

    public Result addOutput(OutputDTO outputDTO){
        if (!warehouseRepository.existsById(outputDTO.getWarehouseId()))
            return new Result("Bunday warehouse mavjud emas", false);
        if (!currencyRepository.existsById(outputDTO.getCurrencyId()))
            return new Result("Bunday currency mavjud emas", false  );

        Output output = new Output();
        output.setDate(outputDTO.getDate());
        output.setWarehouse(warehouseRepository.getById(outputDTO.getWarehouseId()));
        output.setCurrency(currencyRepository.getById(outputDTO.getCurrencyId()));
        output.setFactureNumber(outputDTO.getFactureNumber());
        output.setClient(clientRepository.getById(outputDTO.getClientId()));
        //UUID generated code below we can change it later
        output.setCode(UUID.randomUUID().toString());

        outputRepository.save(output);
        return new Result("Output successfully ", true);
    }

    public List<Output> getOutputs(){
        return outputRepository.findAll();
    }

    public Output getOutput(Integer id){
        if (!outputRepository.existsById(id))
            return new Output();
        return outputRepository.getById(id);
    }

    public Result editOutput(Integer id, OutputDTO outputDTO){
        if (!outputRepository.existsById(id))
            return new Result("No such Output with this id", false);
        Output editingOutput = outputRepository.getById(id);
        editingOutput.setDate(outputDTO.getDate());
        editingOutput.setWarehouse(warehouseRepository.getById(outputDTO.getWarehouseId()));
        editingOutput.setCurrency(currencyRepository.getById(outputDTO.getCurrencyId()));
        editingOutput.setFactureNumber(outputDTO.getFactureNumber());
        editingOutput.setClient(clientRepository.getById(outputDTO.getClientId()));

        outputRepository.save(editingOutput);
        return new Result("Successfully edited",  true);
    }

    public Result deleteOutput(Integer id){
        if (!outputRepository.existsById(id))
            return new Result("No such Output with this id", false);
        outputRepository.deleteById(id);
        return new Result("Successfully edited",  true);
    }
}
