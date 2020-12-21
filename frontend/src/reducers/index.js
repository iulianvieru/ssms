import { combineReducers } from 'redux';
import studentReducer from './studentsReducer';
import uploadExcelFile from './fileUploadReducer';



export default combineReducers({
    students: studentReducer,
    uploadExcelFile: uploadExcelFile
});


