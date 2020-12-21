import React from 'react';
import { Link } from 'react-router-dom';
import {properties} from '../properties'

const Header = () => {
    return (
        <div className="ui three item menu">

            <Link to="/" className="  item">Students</Link>

            <div className="item">
                <Link to="/import" className="item">Import</Link>                
            </div>
            <div className="item">
            <a href={properties.exportApiURL} className="item">Export</a>
            </div>
        </div>
    )
}

export default Header;