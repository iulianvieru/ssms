const initialState = { students: [], start: 0, limit: 20, hasMore: true };

export default (state = initialState, action) => {
    switch (action.type) {
        case "FETCH_STUDENT":
            const returnObj = {

                start: action.payload.start,
                limit: action.payload.limit,
                students: [...state.students, action.payload.students]
            };

            return action.payload;

        default:
            return state;

    }

}