import {Links} from "../service/Links";

export class Goods {
  id: number;
  name: string;
  description: string;
  price: number;
  createBy: string;
  createDate: string;
  ownerUnit: string;
  lastModifyBy: string;
  lastModifyDate: string;
  lastModifyOwnerUnit: string;
  version: number;
  _links: Links;
}
