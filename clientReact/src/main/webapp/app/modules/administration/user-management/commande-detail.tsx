import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Badge } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { languages } from 'app/config/translation';
import { getUser } from './user-management.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CommandeDetail = () => {
 

  return (
    <div>
      <h2>
        Liste des commandes
      </h2>
      <Row size="md">
        <dl className="jh-entity-details">
          <dt>
             Produit
          </dt>
          <dt>
             Categorie
          </dt>
          <dt>
             Prénom client
          </dt>
          <dt>
             Nom client
          </dt>
          <dt>
             Téléphone
          </dt>
          <dt>
             Date
          </dt>
          <dd>
            <ul className="list-unstyled">
              
            </ul>
          </dd>
        </dl>
      </Row>
      <Button tag={Link} to="/admin/user-management" replace color="info">
        <FontAwesomeIcon icon="arrow-left" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.back">Back</Translate>
        </span>
      </Button>
    </div>
  );
};

export default CommandeDetail;
