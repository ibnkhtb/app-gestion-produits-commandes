import './header.scss';

import React, { useState, useEffect} from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { Nav, Navbar, Badge } from 'react-bootstrap';
import {  NavbarToggler, Collapse} from 'reactstrap';
import LoadingBar from 'react-redux-loading-bar';
import axios from 'axios';
import { useAppSelector } from 'app/config/store';
import { ICategorie } from '../../model/categorie.model';

import { Home, Brand } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import { useAppDispatch } from 'app/config/store';
import { setLocale } from 'app/shared/reducers/locale';

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const dispatch = useAppDispatch();

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
  };

 

  const toggleMenu = () => setMenuOpen(!menuOpen);

    //on recupere les categories de produits
    const [categories, setCategories] = useState<ICategorie[]>([]);
      useEffect(() => {
        console.log('Header component mounted');
        axios.get('http://localhost:8080/api/categories')
            .then(response => setCategories(response.data))
            .catch(error => console.error('Error fetching categories', error));
    }, []);
    
    //fin
 

return (
  <>
   {/*entete 1 */}
    <div id="app-header">
      <LoadingBar className="loading-bar" />
      <Navbar data-cy="navbar" expand="md" fixed="top" className="shadow mp-4 jh-navbar">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <Brand />
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ms-auto" navbar>
            {props.isAuthenticated && props.isAdmin && <AdminMenu showOpenAPI={props.isOpenAPIEnabled} />}
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Collapse>
      </Navbar>
    </div>

    {/*entete 2 menu categories de produits */}
    <div className='mt-4 border-bottom shadow bg-dark'> {/* Ajout de classes Bootstrap pour le container */}
      <Navbar bg="light" className='badge-css' expand="lg">
        <Nav className="mx-auto">
          {categories.map(categorie => (
            <Nav.Link key={categorie.idCategorie} as={Link} to={`/produits/categories/${categorie.idCategorie}`} className=''>
             <Badge className='badge-css'>
                {categorie.nomCategorie}
            </Badge>
            </Nav.Link>
          ))}
        </Nav>
      </Navbar>
    </div>
  </>
);


};

export default Header;
