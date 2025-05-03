// gestion-commandes-produits.service.ts
import axios from 'axios';

 

export const getAllCommandes = () => {
  return axios.get('http://localhost:8080/api/commandes')
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching commandes', error);
      throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
    });
};

export const getCategories = () => {
  return axios.get('http://localhost:8080/api/categories')
    .then(response => response.data)
    .catch(error => {
      console.error('Error fetching categories', error);
      throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
    });
};

export const saveCategorie = (objetCategorie) => {
  return axios.post('http://localhost:8080/api/categories',objetCategorie)
    .then(response => response.data) 
    .catch(error => {
      console.error('Error saving categorie', error);
      throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
    });
};

export const saveProduit = (objetProduit) => {
  return axios.post('http://localhost:8080/api/produits',objetProduit)
    .then(response => response.data) 
    .catch(error => {
      console.error('Error saving produit', error);
      throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
    });
};

  // Fonction pour gérer l'upload de l'image
export const uploadImage = async (formData, token) => {
 
  try {
    const response = await fetch('http://localhost:8080/api/produits/upload-image', {
      method: 'POST',
      body: formData,
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    return response;
  }catch (error) {
    console.error('Erreur côté serveur :', error.message);
    alert(`Erreur lors de l'enregistrement du produit : ${error.message}`);
  }
};




