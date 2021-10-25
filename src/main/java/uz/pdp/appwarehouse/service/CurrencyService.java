package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Currency;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    public Result addCurrency(Currency currency){
        if (!currencyRepository.existsByName(currency.getName()))
            return new Result("Bunday valyuta allaqachon bor", false);

        currencyRepository.save(currency);
        return new Result("Currency successfully added", true);
    }

    public List<Currency> getCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency getCurrency(Integer id){
        if (!currencyRepository.existsById(id))
            return new Currency();

        return currencyRepository.getById(id);
    }

    public Result editCurrency(Integer id, Currency currency){
        if (!currencyRepository.existsById(id))
            return new Result("No such Currency with this id", false);
        Currency editingCurrency = currencyRepository.getById(id);
        editingCurrency.setName(currency.getName());
        currencyRepository.save(editingCurrency);
        return new Result("Successfully edited", true);
    }

    public Result deleteCurrency(Integer id){
        if (!currencyRepository.existsById(id))
            return new Result("No such Currency with this id", false);
        currencyRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
