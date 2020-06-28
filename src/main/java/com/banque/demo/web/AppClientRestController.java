package com.banque.demo.web;

import com.banque.demo.dao.ClientRepository;
import com.banque.demo.dao.CompteRepository;
import com.banque.demo.dao.MessageRepository;
import com.banque.demo.dao.VirementRepository;
import com.banque.demo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AppClientRestController {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private MessageRepository messageRepository;





    //ClientController

    @GetMapping("/client")
    public Client Profil(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return  clientRepository.findByUsername(username);
    }

    @GetMapping(value="/client/ListComptes")
    public List<Compte> comptes(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Compte> mescomptes= (List<Compte>) clientRepository.findByUsername(username).getComptes();
           return mescomptes;

    }

    @GetMapping(value="/client/ListComptes/{id_compte}")
    public Compte ConsulterCompte(@PathVariable(name="id_compte") Long id_compte){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client_actuel=clientRepository.findByUsername(username);
       List<Compte> mes_comptes= (List<Compte>) client_actuel.getComptes();
          Compte compte_actuel=mes_comptes.get(Math.toIntExact(id_compte));
         return compte_actuel;


    }

    @GetMapping(value="/client/ListComptes/{id_compte}/ConsulterSolde")
    public long ConsulterSolde(@PathVariable(name="id_compte") Long id_compte){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client_actuel=clientRepository.findByUsername(username);
        List<Compte> mes_comptes= (List<Compte>) client_actuel.getComptes();
        Compte compte_actuel=mes_comptes.get(Math.toIntExact(id_compte));
        long solde_compte_actuel=compte_actuel.getSolde();
        return solde_compte_actuel;
    }


    
   @PostMapping("/client/ListComptes/{id_compte}/EffectuerVirement")
    public Virement effectuerVirement(@RequestBody Virement virement, @PathVariable(name="id_compte") Long id_compte){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username = auth.getName();
       Client client_actuel=clientRepository.findByUsername(username);
       //debiteur
       List<Compte> mes_comptes= (List<Compte>) client_actuel.getComptes();
       Compte compte_actuel=mes_comptes.get(Math.toIntExact(id_compte));

      //crediteur
       Compte compte2 = compteRepository.findByNum(virement.getNumcomptebenf());

         virement.Virer(compte_actuel,compte2,virement.getMontant());
         compte_actuel.getVirements().add(virement);
         virement.setClient(client_actuel);
         virement.setCompte(compte_actuel);
         compteRepository.save(compte_actuel);
         compteRepository.save(compte2);

         return  virementRepository.save(virement);



    }

    @GetMapping(value="/client/ListComptes/{id_compte}/ConsulterTransactions")
    public List<Virement> virements(@PathVariable(name="id_compte") Long id_compte){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client_actuel=clientRepository.findByUsername(username);
         List<Compte> mes_comptes= (List<Compte>) client_actuel.getComptes();
         Compte compte_actuel=mes_comptes.get(Math.toIntExact(id_compte));
         List<Virement> mes_virements= (List<Virement>) compte_actuel.getVirements();
        return mes_virements;
    }


    @PostMapping("/client/ContacterAgent")
    public Message contacter(@RequestBody Message message, @RequestBody Agent agent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client_actuel=clientRepository.findByUsername(username);
        message.setAgent(agent);
        message.setClient(client_actuel);
        client_actuel.getMessages().add(message);
        return messageRepository.save(message);
    }



}
