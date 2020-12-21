import {jsonStudents, httpUpload} from '../apis/httpUtil';



export const fetchStudents = (page = 0, limit = 20) => async dispatch => {
    const response = await jsonStudents.get(`/${page}/${limit}`);    
    dispatch({ type: 'FETCH_STUDENT', payload: { page: ++page, limit, students: response.data } });
};
export const uploadStudentsFile = (file) => async dispatch => {
    const response = await httpUpload.post("/students/upload/}");
    
    dispatch({ type: 'UPLOAD_EXCEL', payload: {response } });
};

export const uploadExcelFile = (file = null) => async dispatch => {
    let formData = new FormData();

    formData.append("file", file);

    const response = await httpUpload.post("", formData);
    //console.log(response);
    return {
        type: "EXCEL_FILE_UPLOAD",
        payload: {excelFileUploaded:true,message:response.message}
    };
};

