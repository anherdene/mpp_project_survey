import React, { Component } from 'react';
import './Login.css';
import jwtAuth from "../../AuthService";

class Login extends Component {
    constructor(){
        super();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        // this.jwtAuth = new AuthService();
    }

    componentWillMount(){
        if(jwtAuth.loggedIn()){
            console.log("asdasdasdasd");
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
                this.props.history.replace('/');
            })
            .catch(err =>{
                alert(err);
            })
    }
}

export default Login;