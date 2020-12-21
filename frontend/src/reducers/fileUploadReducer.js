export default (state=[{file:""}],action)=>{
    switch(action.type){
        case "UPLOAD_EXCEL":return action.payload;
        default: return state;
    }
}