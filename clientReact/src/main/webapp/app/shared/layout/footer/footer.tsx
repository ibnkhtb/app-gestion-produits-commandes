import './footer.scss';

import React from 'react';
import { FaFacebook, FaTwitter, FaInstagram } from 'react-icons/fa'; // Assurez-vous d'avoir react-icons installé, sinon exécutez npm install react-icons

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-links">
        <h6><a href="#">Contactez-nous</a></h6>
        <h6><a href="#">Services</a></h6>
        <h6><a href="#">Liens légaux</a></h6>
      </div>
      <div className="footer-copyright">
        @2023 M2 GDIL
      </div>
      <div className="footer-social">
        <a href="#" target="_blank" rel="noopener noreferrer">
          <FaFacebook />
        </a>
        <a href="#" target="_blank" rel="noopener noreferrer">
          <FaTwitter />
        </a>
        <a href="#" target="_blank" rel="noopener noreferrer">
          <FaInstagram />
        </a>
      </div>
    </footer>
  );
};
export default Footer;
