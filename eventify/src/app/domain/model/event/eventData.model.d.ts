export interface EventData {
  id: number;
  createdAt: Date;
  latitude: number;
  longitude: number;
  stage: string;
  eventname: string;
  eventdescription: string;
  local_name: string;
  cep_address: string;
  state_address: string;
  city_address: string;
  neighborhood_address: string;
  number_address: number;
  street_address: string;
  complement_address: string;
  date_start: Date;
  date_end: Date;
  pix_key: string;
}
