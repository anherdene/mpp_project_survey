import React, {Component} from 'react';
import {fetchSurvey} from "../../api";
import AdminSideNav from "../AdminSideNav/AdminSideNav";
import AdminQuestionResults from "./AdminQuestionResults";
import NavBar from "../NavBar/NavBar";

class AdminSurveyResults extends Component {
    constructor (props) {
        super(props);
        this.state = {
            survey: null
        };
    }

    async componentDidMount() {
        await this.getSurvey();
    }

    async getSurvey() {
        const params = this.props.match.params;
        const survey = await fetchSurvey(params.surveyId);
        this.setState({
            survey
        });
    }


    render() {
        const {survey} = this.state;
        if (survey === null) return <p>Loading ...</p>;
        return (
            <div>
                <NavBar />
                <div className="container">
                    <div className="row">
                        <AdminSideNav/>
                        <div className="jumbotron col-9">
                            <h4 className="survey-title">{survey.title}</h4>
                            <p className="lead">{survey.description}</p>
                            <hr className="my-4" />
                            {/*<Questions/>*/}
                            <AdminQuestionResults children={survey}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default AdminSurveyResults;