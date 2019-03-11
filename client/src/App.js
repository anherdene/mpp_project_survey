import React, {Component} from 'react';
import Surveys from "./components/Surveys/Surveys";
import Survey from "./components/Survey/Survey";
import Callback from "./Callback";
import SecuredRoute from "./components/SecuredRoute/SecuredRoute";
import NewSurvey from "./components/NewSurvey/NewSurvey";
import {Route, withRouter} from 'react-router-dom';
import auth0Client from './Auth';
import AdminSurveys from "./components/AdminSurveys/AdminSurveys";
import AdminSurvey from "./components/AdminSurvey/AdminSurvey";
import Login from "./components/Login/Login";
import SignUp from "./components/SignUp/SignUp";
import jwtAuth from "./AuthService";
import AdminSurveyResults from "./components/AdminSurveyResults/AdminSurveyResults";

class App extends Component {

    constructor (props) {
        super(props);
        this.state = {
            checkingSession: true,
        }
    }

    async componentDidMount() {
        if (this.props.location.pathname === '/callback') {
            this.setState({checkingSession:false});
            return;
        }

        if (jwtAuth.loggedIn()) {
            try {
                await jwtAuth.getUserProfile();
            } catch (e) {
                console.log(e);
            }
        }

        try {
            await auth0Client.silentAuth();
            this.forceUpdate();
        } catch (err) {
            if (err.error !== 'login_required') console.log(err.error);
        }
        this.setState({checkingSession:false});
    }

    render() {
        return (
            <div>
                <Route exact path='/' component={Surveys}/>
                <Route exact path='/survey/:surveyId' component={Survey}/>
                {/*<SecuredRoute path='/survey/:surveyId'*/}
                              {/*history={this.props.history}*/}
                              {/*component={Survey}*/}
                              {/*checkingSession={this.state.checkingSession} />*/}
                <Route exact path='/callback' component={Callback}/>
                <Route exact path='/login' component={Login}/>
                <Route exact path='/signup' component={SignUp}/>
                <SecuredRoute path='/new-survey'
                              history={this.props.history}
                              component={NewSurvey}
                              checkingSession={this.state.checkingSession} />
                <SecuredRoute path='/admin'
                              component={AdminSurveys}
                              history={this.props.history}
                              checkingSession={this.state.checkingSession} />
                <Route exact path='/manage/:surveyId' component={AdminSurvey}/>
                <Route exact path='/results/:surveyId' component={AdminSurveyResults}/>
                {/*<SecuredRoute path='/manage/:surveyId'*/}
                              {/*component={AdminSurvey}*/}
                              {/*checkingSession={this.state.checkingSession} />*/}
                {/*<Route exact path='/manage/:questionId' component={AdminQuestion}/>*/}
            </div>

        );
    }
}

export default withRouter(App);
