// Modal.tsx
import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import './detail.scss';
import { addClient, getUser, addCommande } from './detailService';
import { IUser } from 'app/shared/model/user.model';
import { IProduit } from 'app/shared/model/produit.model';

interface CustomModalProps {
    isOpen: boolean;
    onClose: () => void;
    user: IUser;
    produit: IProduit;
}
  

const ModalClient =  (props : CustomModalProps  ) => {
  const [nom, setNom] = useState('');
  const [prenom, setPrenom] = useState('');
  const [telephone, setTelephone] = useState('');
  const [adresse, setAdresse] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

     const objetClient = {
        nom: nom,
        prenom: prenom,
        telephone: telephone,
        adresse: adresse,
        email: email,
        user: props.user
      }

     const clientRep = await addClient(objetClient);
      
        if(clientRep){
        const currentDate = new Date();
        const date = currentDate.toLocaleDateString();

        const objetCommande = {
          dateCommande: date,
          client:clientRep.data,
          produit: props.produit
        }

        //console.log(objetCommande );
        const response = await addCommande(objetCommande);
        alert('Commande enregistré avec succès ! ');
        props.onClose; 
      }  
        
     }
        
    //on recupere l'objet utilisateur
   // const response = await addClient(objetClient);


    // Faire quelque chose avec les valeurs des champs (par exemple, enregistrer dans la base de données)

    // Une fois le traitement effectué, fermez le modal
    //props.onClose();
   

  return (
    <Modal show={props.isOpen} onHide={props.onClose}>
      <Modal.Header closeButton>
        <Modal.Title className='text-info'>Veuillez renseigner ses champs SVP !</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="nom">
            <Form.Label>Nom </Form.Label>
            <Form.Control type="text" value={nom} onChange={(e) => setNom(e.target.value)} required />
          </Form.Group>

          <Form.Group controlId="prenom">
            <Form.Label>Prénom </Form.Label>
            <Form.Control type="text" value={prenom} onChange={(e) => setPrenom(e.target.value)} required />
          </Form.Group>

          <Form.Group controlId="telephone">
            <Form.Label>Téléphone</Form.Label>
            <Form.Control type="text" value={telephone} onChange={(e) => setTelephone(e.target.value)} required />
          </Form.Group>

          <Form.Group controlId="adresse">
            <Form.Label>Adresse </Form.Label>
            <Form.Control type="text" value={adresse} onChange={(e) => setAdresse(e.target.value)} required />
          </Form.Group>

          <Form.Group controlId="email">
            <Form.Label>Email </Form.Label>
            <Form.Control type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </Form.Group>

          <Button variant="primary" type="submit">
            Enregistrer
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ModalClient;
