import React, { Component } from 'react';
import './SignUp.css';
import jwtAuth from "../../AuthService";
import {createUser} from "../../api";
import {Link} from "react-router-dom";

class SignUp extends Component {
    constructor(){
        super();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        // this.jwtAuth = new AuthService();
    }

    componentWillMount(){
        if(jwtAuth.loggedIn())
            this.props.history.replace('/');
    }

    render() {
        return (
            <div className="center">
                <div className="card">
                    <h1>Sign Up</h1>
                    <Link to="/login">
                        <h4 className="sign-up">Login</h4>
                    </Link>
                    <form onSubmit={this.handleFormSubmit}>
                        <input
                            className="form-item"
                            placeholder="Username goes here..."
                            name="username"
                            type="text"
                            onChange={this.handleChange}
                        />
                        <input
                            className="form-item"
                            placeholder="Password goes here..."
                            name="password"
                            type="password"
                            onChange={this.handleChange}
                        />
                        <input
                            className="form-submit"
                            value="SUBMIT"
                            type="submit"
                        />
                    </form>
                </div>
            </div>
        );
    }

    handleChange(e){
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        )
    }

    async handleFormSubmit(e){
        e.preventDefault();

        await createUser(this.state.username,this.state.password)
            .then(res =>{
                this.props.history.replace('/');
            })
            .catch(err =>{
                alert("Username or password incorrect");
            })
    }
}

export default SignUp;