
import { httpUpload } from "../apis/httpUtil";

class UploadFilesService {
  upload(file, onUploadProgress) {
    let formData = new FormData();    
    formData.append("file", file);

    return httpUpload.post("", formData, {

      onUploadProgress,
    });
  }
}

export default new UploadFilesService();
