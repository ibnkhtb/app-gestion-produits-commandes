import axios from 'axios';

export const getProduitById = (idProduit) => {
    return axios.get(`http://localhost:8080/api/produits/${idProduit}`)

      .then(response => response.data)
      .catch(error => {
        console.error('Error fetching produit', error);
        throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
      });
  };

 export const getUser = (id) => {
    return axios.get(`http://localhost:8080/api/users/account/${id}`)

      .then(response => response.data)
      .catch(error => {
        console.error('Error fetching user', error);
 
      });
  }; 


export const addCommande = (objetCommande) => {
    return axios.post('http://localhost:8080/api/commandes',objetCommande)
      .then(response => response) 
      .catch(error => {
        console.error('Error saving commandes', error);
        throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
      });
  };

  export const addClient = (objetClient) => {
    return axios.post('http://localhost:8080/api/clients',objetClient)
      .then(response => response) 
      .catch(error => {
        console.error('Error saving client', error);
        //throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
      });
  };


  export const getClientByUserId = (idUser) => {
    return axios.get(`http://localhost:8080/api/clients/user/${idUser}`)
      .then(response => {
        if (response.status === 200) {
          const clientData = response.data;
          if (clientData) {
            return clientData;
          } else {
            console.error('Client not found for ID:', idUser);
            // Au lieu de jeter une erreur, vous pouvez retourner null ou un objet indiquant que le client n'a pas été trouvé
            return null;
          }
        } else {
          console.error('Error fetching client. Unexpected status:', response.status);
          return null;

        }
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          // Gérer spécifiquement l'erreur 404 ici
          console.error('Client not found for ID:', idUser);
          return null;
        } else {
          console.error('Error fetching client', error);
          return null;
        }
      });
  };

  export const getClient = (idClient) => {
    return axios.get(`http://localhost:8080/api/clients/${idClient}`)
      .then(response => response.data)
      .catch(error => {
        if (error.response && error.response.status === 404) {
          // Gérer spécifiquement l'erreur 404 ici
          console.error('Client not found for ID:', idClient);
          return null;
        } else {
          console.error('Error fetching client', error);
          throw error; // Propagez l'erreur pour que le composant puisse la gérer si nécessaire
        }
      });
  };
  