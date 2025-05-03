import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavDropdown } from './menu-components';
import { Translate, translate } from 'react-jhipster';
import './account.scss';

const adminMenuItems = () => (
  <>
  <MenuItem icon="list" to="/admin/gestion-commandes-produits">
      Commandes / Produits
  </MenuItem>
    <MenuItem icon="users" to="/admin/user-management">
        Gestion utilisateurs
    </MenuItem>
    </>
);

export const AdminMenu = ({ showOpenAPI }) => (
  <NavDropdown  icon="list" name={"Mon espace"} id="admin-menu" data-cy="adminMenu">
    {adminMenuItems()}
  </NavDropdown>
);

export default AdminMenu;
