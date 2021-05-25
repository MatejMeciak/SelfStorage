export interface File {
  id: number;
  fileSize: number;
  fileName: string;
  date: number;
  access: boolean;
  link?: string;
  mimeType: string;
}

