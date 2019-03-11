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
import AdminQuestion from "./components/AdminQuestion/AdminQuestion";
import Login from "./components/Login/Login";

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
                <SecuredRoute path='/new-survey'
                              history={this.props.history}
                              component={NewSurvey}
                              checkingSession={this.state.checkingSession} />
                <SecuredRoute path='/admin'
                              component={AdminSurveys}
                              history={this.props.history}
                              checkingSession={this.state.checkingSession} />
                <Route exact path='/manage/:surveyId' component={AdminSurvey}/>
                {/*<SecuredRoute path='/manage/:surveyId'*/}
                              {/*component={AdminSurvey}*/}
                              {/*checkingSession={this.state.checkingSession} />*/}
                {/*<Route exact path='/manage/:questionId' component={AdminQuestion}/>*/}
            </div>

        );
    }
}

export default withRouter(App);
