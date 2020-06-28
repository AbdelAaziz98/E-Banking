package com.banque.demo.web;

import com.banque.demo.dao.*;
import com.banque.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class AppAgentRestController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;






    //AgentController

    //Consulter tous les comptes d'un client specifique
    @GetMapping(value="/agent/{id_client}/ConsulterComptes")
    public List<Compte> comptes(@PathVariable (name="id_client") Long id_client){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        Client client_actuel=clients.get(Math.toIntExact(id_client));
        List<Compte> comptes= (List<Compte>) client_actuel.getComptes();
        return comptes;


    }

    @PostMapping(value="/agent/{id_client}/AjouterCompte")
    public Compte ajoutercompte(@PathVariable (name="id_client") String id_client, @RequestBody Compte compte){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        Client client_actuel=clientRepository.getOne(Long.parseLong(id_client));
        client_actuel.setAgence(agence_actuelle);
        client_actuel.getComptes().add(compte);
        compte.setClient(client_actuel);
        clientRepository.save(client_actuel);
        agenceRepository.save(agence_actuelle);
        return compteRepository.save(compte);
    }

    @PutMapping(value="/agent/{id_client}/ConsulterComptes/{id_compte}")
    public Compte updatecompte(@PathVariable (name="id_client") Long id_client, @PathVariable (name="id_compte") Long id_compte, @RequestBody Compte compte){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        Client client_actuel=clients.get(Math.toIntExact(id_client));
        client_actuel.setAgence(agence_actuelle);
        client_actuel.getComptes().add(compte);
        compte.setClient(client_actuel);
        compte.setId(id_compte);
        clientRepository.save(client_actuel);
        agenceRepository.save(agence_actuelle);
        return compteRepository.save(compte);
    }

    @DeleteMapping(value="/agent/ConsulterComptes/{id_compte}")
    public void deletecompte(@PathVariable(name="id_compte") Long id_compte){

        compteRepository.deleteById(id_compte);
    }
    @GetMapping(value="/agent/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "niiiice";
    }

    @PostMapping(value="/agent/CreerClient")
    public Client ajouterclient(@RequestBody Client client){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        client.setAgence(agence_actuelle);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        agence_actuelle.getClients().add(client);
        agenceRepository.save(agence_actuelle);
        client.setRole("CLIENT");
        Client c=clientRepository.save(client);
        return c;

    }
    @PutMapping(value="/agent/{id_client}/ModifierClient")
    public Client modifierclient(@PathVariable (name="id_client") Long id_client, @RequestBody Client client){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        client.setAgence(agence_actuelle);
        client.setId(id_client);
        agence_actuelle.getClients().add(client);
        agenceRepository.save(agence_actuelle);
        return clientRepository.save(client);

    }


    @GetMapping(value="/agent/ConsulterClients")
    public List<Client> clients(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        return clients;
    }

    @PutMapping(value="/agent/ActiverClient/{id_client}")
    public Client activerclient(@PathVariable (name="id_client") Long id_client, @RequestBody Client client){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        client.setAgence(agence_actuelle);
        client.setId(id_client);
        client.setStatus("active");
        agence_actuelle.getClients().add(client);
        agenceRepository.save(agence_actuelle);
        return clientRepository.save(client);
    }

    @PutMapping(value="/agent/SuspendreClient/{id_client}")
    public Client suspendreclient(@PathVariable (name="id_client") Long id_client, @RequestBody Client client){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        client.setAgence(agence_actuelle);
        client.setId(id_client);
        client.setStatus("suspendu");
        agence_actuelle.getClients().add(client);
        agenceRepository.save(agence_actuelle);
        return clientRepository.save(client);
    }


    @GetMapping(value="/agent/ConsulterMessage/{id_client}")
    public List<Message> messages(@PathVariable(name="id_client") Long id_client){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        Client client_actuel=clients.get(Math.toIntExact(id_client));
        List<Message> messages= (List<Message>) client_actuel.getMessages();
        return messages;
    }

    @PostMapping("/agent/ContacterClient/{id_client}")
    public Message contacter_client(@PathVariable(name="id_client") Long id_client, @RequestBody Message message) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Agent agent_actuel=agentRepository.findByUsername(username);
        Agence agence_actuelle=agent_actuel.getAgence();
        List<Client> clients= (List<Client>) agence_actuelle.getClients();
        Client client_actuel=clients.get(Math.toIntExact(id_client));
        message.setAgent(agent_actuel);
        message.setClient(client_actuel);
        client_actuel.getMessages().add(message);
        agent_actuel.getMessages().add(message);
        return messageRepository.save(message);
    }




}
