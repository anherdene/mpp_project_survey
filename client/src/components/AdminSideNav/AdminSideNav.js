import React from 'react';
import {withRouter} from 'react-router-dom';

function AdminSideNav(props) {

    return (
        <div className="jumbotron col-2 admin-sidebar">
            <ul className="nav navbar-nav">
                <li><a href="http://google.com"><i className="fas fa-question"></i><span>Questions</span></a></li>
                <li><a href="http://google.com"><i className="fas fa-share"></i><span>Share</span></a></li>
                <li><a href="http://google.com"><i className="fas fa-chart-pie"></i><span>Results</span></a></li>
            </ul>
        </div>
    );
}

export default withRouter(AdminSideNav);