import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import './account.scss';

const accountMenuItemsAuthenticated = () => (
  <>
    {/* <MenuItem icon="wrench" to="/account/settings" data-cy="settings">
      <Translate contentKey="global.menu.account.settings">Settings</Translate>
    </MenuItem>
     <MenuItem icon="lock" to="/account/password" data-cy="passwordItem">
      <Translate contentKey="global.menu.account.password">Password</Translate>
    </MenuItem>
     */}
    <li className="nav-item mr-2 mycolortText1"> 
      <MenuItem icon="sign-out-alt" to="/logout" data-cy="logout">
        Deconnexion 
      </MenuItem>
    </li>
  </>
);

const accountMenuItems = () => (
  <>
   <li className="nav-item mr-2 mycolortText1">
      <MenuItem id="login-item" icon="sign-in-alt" to="/login" data-cy="login" >
        Se connecter  
      </MenuItem>
    </li>
    <li className="nav-item mr-2 mycolortText1">
      <MenuItem icon="user-plus" to="/account/register" data-cy="register">
         Créer mon compte 
      </MenuItem>
    </li>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <ul className="navbar-nav ml-auto text-light">
    {isAuthenticated && accountMenuItemsAuthenticated()}   
    {!isAuthenticated && accountMenuItems()}
  </ul>
);

export default AccountMenu;
