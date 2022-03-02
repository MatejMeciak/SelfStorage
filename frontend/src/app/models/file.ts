export interface File {
  id: number;
  name: string;
  fileSize: number;
  folderId: number;
  categories: any[];
  mimeType: string;
  date: number;
  ownerId: number;
  access: boolean;
}


