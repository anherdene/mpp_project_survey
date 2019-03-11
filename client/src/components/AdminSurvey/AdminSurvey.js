import React, {Component} from 'react';
import AdminSideNav from "../AdminSideNav/AdminSideNav";
import AdminQuestions from "../AdminQuestions/AdminQuestions";
import NavBar from "../NavBar/NavBar";
import {fetchSurvey} from "../../api";

class AdminSurvey extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            survey: null
        };
    }

    async componentDidMount() {
        await this.refreshQuestion();
    }

    async refreshQuestion() {
        // const { match: { params } } = this.props;
        const params = this.props.match.params;
        // console.log(this.props);
        // const survey = (await axios.get(`http://localhost:8081/${params.surveyId}`)).data;
        const survey = await fetchSurvey(params.surveyId);
        const questions = survey.questions;
        console.log(questions);
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
                        <AdminQuestions children={survey}/>
                    </div>
                </div>
            </div>
            </div>
        )
    }
}

export default AdminSurvey;