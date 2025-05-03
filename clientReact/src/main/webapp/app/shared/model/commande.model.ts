import {ICategorie} from './categorie.model';
import {IClient} from './client.model';
import { IProduit } from './produit.model';

export interface ICommande {
  idCommande: any;
  dateCommande: String;
  client?: IClient;
  produit?: IProduit;
}
