export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: string[];
  provider: string;
}
