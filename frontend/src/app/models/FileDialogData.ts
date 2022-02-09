import { File } from "./file";
import { Observable } from "rxjs";

export interface FileDialogData {
  file: File
  image?: Observable<string>;
}
