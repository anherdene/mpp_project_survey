import React, { Component } from 'react';
import './Login.css';
import jwtAuth from "../../AuthService";
import {Link} from "react-router-dom";

class Login extends Component {
    constructor(){
        super();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);

        this.state = {
            user: null
        }
        // this.jwtAuth = new AuthService();
    }

    componentWillMount(){
        if(jwtAuth.loggedIn()){
            try {
                jwtAuth.getUserProfile().then( res => {
                    console.log(res);
                    if (localStorage.role === "user") {
                    this.props.history.replace('/');
                } else if (localStorage.role === "admin") {
                    this.props.history.replace('/admin');
                }});

            } catch (e) {
                console.log(e);
            }
        }
    }

    render() {
        return (
            <div className="center">
                <div className="card">
                    <h1>Login</h1>
                    <Link to="/signup">
                        <h4 className="sign-up">Sign up</h4>
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

    handleFormSubmit(e){
        e.preventDefault();

        jwtAuth.login(this.state.username,this.state.password)
            .then(res =>{
                const {profile} = jwtAuth.getUserProfile();

                this.setState({
                    user: profile
                });
                if (this.state.username === "admin") {
                    this.props.history.replace('/admin');
                } else {
                    this.props.history.replace('/');
                }
            })
            .catch(err =>{
                alert("Username or password incorrect");
            })
    }
}

export default Login;