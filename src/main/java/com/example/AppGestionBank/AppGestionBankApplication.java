package com.example.AppGestionBank;

import com.example.AppGestionBank.entities.*;
import com.example.AppGestionBank.entities.enums.StatCompte;
import com.example.AppGestionBank.entities.enums.TypeOp;
import com.example.AppGestionBank.exceptions.ClientNotFoundException;
import com.example.AppGestionBank.exceptions.CompteBancaireNotFoundException;
import com.example.AppGestionBank.exceptions.MontantNotSufficientException;
import com.example.AppGestionBank.repositories.ClientRepository;
import com.example.AppGestionBank.repositories.CompteBancaireRepository;
import com.example.AppGestionBank.repositories.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class AppGestionBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppGestionBankApplication.class, args);
	}
	/*@Bean
	CommandLineRunner commandLineRunner(CompteBancaireService compteBancaireService) {
		return args -> {
			Stream.of("amin","reda","soulayman").forEach(name -> {
				Client client = new Client();
				client.setNom(name);
				client.setEmail(name+"@gmail.com");
				compteBancaireService.addClient(client);
			});

			compteBancaireService.listClients().forEach(client -> {
                try {
                    compteBancaireService.addCompteBancaireCourant(Math.random()*90000,
                    9000,client.getId());
					compteBancaireService.addCompteBancaireEpargne(Math.random()*120000,
							5.5,client.getId());
					List<CompteBancaire> compteBancaires = compteBancaireService.listCompteBancaire();
					for (CompteBancaire compteBancaire : compteBancaires) {
						for (int i = 0; i < 10; i++) {
							compteBancaireService.credit(compteBancaire.getId(), 10000+Math.random()*120000,"Credit");
							compteBancaireService.debit(compteBancaire.getId(), 1000+Math.random()*9000,"Debit");
						}
					}

                } catch (ClientNotFoundException e) {
                    e.printStackTrace();
                } catch (CompteBancaireNotFoundException | MontantNotSufficientException e) {
					e.printStackTrace();
                }
            });

		};
	}

	//Bean
	CommandLineRunner start(ClientRepository ClientRepository,
							CompteBancaireRepository CompteBancaireRepository,
							OperationRepository operationRepository, ClientRepository clientRepository, CompteBancaireRepository compteBancaireRepository) {
		return args -> {
			Stream.of("karim","ahmed","yahya").forEach(name -> {
				Client c = new Client();
				c.setNom(name);
				c.setEmail(name+"@gmail.com");
				ClientRepository.save(c);
			});
			clientRepository.findAll().forEach(client -> {
				CompteCourant cc = new CompteCourant();
				cc.setId(UUID.randomUUID().toString());
				cc.setSolde(Math.random()*90000);
				cc.setDateCreation(new Date());
				cc.setStatut(StatCompte.CREATED);
				cc.setClient(client);
				cc.setDecouvert(9000);

				CompteBancaireRepository.save(cc);

				CompteEpargne ce = new CompteEpargne();
				ce.setId(UUID.randomUUID().toString());
				ce.setSolde(Math.random()*90000);
				ce.setDateCreation(new Date());
				ce.setStatut(StatCompte.CREATED);
				ce.setClient(client);
				ce.setTauxInteret(5.5);

				CompteBancaireRepository.save(ce);
			});

			CompteBancaireRepository.findAll().forEach(compte -> {
				for (int i = 0; i < 10; i++) {

					Operation op = new Operation();
					op.setDateOperation(new Date());
					op.setMontant(Math.random()*12000);
					op.setType(Math.random()>0.5? TypeOp.DEBIT: TypeOp.CREDIT);
					op.setCompteBancaire(compte);
					operationRepository.save(op);
				}
			});


		};

	}*/

}
