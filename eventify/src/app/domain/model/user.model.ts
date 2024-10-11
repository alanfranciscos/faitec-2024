import { UserCredential } from './../dto/user-credential';
export interface User {
  id?: string;
  fullName: string;
  email: string;
  password: string;
}

export interface UserInputCredential {
  name: string;
  email: string;
  password: string;
  profileImage: File | null;
}
