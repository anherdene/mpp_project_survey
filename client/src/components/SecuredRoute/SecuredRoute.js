import React from 'react';
import {Route} from 'react-router-dom';
import jwtAuth from "../../AuthService";

function SecuredRoute(props) {
    const {component: Component, path, checkingSession} = props;
    return (
        <Route path={path} render={() => {
            if (checkingSession) return <h3>Validating session...</h3>;
            if (!jwtAuth.loggedIn()) {
                props.history.replace('/login');
                return <div></div>;
            }
            return <Component />
        }} />
    );
}

export default SecuredRoute;