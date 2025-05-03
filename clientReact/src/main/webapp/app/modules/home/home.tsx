import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import Carousel from 'react-bootstrap/Carousel';
import   { useEffect, useState } from 'react';
import axios from 'axios'; // Assurez-vous d'avoir axios installé, sinon exécutez npm install axios
import { IProduit } from '../../shared/model/produit.model';
import { Nav, Navbar, Badge,Card  } from 'react-bootstrap';

import { useAppSelector } from 'app/config/store';
import Slider from './Caroussel/caroussel';
import slides from './Caroussel/images.json'




export const Home = () => {
  
  //on recupere les produits
  const [produits, setProduits] = useState<IProduit[]>([]);
  useEffect(() => {
    console.log('Header component mounted');
    axios.get('http://localhost:8080/api/produits/top')
        .then(response => setProduits(response.data))
        .catch(error => console.error('Error fetching categories', error));
}, []);

  return (
    <>
     {/* // div pour différentes catégories de produits et le caroussel (slider) */}
     <div className='container  bg-slider'>
         <Slider slides={slides}/>
    </div>

    {/* // produits les plus vendus  */}
    <div className='container mt-4 shadow'>
       <h2 className='border-bottom mb-4 mycolorText2 fw-bold'>Top produits</h2>
        <Row xs={1} md={4} className='g-4 '>
            {produits.map(produit => (
              <Col key={produit.idProduit}>
                <Card className=''>
                  <Link to={`/details/produits/${produit.idProduit}`} className='card-link'>
                    <Card.Body className='text-end shadow'>
                      <Card.Title className='text-no-underline'><span></span>{produit.nomProduit}</Card.Title>
                      <Card.Text className='mycolorText1 text-decoration-none'>Prix: {produit.prixProduit} €</Card.Text>
                    </Card.Body>
                    <Card.Img
                      variant='top'
                      src={`../../../${produit.imageProduit.toString()}`}
                      alt={produit.nomProduit}
                      style={{ objectFit: 'cover', height: '200px', width: '100%' }}
                    />
                  </Link>
                </Card>
              </Col>
            ))}
          </Row>
      </div>
    
    </>
   //fin
   );
}; 

export default Home;
