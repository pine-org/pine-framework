import {Links} from "../service/Links";

export class Product {
  id: number;
  name: string;
  code: string;
  price: number;
  description: string;
  createBy: string;
  createDate: string;
  ownerUnit: string;
  lastModifyBy: string;
  lastModifyDate: string;
  lastModifyOwnerUnit: string;
  version: number;
  photo: string;
  _links: Links;
}
