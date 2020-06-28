package com.banque.demo;

import com.banque.demo.dao.AdminRepository;
import com.banque.demo.dao.BanqueRepository;
import com.banque.demo.entities.Adminstrateur;
import com.banque.demo.entities.Banque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BanqueProjectApplication implements CommandLineRunner  {


    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    private PasswordEncoder passwordEncoder;

    public BanqueProjectApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(BanqueProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Banque banque =banqueRepository.save(new Banque("MonNom"));
        Adminstrateur admin1=new Adminstrateur();
        admin1.setUsername("aziz");
        admin1.setPassword(passwordEncoder.encode("1234"));
        admin1.setActivated(true);
        admin1.setBanque(banque);
        admin1.setNom("aziz");
        admin1.setRole("ADMIN");
        admin1.setPrenom("Idaou");
        Adminstrateur adminstrateur=adminRepository.save(admin1);
        Banque banque2 =banqueRepository.save(new Banque("MonNom2"));
        Adminstrateur admin12=new Adminstrateur();
        admin12.setUsername("azi22z");
        admin12.setPassword("123224");
        admin12.setActivated(true);
        admin12.setBanque(banque);
        admin12.setNom("azi2z");
        admin12.setPrenom("Ida2ou");
        admin12.setRole("ADMIN");
        Adminstrateur adminstrateur2=adminRepository.save(admin12);



    }

}
