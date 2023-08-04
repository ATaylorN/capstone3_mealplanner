package com.techelevator.model;

public class Ingredient {
    // gonna let ID be determined by the DB
    private int id;
    private String name;

    public Ingredient(){
    }
    public Ingredient(String name){
        this.name = name;
    }
    public Ingredient(int id, String name){
        this.id = id;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setId(int id){
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
