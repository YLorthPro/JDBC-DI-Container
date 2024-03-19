package be.ylorthioir.entities;

public class Javanais {

    private long id;
    private String nom;
    private String prenom;
    private int numeroRoueInfortune;
    private Voiture voiture;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumeroRoueInfortune() {
        return numeroRoueInfortune;
    }

    public void setNumeroRoueInfortune(int numeroRoueInfortune) {
        this.numeroRoueInfortune = numeroRoueInfortune;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    @Override
    public String toString() {;
        if(voiture == null)
            return "Je m'appelle "+ prenom+ " et je n'ai pas de voiture";
        else
            return "Je m'appelle "+ prenom+ " et je roule en " +voiture.getMarque();
    }
}
