import { IUser } from "./user.model";

export interface IClient {
  idClient: number;
  nom: string;
  prenom: string;
  adresse: String;
  telephone: string;
  email: String;
  user: IUser;
}
