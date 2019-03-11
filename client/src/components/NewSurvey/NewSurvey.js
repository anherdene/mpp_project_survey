import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import auth0Client from '../../Auth';
import axios from 'axios';
import NavBar from "../NavBar/NavBar";
import {FileUploader} from "../AdminSurveys/AdminFileUploader";

class NewSurvey extends Component {
    constructor(props) {
        super(props);

        this.state = {
            disabled: false,
            title: '',
            description: '',
        };
    }

    updateDescription(value) {
        this.setState({
            description: value,
        });
    }

    updateTitle(value) {
        this.setState({
            title: value,
        });
    }

    async submit() {
        this.setState({
            disabled: true,
        });
        alert("Successfully created survey!");
        this.props.history.replace('/admin')

        // await axios.post('http://localhost:8081', {
        //     title: this.state.title,
        //     description: this.state.description,
        // }, {
        //     headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        // }).then(response=> {
        //
        //     // this.props.history.push('/survey/' + response.data.id);
        //     // console.log(response.data);
        // });

    }

    render() {
        return (
            <div>
                <NavBar/>
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            <div className="card border-primary">
                                <div className="card-header">New Survey</div>
                                <div className="card-body text-left">
                                    <div className="form-group">
                                        <label htmlFor="exampleInputEmail1">Survey:</label>
                                        <input
                                            disabled={this.state.disabled}
                                            type="text"
                                            onBlur={(e) => {
                                                this.updateTitle(e.target.value)
                                            }}
                                            className="form-control"
                                            placeholder="Give your survey a title."
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="exampleInputEmail1">Description:</label>
                                        <input
                                            disabled={this.state.disabled}
                                            type="text"
                                            onChange={(e) => {
                                                this.updateDescription(e.target.value)
                                            }}
                                            className="form-control"
                                            placeholder="Give more context to your survey."
                                        />
                                    </div>
                                    {(this.state.title !== "" && this.state.description !== "") &&
                                    <div className="form-group">
                                        <label htmlFor="exampleInputEmail1">CSV File:</label>
                                        <FileUploader title={this.state.title} desc={this.state.description}/>
                                    </div>
                                    }

                                    <button
                                        disabled={this.state.disabled}
                                        className="btn btn-primary"
                                        onClick={() => {
                                            this.submit()
                                        }}>
                                        Submit
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(NewSurvey);