package pojos;
// Generated 29-ene-2024 10:07:31 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Autor generated by hbm2java
 */
public class Autor  implements java.io.Serializable {


     private int idAutor;
     private String nombre;
     private String fechNacimiento;
     private String nacionalidad;
     private Set obras = new HashSet(0);

    public Autor() {
    }

	
    public Autor(int idAutor, String nombre) {
        this.idAutor = idAutor;
        this.nombre = nombre;
    }
    public Autor(int idAutor, String nombre, String fechNacimiento, String nacionalidad, Set obras) {
       this.idAutor = idAutor;
       this.nombre = nombre;
       this.fechNacimiento = fechNacimiento;
       this.nacionalidad = nacionalidad;
       this.obras = obras;
    }
   
    public int getIdAutor() {
        return this.idAutor;
    }
    
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFechNacimiento() {
        return this.fechNacimiento;
    }
    
    public void setFechNacimiento(String fechNacimiento) {
        this.fechNacimiento = fechNacimiento;
    }
    public String getNacionalidad() {
        return this.nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    public Set getObras() {
        return this.obras;
    }
    
    public void setObras(Set obras) {
        this.obras = obras;
    }

    @Override
    public String toString() {
        return nombre;
    }




}


