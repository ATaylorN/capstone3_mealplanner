import axios from 'axios';


export default{
    addIngredient(ingredient){
      return axios.post('/ingredients', ingredient)
    },
    getAllIngredients(){
        return axios.get('/ingredients');
    },
    searchIngredient(searchTerms){
      return axios.get('/ingredients', searchTerms);
    }
}
/*
Good Ingredient Categories/Filters
- is frozen 
- is dry good

- is produce

  - is vegetable    
  - is fruit
  
- is a spice
  - is fresh
  - is dried

- is dairy product

- is meat
  - is preserved/dried
  - is beef
  - is poultry
  - is plant-based
  - is seafood
    - is fish
    - is shellfish


*/