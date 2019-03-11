import React from 'react';
import {Link, withRouter} from 'react-router-dom';
import jwtAuth from "../../AuthService";


function NavBar(props) {
    const signOut = () => {
        jwtAuth.logout();
        props.history.replace('/');
    };
    return (
        <nav className="navbar navbar-dark bg-primary fixed-top">
            <Link className="navbar-brand" to="/">
                Survey app
            </Link>
            {
                !jwtAuth.loggedIn() &&
                <Link to="/login">
                    <button className="btn btn-dark">Sign In</button>
                </Link>

            }
            {
                jwtAuth.loggedIn() &&
                <div>
                    <label className="mr-2 text-white">User</label>
                    <button className="btn btn-dark" onClick={() => {signOut()}}>Sign Out</button>
                </div>
            }
        </nav>
    );
}

export default withRouter(NavBar);