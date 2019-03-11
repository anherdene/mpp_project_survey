import React, { Component } from 'react';
import AuthService from './AuthService';

export default function withAuth(AuthComponent) {
    const Auth = new AuthService(process.env.REACT_APP_WS_URL);
    return class AuthWrapped extends Component {
        constructor() {
            super();
            this.state = {
                user: null
            }
        }

        componentWillMount() {
            if (!Auth.loggedIn()) {
                this.props.history.replace('/login')
            } else {
                try {
                    const profile = Auth.getProfile()
                    this.setState({
                        user: profile
                    })
                } catch (err) {
                    Auth.logout()
                    this.props.history.replace('/login')
                }
            }
        }

        render() {
            if (this.state.user) {
                return (
                    <AuthComponent history={this.props.history} user={this.state.user}/>
                )
            } else {
                return null
            }
        }
    }
}


// -Deployment Env : 10%
// +Initial design  : 30%
// +Degree of matching design and imp : 5%
// UI (usability) : 5%
// + Authentication : 5%
// 50% Upload survey from CSV : 10%
// 50% Input validation : +5%
// -Charting/reporting : 10%
// +User rating : 5%
// +Personal cont : 10%
// Test coverage : 5% (30% line coverage) + 5
// +Scalability : 5%