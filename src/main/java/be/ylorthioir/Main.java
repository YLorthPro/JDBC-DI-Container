package be.ylorthioir;

import be.ylorthioir.dal.DiContainer;
import be.ylorthioir.dal.InitDB;
import be.ylorthioir.dal.JavanaisRepository;
import be.ylorthioir.entities.Javanais;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        InitDB.initialisation();
        
        DiContainer container = DiContainer.init();
        
        JavanaisRepository repo = container.get(JavanaisRepository.class);

        List<Javanais> javanais = repo.getAll();
        System.out.println(javanais.size());

        repo.delete(2L);

        javanais = repo.getAll();
        System.out.println(javanais.size());
    }
}