import React, {Component} from 'react';
import axios from 'axios';
import auth0Client from '../../Auth';
import Questions from "../Questions/Questions";
import {fetchSurvey} from "../../api";
import NavBar from "../NavBar/NavBar";

class Survey extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            survey: null
        };

        this.submitAnswer = this.submitAnswer.bind(this);
        this.newQuestion = this.newQuestion.bind (this);
    }

    async componentDidMount() {
        await this.refreshQuestion();
        await this.getUserData();
    }

    async getUserData() {
        const user = (await axios.get(`http://localhost:8081/user`)).data;
        this.setState({
            user,
        })
    }

    async refreshQuestion() {
        const { match: { params } } = this.props;
        console.log(this.props);
        // const survey = (await axios.get(`http://localhost:8081/${params.surveyId}`)).data;
        const survey = await fetchSurvey(params.surveyId);
        const questions = survey.questions;
        console.log(questions);
        this.setState({
            survey
        });
    }

    async submitAnswer(answer) {
        await axios.post(`http://localhost:8081/answer/${this.state.survey.id}`, {
            answer,
        }, {
            headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        });
        await this.refreshQuestion();
    }

    async newQuestion(question) {
        await axios.post(`http://localhost:8081/question/${this.state.survey.id}`, {
            question,
        }, {
            headers: { 'Authorization': `Bearer ${auth0Client.getIdToken()}` }
        });
        await this.refreshQuestion();
    }

    myCallback = (dataFromChild) => {
        console.log(dataFromChild);
    };

    render() {
        // console.log(this.state);
        const {survey} = this.state;
        // const {questions} = this.state['survey']['questions'];
        if (survey === null) return <p>Loading ...</p>;
        return (
            <div>
                <NavBar/>
            <div className="container">
                <div className="row">
                    {/*<AdminSideNav/>*/}
                    <div className="jumbotron col-9">
                        <h4 className="survey-title">{survey.title}</h4>
                        <p className="lead">{survey.description}</p>
                        <hr className="my-4" />

                        <Questions callbackFromParent={this.myCallback} children={survey}/>
                    </div>
                </div>
            </div>
            </div>
        )
    }
}

export default Survey;