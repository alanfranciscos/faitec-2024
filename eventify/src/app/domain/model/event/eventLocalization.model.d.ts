export interface EventLocation {
  locationName: string;
  zipCode: string;
  country: string;
  city: string;
  neighborhood: string;
  street: string;
  number: string;
  complement: string;
  coordinates: Coordinate;
}

export interface Coordinate {
  lat: number;
  lng: number;
}
