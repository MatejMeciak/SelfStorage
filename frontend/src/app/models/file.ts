export interface File {
  id: number;
  name: string;
  fileSize: number;
  categoryId: number;
  mimeType: string;
  date: number;
  ownerId: number;
  access: boolean;
}

