package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Result addClient(Client client){
        if (clientRepository.existsByPhoneNumber(client.getPhoneNumber()))
            return new Result("Bunday raqam allaqachon mavjud", false);

        clientRepository.save(client);
        return new Result("Client successfully added", true);
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public Client getClient(Integer id){
        if (!clientRepository.existsById(id))
            return new Client();
        return clientRepository.getById(id);
    }

    public Result editClient(Integer id, Client client){
        if (!clientRepository.existsById(id))
            return new Result("No such Client with this id", false);
        Client editingClient = clientRepository.getById(id);
        editingClient.setName(client.getName());
        editingClient.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(editingClient);
        return new Result("Successfully edited", true);
    }

    public Result deleteClient(Integer id){
        if (!clientRepository.existsById(id))
            return new Result("No such Client with this id", false);

        clientRepository.deleteById(id);
        return new Result("Successfully deleted", true);
    }
}
