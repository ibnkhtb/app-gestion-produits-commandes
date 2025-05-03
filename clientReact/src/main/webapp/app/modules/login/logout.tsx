import React, { useLayoutEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOut, faBackspace } from '@fortawesome/free-solid-svg-icons';
import { Card, Alert,Button } from 'react-bootstrap';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import { Link } from 'react-router-dom';


export const Logout = () => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });

  return (
    <>
      <div className="p-5">
        <h4 className='text-success'>Déconnexion reussi!</h4>
      </div>
      
      <Button variant="secondary" className='mt-4 fw-bolder bg-info' >
      <Link to={`/`} className=''>
        <FontAwesomeIcon icon={faBackspace} className="me-2 " />
         Retour à la page d'accueil
        </Link>
      </Button>
    </>
  );
 
};

export default Logout;
