package pojos;
// Generated 29-ene-2024 10:07:31 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Obra generated by hbm2java
 */
public class Obra  implements java.io.Serializable {


     private int idObras;
     private Autor autor;
     private String nombre;
     private String tipoArte;
     private byte estado;
     private Set exposicions = new HashSet(0);

    public Obra() {
    }

	
    public Obra(int idObras, Autor autor, String nombre, byte estado) {
        this.idObras = idObras;
        this.autor = autor;
        this.nombre = nombre;
        this.estado = estado;
    }
    public Obra(int idObras, Autor autor, String nombre, String tipoArte, byte estado, Set exposicions) {
       this.idObras = idObras;
       this.autor = autor;
       this.nombre = nombre;
       this.tipoArte = tipoArte;
       this.estado = estado;
       this.exposicions = exposicions;
    }
   
    public int getIdObras() {
        return this.idObras;
    }
    
    public void setIdObras(int idObras) {
        this.idObras = idObras;
    }
    public Autor getAutor() {
        return this.autor;
    }
    
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipoArte() {
        return this.tipoArte;
    }
    
    public void setTipoArte(String tipoArte) {
        this.tipoArte = tipoArte;
    }
    public byte getEstado() {
        return this.estado;
    }
    
    public void setEstado(byte estado) {
        this.estado = estado;
    }
    public Set getExposicions() {
        return this.exposicions;
    }
    
    public void setExposicions(Set exposicions) {
        this.exposicions = exposicions;
    }

    @Override
    public String toString() {
        return nombre;
    }




}


