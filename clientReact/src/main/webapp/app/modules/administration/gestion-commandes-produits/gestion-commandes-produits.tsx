import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table, Modal, ModalHeader, ModalBody, ModalFooter,Alert, Row, Col, Form  } from 'reactstrap';
import { Translate, translate, ValidatedField ,TextFormat, JhiPagination, JhiItemCount, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
 import { type FieldError, useForm } from 'react-hook-form';
 import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getAllCommandes, saveCategorie, getCategories, saveProduit,uploadImage} from './gestion-commandes-produits.reducer';
import './gestion-commandes-produits.scss';
import CategorieModal from './categorie-modal'; // Importez votre composant modal

 
 

const GestionCommandesProduits = () => {
  const [commandes, setCommandes] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModalCategorie, setShowModalCategorie] = useState(false); // État local pour contrôler l'affichage de la modal
  const [showModalProduit, setShowModalProduit] = useState(false); // État local pour contrôler l'affichage de la modal
  const [selectedCategorie, setSelectedCategorie] = useState(''); // État pour stocker la catégorie sélectionnée
 
  const token = useAppSelector(state => state.authentication.idToken);

 interface ICategorieModalProps {
    showModalCategorie: boolean;
    loginError: boolean;
    handleCategorie: (nomCategorie: string) => void;
    handleClose: () => void;
  }
  
 
  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });
 
  
  //function save categorie
  const handleSaveCategorie = async (data) => {
    try {
      const ObjetCategorie = { nomCategorie: data.nomCategorie };
      await saveCategorie(ObjetCategorie);
      <Alert> Opérarion reussi ! </Alert>
      console.log(ObjetCategorie);
      handleCloseModalCategorie();
    } catch (error) {
      // Affichez des détails sur la réponse du serveur dans la console
      console.error('Erreur lors de l\'enregistrement de la catégorie', error.response);
      // Examinez les détails de l'erreur côté serveur
      console.log('Erreur côté serveur :', error.response.data);
      // Affichez le message d'erreur à l'utilisateur (peut-être dans une alerte ou un composant dédié)
      alert(`Erreur lors de l'enregistrement de la catégorie : ${error.response.data.message}`);
    }
  };


  const handleSaveProduit = async (data) => {
    try {
      // Assurez-vous que les champs requis sont remplis
      if (!data.nomProduit || !data.prixProduit || !data.imageProduit || !selectedCategorie) {
        alert("Veuillez remplir tous les champs obligatoires.");
        return;
      }

      // Construire l'objet produit
      const folderImage = 'C:/Users/HP15/Desktop/images/';
      
      const imageFile = data.imageProduit[0];
      const imageName = imageFile.name;

      const imagePath = `${folderImage}${imageName}`;
     // alert(imagePath) ;
      // Traitez la réponse, par exemple, récupérez le chemin de l'image retourné par le serveur
      // const imagePath = await response.text();
      // alert(imagePath + `Bearer ${token}`);
       const objetProduit = {
          nomProduit: data.nomProduit,
          descriptionProduit: data.descriptionProduit,
          prixProduit: data.prixProduit,
          imageProduit: imageName,
          categorie: selectedCategorie,
      };

      // Enregistrez le produit sur le serveur
      await saveProduit(objetProduit);

      alert('Opération réussie !');
    } catch (error) {
      console.error('Erreur côté serveur :', error.message);
      alert(`Erreur lors de l'enregistrement du produit : ${error.message}`);
    }
  };


    
  
  
  // retrieve all commandes
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await getAllCommandes();
        setCommandes(response);
      } catch (error) {
        // Gérer l'erreur si nécessaire
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);


   // retrieve all categories
   useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await getCategories();
        setCategories(response);
      } catch (error) {
        // Gérer l'erreur si nécessaire
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  // Fonction pour ouvrir la modal categorie
  const handleOpenModalCategorie = () => {
    setShowModalCategorie(true);
  };

  // Fonction pour fermer la modal categorie
  const handleCloseModalCategorie= () => {
    setShowModalCategorie(false);
  };

    // Fonction pour ouvrir la modal produit
    const handleOpenModalProduit = () => {
        setShowModalProduit(true);
      };
    
      // Fonction pour fermer la modal produit
      const handleCloseModalProduit= () => {
        setShowModalProduit(false);
      };

  return (
    <>
      <div className='container '> 
        <h3 id="user-management-page-heading" data-cy="userManagementPageHeading" className='border-bottom mycolorText2'>
            Gestion Commandes et Produits
        </h3>
        <div className="d-flex justify-content-end mb-2">
            <Button   className="pr-2 btn bg-color2 btn-primary " onClick={handleOpenModalProduit}>
                <FontAwesomeIcon icon="plus" /> 
                Ajouter un produit
            </Button>
            <span>&nbsp;</span>
            {/* Utilisez handleOpenModal pour ouvrir la modal */}
            <Button className=" btn bg-color1 btn-primary" onClick={handleOpenModalCategorie}>
                <FontAwesomeIcon icon="plus" /> 
                Ajouter une catégorie
            </Button>
        </div>
      </div>
      <div className='container shadow border-color2'>
        <Table responsive striped>
          <thead>
            <tr>
              <th>Produit</th>
              <th>Categorie</th>
              <th>Prenom Client</th>
              <th>Nom Client</th>
              <th>Téléphone</th>
              <th>Date commande</th>
            </tr>
          </thead>
          <tbody className=''>
            {commandes.map((commande, i) => (
              <tr id={commande.idCommande} key={`commande-${i}`}>
                <td>{commande.produit.nomProduit}</td>
                <td>{commande.produit.categorie.nomCategorie}</td>
                <td>{commande.client.prenom}</td>
                <td>{commande.client.nom}</td>
                <td>{commande.client.telephone}</td>
                <td>{commande.dateCommande}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>

      {/* Modal Ajouter Categorie*/}
      <Modal isOpen={showModalCategorie} toggle={handleCloseModalCategorie}>
      <Form onSubmit={handleSubmit(handleSaveCategorie)}>
        <ModalHeader>Nouvelle catégorie</ModalHeader>
        <ModalBody>
        <Row>
            <Col md="12">
              <ValidatedField
                name="nomCategorie"
                label={'Nom :'}
                required
                autoFocus
                data-cy="nomCategorie"
               
                validate={{ 
                   required: 'Ce champ doit etre renseigné !', 
                   minLength: { value: 3, message:  'Minimum 3 caractères'  },
                 }}
                register={register}
                error={errors.nomCategorie as FieldError}
                isTouched={touchedFields.nomCategorie}
              />
            </Col>
          </Row>
         </ModalBody>
         <ModalFooter>
            <Button color="secondary" onClick={handleCloseModalCategorie} tabIndex={1}>
                Annuler
            </Button>{' '}
            <Button color="primary" type="submit" data-cy="submit">
                Enregistrer
            </Button>
         </ModalFooter>
      </Form>
      </Modal>
      {/*fin modal Ajouter categorie */}


       {/* Modal  Ajouter produit*/}
       <Modal isOpen={showModalProduit} toggle={handleCloseModalProduit}>
       <Form onSubmit={handleSubmit(handleSaveProduit)}>
        <ModalHeader>Nouveau Produit</ModalHeader>
        <ModalBody>
        <Row>
            <Col md="12">
              <ValidatedField
                name="nomProduit"
                label={'Nom :'}
                required
                autoFocus
                data-cy="nomProduit"
                validate={{ 
                   required: 'Minimum 3 caractères', 
                   minLength: { value: 3, message:  'Minimum 3 caractères'  },
                 }}
                register={register}
                error={errors.nomProduit as FieldError}
                isTouched={touchedFields.nomProduit}
              />
              <ValidatedField
                    name="descriptionProduit"
                    label={'Description :'}
                    required
                    autoFocus
                    data-cy="description"
                    as="textarea" // Utilisez l'attribut "as" pour définir le type de champ
                    validate={{ 
                        required: '', 
                    }}
                    register={register}
                    error={errors.descriptionProduit as FieldError}
                    isTouched={touchedFields.descriptionProduit}
                />

              <ValidatedField
                name="prixProduit"
                label={'Prix :'}
                required
                autoFocus
                data-cy="prixProduit"
                validate={{ 
                   required: '', 
                 }}
                register={register}
                error={errors.prixProduit as FieldError}
                isTouched={touchedFields.prixProduit}
              />
              <ValidatedField
                    name="imageProduit"
                    label={'Photo :'}
                    required
                    autoFocus
                    data-cy="imageProduit"
                    type="file" // Utilisez l'input de type fichier pour permettre à l'utilisateur de choisir un fichier depuis le système de fichiers
                    accept="image/*" // N'accepter que les fichiers image
                    validate={{ 
                        required: '', 
                    }}
                    register={register}
                    error={errors.imageProduit as FieldError}
                    isTouched={touchedFields.imageProduit}
                />
                   <span className='mb-2'>Catégorie :</span><br className='mb-2'/>
                  <select
                        className='shadow '
                        name="categorie"
                        required
                        autoFocus
                        data-cy="categorie"
                        {...register('categorie')}
                        onChange={(e) => setSelectedCategorie(categories.find(cat => cat.nomCategorie === e.target.value))}
                    >
                        
                        {categories.map((categorie, index) => (
                            <option key={index} value={categorie.nomCategorie}>
                                {categorie.nomCategorie}
                            </option>
                        ))}
                    </select>
               </Col>
          </Row>
         </ModalBody>
         <ModalFooter>
            <Button color="secondary" onClick={handleCloseModalProduit} tabIndex={1}>
                Annuler
            </Button>{' '}
            <Button color="primary" type="submit" data-cy="submit">
                Enregistrer
            </Button>
         </ModalFooter>
      </Form>
      </Modal>
      {/*fin modal Ajouter produit */}
    </>
  );
};

export default GestionCommandesProduits;
