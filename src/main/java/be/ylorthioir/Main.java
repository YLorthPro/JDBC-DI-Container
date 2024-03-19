package be.ylorthioir.dal;

import be.ylorthioir.entities.Javanais;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        InitDB.initialisation();
        
        DiContainer container = DiContainer.init();
        
        RequetePreparee repo = container.get(RequetePreparee.class);

        List<Javanais> javanais = repo.getAll();
        System.out.println(javanais.size());

        repo.delete(2L);

        javanais = repo.getAll();
        System.out.println(javanais.size());
    }
}