import React from 'react';

import {BrowserRouter, Route}  from 'react-router-dom';

import StudentList from './StudentList';
import FileUpload from './FileUpload'
import Header from './Header'




const App = () => {
    return (
        <div className="ui raised very text container segment">

        <div className="ui container">
            
            <BrowserRouter>
            <Header/>
                <div>
                    <Route path="/" exact component={StudentList}/>
                    <Route path="/import" exact component={FileUpload}/>
                    
                </div>
            </BrowserRouter>
        </div>
        </div>
    );
};

export default App;
