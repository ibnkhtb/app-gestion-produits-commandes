import React, { useEffect, useState, HtmlHTMLAttributes } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { IProduit } from '../../shared/model/produit.model';
import { Card, Alert,Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeftRotate, faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { getProduitById , getClient, addCommande, getClientByUserId, getUser} from './detailService';
import { getSession } from 'app/shared/reducers/authentication';
import { useSelector, useDispatch } from 'react-redux';
import { IClient } from 'app/shared/model/client.model';
import ModalClient from './modalClient';
import { IUser } from 'app/shared/model/user.model';

// Styles pour le modal directement dans Detail.js
const modalStyle : React.CSSProperties = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  background: 'white',
  padding: '20px',
  borderRadius: '8px',
  width: '400px',
};

interface RootState {
  authentication: {
    isAuthenticated: boolean;
    account?: {
      id: string; // ou le type réel d'id
      // Autres propriétés de compte
    };
    // Autres propriétés de l'état d'authentification
  };
  // Autres tranches (slices) du state
}

const Detail = () => {
  const { idProduit } = useParams();
  const [produit, setProduit] = useState<IProduit | null>(null);
  const [client, setClient] = useState<IClient | null>(null);
  const [user, setUser] = useState<IUser | null>(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [cartItems, setCartItems] = useState<IProduit[]>([]); // Liste des produits ajoutés
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state: RootState) => state.authentication.isAuthenticated);
  const [userId, setUserId] = useState<string | null>(null);
  const userIdFromStore  = useSelector((state: RootState) => state.authentication.account?.id);
  const [showModal, setShowModal] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false); // Ajoutez cet état

  const handleOpenModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

 useEffect(() => {
  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getProduitById(idProduit);
      setProduit(response);
    } catch (error) {
      // Gérer l'erreur si nécessaire
    } finally {
      setLoading(false);
    }
  };

  fetchData();
}, []);



 

  const saveCommande = async () => {
    if (produit) {
      //Si l'utilisateur n'est pas connecté on le suggere de se connecter ou de créer un compte 
      if(!isAuthenticated )
        alert('Merci de créer un compte. Si vous avez déjà un compte connectez-vous !')
      
      //si utilisateur est connecté
        //on recupère son identifiant 
      if (isAuthenticated) {

        //on recupère son identifiant
          setUserId(userIdFromStore);
          const idUser =  await getClientByUserId(parseInt(userIdFromStore, 10));
          if(idUser !== null){ //donc utlisateur est deja un client
             const response2 =  await getClient(idUser);

            if(response2 !== null){ 
              const currentDate = new Date();
              const date = currentDate.toLocaleDateString();
  
              const objetCommande = {
                dateCommande: date,
                client: response2,
                produit: produit
              }
              const response3 = await addCommande(objetCommande);
              if(response3)
                alert('Commande enregistré avec succès ! '); 
            } 
          }
          else{ 
            const idUser = parseInt(userIdFromStore, 10);
            const userRep = await getUser(idUser)

            if(userRep){
              setUser(userRep);
              setIsModalOpen(true);  
            }
              
                           
          } 
         
            // La réponse n'est pas null, vous pouvez procéder avec le reste de la logique
            // alert(response);
    
            // J'enregistre dans la base de données (ajoutez votre logique ici)
         


      
        }
      
           //j'enregistre dans la base de données
         
    
    }
  };

  const folderImage ="http://localhost/images/";
  const sousChaine ='content';

//  console.log('Rendering component with state:', { loading, error, produit });


  return (
    <>
    
      {loading && <p>Chargement en cours...</p>}
      {error && <Alert variant="danger">{error}</Alert>}
      {!loading && !error && produit === null && (
        <Alert variant="info">Aucun produit disponible dans cette catégorie.</Alert>
      )}
      {!loading && !error && produit !== null && (
        <div className='container mt-4'>
          <Card className='d-flex flex-row'>
            <Card.Img
              variant='top'
              src={ `../../../${produit.imageProduit.toString()}`.includes(sousChaine)
              ?  `../../../${produit.imageProduit.toString()}` 
              : `${folderImage}${produit.imageProduit.toString()}`}
              alt={produit.nomProduit}
              style={{ objectFit: 'cover', height: '500px', width:'50%'}} // Ajustez la taille de l'image selon vos besoins
            />
            <Card.Body className='text-end flex-grow-1 shadow'  style={{ objectFit: 'cover', height: '500px', width:'50%'}}>
              <Card.Title>
                  {produit.nomProduit}&nbsp;&nbsp;&nbsp; &nbsp;
                  <span className='mr-4 text-warning'>{produit.prixProduit} € </span>
             </Card.Title>
              <hr className='border mt-4'/>
              <Card.Text className='mt-4 text-start text-justify'>
                 <p>{produit.descriptionProduit}</p>
                 <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatem, aliquid voluptates officiis distinctio odio labore. Accusantium enim, at ut voluptatibus expedita porro similique hic eaque tempore distinctio quisquam beatae vitae.</p>
                 <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptatem, aliquid voluptates officiis distinctio odio labore. Accusantium enim, at ut voluptatibus expedita porro similique hic eaque tempore distinctio quisquam beatae vitae.</p>
              </Card.Text>
              <Button variant="secondary" className='mt-4 fw-bolder ' onClick={saveCommande}>
                 <FontAwesomeIcon icon={faShoppingCart} className="me-2 " />
                  AJOUTER
              </Button>
            </Card.Body>
          </Card>
        </div>
      )}

   <ModalClient isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} user={user} produit={produit} />

    </>
    
  );
};

export default Detail;
