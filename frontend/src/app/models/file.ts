export interface File {
  id: number;
  sizeFile: number;
  fileName: string;
  date: number;
  access: boolean;
  link?: string;
}

