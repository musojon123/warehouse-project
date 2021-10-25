package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.MeasurementRepository;

import java.util.List;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result addMeasurement(Measurement measurement){
        boolean existByName = measurementRepository.existsByName(measurement.getName());
        if (existByName)
            return new Result("Bunday o'chov birligi mavjud", false);
        measurementRepository.save(measurement);
        return new Result("Successfully added measurement", true);
    }

    public List<Measurement> getMeasurements(){
        return measurementRepository.findAll();
    }

    public Measurement getMeasurement(Integer id){
        if (!measurementRepository.existsById(id))
            return new Measurement();
        return measurementRepository.getById(id);
    }

    public Result editMeasurement(Integer id,  Measurement measurement){
        if (!measurementRepository.existsById(id))
            return new Result("No such measurement with this id", false, id);

        Measurement editingMeasurement = measurementRepository.getById(id);
        editingMeasurement.setName(measurement.getName());
        measurementRepository.save(editingMeasurement);
        return new Result("Successfully edited", true);
    }

    public Result deleteMeasurement(Integer id){
        if (!measurementRepository.existsById(id))
            return new Result("No such measurement with this id", false, id);
        measurementRepository.deleteById(id);
        return new Result("Measurement successfully deleted", true, id);
    }



}
