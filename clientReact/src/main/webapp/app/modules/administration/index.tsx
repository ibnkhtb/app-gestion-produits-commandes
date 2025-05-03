// Importez useState depuis React pour gérer l'état du modal
import React, { useState } from 'react';
import { Route } from 'react-router-dom';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import UserManagement from './user-management';
import Logs from './logs/logs';
import Health from './health/health';
import Metrics from './metrics/metrics';
import Configuration from './configuration/configuration';
import Docs from './docs/docs';
import GestionCommandesProduits from './gestion-commandes-produits/gestion-commandes-produits';
import CategorieModal from './gestion-commandes-produits/categorie-modal';

const AdministrationRoutes = () => {
  // Utilisez l'état pour gérer l'affichage du modal
  const [showCategorieModal, setShowCategorieModal] = useState(false);

  // Fonction pour fermer le modal
  const handleCloseCategorieModal = () => setShowCategorieModal(false);

  // Fonction pour enregistrer la catégorie
  const handleSaveCategorieModal = (categoryName) => {
    // Effectuez ici l'enregistrement de la catégorie
    console.log(`Enregistrer la catégorie : ${categoryName}`);
  };

  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* Utilisez les propriétés nécessaires pour CategorieModal */}
        <Route path="gestion-commandes-produits/*" element={<GestionCommandesProduits />} />
        <Route
          path="gestion-commandes-produits/newCategorie"
          element={
          <CategorieModal
            showModal={showCategorieModal}
            handleClose={handleCloseCategorieModal}
            saveCategory={handleSaveCategorieModal}
          />
       }
        />
        <Route path="user-management/*" element={<UserManagement />} />
        <Route path="health" element={<Health />} />
        <Route path="metrics" element={<Metrics />} />
        <Route path="configuration" element={<Configuration />} />
        <Route path="logs" element={<Logs />} />
        <Route path="docs" element={<Docs />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};

export default AdministrationRoutes;
