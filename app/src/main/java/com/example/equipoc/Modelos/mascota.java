package com.example.equipoc.Modelos;

public class mascota {
    String id_animal;
    String nombre;
    String raza;
    String edad;
    clientes clientes;


//para poder obtener el nombre de la mascota

    public String getId_animal() {
        return id_animal;
    }

    public void setId_animal(String id_animal) {
        this.id_animal = id_animal;
    }

    public String getNombre() {
        return nombre;
    }

    //para poder agregar un valor a la mascota
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;

    }


    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public com.example.equipoc.Modelos.clientes getClientes() {
        return clientes;
    }

    public void setClientes(com.example.equipoc.Modelos.clientes clientes) {
        this.clientes = clientes;
    }
}
