import {Links} from "../service/Links";

export class Goods {
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
  _links: Links;
}
