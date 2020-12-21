import axios from 'axios';
import {properties} from '../properties';

const jsonStudents = axios.create({
    baseURL: properties.studentsApiURL    
  })

 const httpUpload = axios.create({    
    baseURL: properties.importApiURL,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })



export {jsonStudents,httpUpload}
