package be.ylorthioir.dal;

import org.reflections.Reflections;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DiContainer implements Closeable {

    private final Map<Class<?>, Object> services = new HashMap<>();
    private Connection connection;
    
    // Initialisation du conteneur
    
    public static DiContainer init() {
        DiContainer container = new DiContainer();
        Reflections reflections = new Reflections("be.ylorthioir");

        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Repository.class);

        try {
            container.connection = DriverManager.getConnection(ConnectionFactory.URL, ConnectionFactory.USERNAME, ConnectionFactory.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create a JDBC connection", e);
        }

        container.services.put(Connection.class, container.connection);

        types.forEach(container::resolve);

        return container;
    }

    // Dépendances d'une classe et instanciation
    private Object resolve(Class<?> clazz) {
        Class<?>[] tInterfaces = clazz.getInterfaces(); // Récupère les interfaces implémentées par la classe
        Constructor<?> constructor = clazz.getConstructors()[0]; // Récupère le premier constructeur de la classe

        Parameter[] parameters = constructor.getParameters(); // Récupère les paramètres du constructeur
        List<Object> values = new ArrayList(); // Liste pour stocker les instances des dépendances résolues

        for (Parameter parameter : parameters) {
            Object value = services.get(parameter.getType()); // Récupère l'instance de la dépendance depuis la Map de services

            if (value == null) {
                // Si l'instance de la dépendance n'existe pas encore, on la résout et on l'ajoute à la Map de services
                value = resolve(parameter.getType());
                services.put(parameter.getType(), value);
            }

            values.add(value); // Ajoute l'instance de la dépendance
        }

        try {
            Object instance = constructor.newInstance(values.toArray()); // Crée une nouvelle instance de la classe avec les dépendances résolues
            for (Class<?> tInterface : tInterfaces) {
                services.put(tInterface, instance); // Ajoute l'instance de la classe à la Map de services pour toutes les interfaces qu'elle implémente
            }
            services.put(clazz, instance); // Ajoute l'instance de la classe à la Map de services avec la clé de la classe
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // Récupération d'une instance de classe à partir du conteneur
    public <T> T get(Class<T> clazz) {
        return (T) services.get(clazz); // Récupère l'instance de la classe depuis la Map services
    }


    @Override
    public void close(){
        try {
            connection.close();
        } catch (SQLException e){
            System.out.println("Close exception");
        }
    }
}
