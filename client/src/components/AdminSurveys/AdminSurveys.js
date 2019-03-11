import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import NavBar from "../NavBar/NavBar";

class AdminSurveys extends Component {
    constructor(props) {
        super(props);

        this.state = {
            surveys: null,
        };
    }

    async componentDidMount() {
        const surveys = (await axios.get('http://localhost:8081/')).data;
        this.setState({
            surveys,
        });
    }

    render() {
        return (
            <div>
                <NavBar />
            <div className="container">
                <div className="row">
                    <Link to="/new-survey">
                        <div className="card text-white bg-secondary mb-3">
                            <div className="card-header">Create survey</div>
                            <div className="card-body">
                                <h4 className="card-title">+ New Survey</h4>
                                <p className="card-text">Create survey</p>
                            </div>
                        </div>
                    </Link>
                    {this.state.surveys === null && <p>Loading surveys...</p>}
                    {
                        this.state.surveys && this.state.surveys.map(survey => (
                            <div key={survey.id} className="col-sm-12 col-md-4 col-lg-3">
                                <Link to={`/manage/${survey.id}`}>
                                    <div className="card text-white bg-success mb-3">
                                        <div className="card-header">Questions: {survey.answers}</div>
                                        <div className="card-body">
                                            <h4 className="card-title">{survey.title}</h4>
                                            <p className="card-text">{survey.description}</p>
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        ))
                    }
                </div>
            </div>
            </div>
        )
    }
}

export default AdminSurveys;