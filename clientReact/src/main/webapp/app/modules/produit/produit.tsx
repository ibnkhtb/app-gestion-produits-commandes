import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import axios from 'axios';
import { IProduit } from '../../shared/model/produit.model';
import { Card, Col, Row, Alert } from 'react-bootstrap';
import { ICategorie } from 'app/shared/model/categorie.model';


const Product = () => {
  const { idCategorie } = useParams();
  const [produits, setProduits] = useState<IProduit[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/produits/categories/${idCategorie}`)
      .then(response => {
        setProduits(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError('Erreur lors de la récupération des produits.');
        setLoading(false);
      });
  }, [idCategorie]);
 
    
  //const categories = ['Electronique', 'Electromenager', 'Santé & Beauté', 'Mode Homme', 'Mode Femme'];
  let test=0;

  const sousChaine ='content';
 // const folderImage0= "https://www.webconversion.fr/wp-content/uploads/2019/06/outils-sondages-questionnaires-formulaires-en-ligne.jpg";
  const folderImage ="http://localhost/images/";

  
  return (
    <>
      {loading && <p>Chargement en cours...</p>}
      {error && <Alert variant="danger">{error}</Alert>}
      {!loading && !error && produits.length === 0 && (
        <Alert variant="info">Aucun produit disponible dans cette catégorie.</Alert>
      )}
      {!loading && !error && produits.length > 0 && (
        <div className='container mt-4'>
           {produits.map((produit, index) => (
              <div>
                  {index===0  && (<h2 className='mycolorText2'>{produit.categorie.nomCategorie}</h2>)} 
              </div> 
           ))}
           <br/>
          <Row xs={1} md={4} className='g-4'>
            {produits.map((produit,index) => (
              <Col key={index}>
                <Card>
                  <Link to={`/details/produits/${produit.idProduit}`} className='card-link shadow'>
                    <Card.Body className='text-end'>
                      <Card.Title>{produit.nomProduit}</Card.Title>
                      <Card.Text className='mycolorText1'>Prix: {produit.prixProduit} €</Card.Text>
                    </Card.Body>
                     
                    <Card.Img
                      variant='top'
                      src={
                        `../../../${produit.imageProduit.toString()}`.includes(sousChaine)
                         ?  `../../../${produit.imageProduit.toString()}` 
                         : folderImage + `${produit.imageProduit}`
                        }
                      //alt={produit.nomProduit}
                      alt = {folderImage + `${produit.imageProduit}`}
                      style={{ objectFit: 'cover', height: '200px', width: '100%' }}
                    />
                  </Link>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      )}
    </>
  );
};

export default Product;
