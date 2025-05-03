import {ICategorie} from './categorie.model';

export interface IProduit {
  idProduit: number;
  nomProduit: string;
  descriptionProduit: string;
  prixProduit: any;
  imageProduit: string;
  categorie?: ICategorie;
}
